/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.context.event.Event;
import org.beangle.commons.context.event.EventListener;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Objects;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 * @since 2.4
 */
public class DbSessionRegistry extends BaseServiceImpl implements SessionRegistry,
    EventListener<SessionDestroyedEvent>, Initializing {

  protected static final Logger logger = LoggerFactory.getLogger(DbSessionRegistry.class);

  private SessionController controller;

  private SessioninfoBuilder sessioninfoBuilder = new SimpleSessioninfoBuilder();

  protected final Map<String, AccessEntry> entries = CollectUtils.newConcurrentHashMap();

  long updatedAt = System.currentTimeMillis();

  /**
   * Default interval for update access log to db.
   * 5 minutes.
   */
  private int updatedInterval = 5 * 60 * 1000;

  public void init() throws Exception {
    Assert.notNull(controller, "controller must set");
    Assert.notNull(sessioninfoBuilder, "sessioninfoBuilder must set");
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public boolean isRegisted(String principal) {
    OqlBuilder builder = OqlBuilder.from(sessioninfoBuilder.getSessioninfoType(), "info");
    builder.where("info.username=:username and info.expiredAt is null", principal).select("info.id")
        .cacheable();
    return !entityDao.search(builder).isEmpty();
  }

  public List<Sessioninfo> getSessioninfos(String principal, boolean includeExpiredSessions) {
    OqlBuilder<Sessioninfo> builder = OqlBuilder.from(sessioninfoBuilder.getSessioninfoType().getName(),
        "info");
    builder.where("info.username=:username", principal);
    if (!includeExpiredSessions) builder.where("info.expiredAt is null");
    return entityDao.search(builder);
  }

  public Sessioninfo getSessioninfo(String sessionId) {
    OqlBuilder<? extends Sessioninfo> builder = OqlBuilder.from(sessioninfoBuilder.getSessioninfoType(),
        "info");
    builder.where("info.id=:sessionid", sessionId);
    List<? extends Sessioninfo> infos = entityDao.search(builder);
    if (infos.isEmpty()) return null;
    else return infos.get(0);
  }

  public SessionStatus getSessionStatus(String sessionid) {
    OqlBuilder<SessionStatus> builder = OqlBuilder.from(sessioninfoBuilder.getSessioninfoType().getName(),
        "info");
    builder.where("info.id=:sessionid", sessionid)
        .select("new org.beangle.security.core.session.SessionStatus(info.username,info.expiredAt)")
        .cacheable();
    List<SessionStatus> infos = entityDao.search(builder);
    if (infos.isEmpty()) return null;
    else return infos.get(0);
  }

  public void register(Authentication auth, String sessionId) throws SessionException {
    SessionStatus existed = getSessionStatus(sessionId);
    String principal = auth.getName();
    // 是否为重复注册
    if (null != existed && Objects.equals(existed.getUsername(), principal)) return;
    // 争取名额
    boolean success = controller.onRegister(auth, sessionId, this);
    if (!success) throw new SessionException("security.OvermaxSession");
    // 注销同会话的其它账户
    if (null != existed) remove(sessionId, " expired with replacement.");
    // 新生
    entityDao.save(sessioninfoBuilder.build(auth, sessionId));
  }

  public Sessioninfo remove(String sessionId) {
    return remove(sessionId, null);
  }

  private Sessioninfo remove(String sessionId, String reason) {
    Sessioninfo info = getSessioninfo(sessionId);
    if (null == info) {
      return null;
    } else {
      // FIXME not in a transaction
      if (null != reason) info.addRemark(reason);
      entityDao.remove(info);
      controller.onLogout(info);
      entries.remove(info.getId());
      Object sessioninfoLog = sessioninfoBuilder.buildLog(info);
      if (null != sessioninfoLog) entityDao.save(sessioninfoLog);
      logger.debug("Remove session {} for {}", sessionId, info.getUsername());
      return info;
    }
  }

  public void expire(String sessionId) {
    Sessioninfo info = getSessioninfo(sessionId);
    if (null != info) {
      controller.onLogout(info);
      info.expireNow();
      entityDao.saveOrUpdate(info);
    }
  }

  // 当会话消失时，退出用户
  public void onEvent(SessionDestroyedEvent event) {
    remove(event.getId());
  }

  public boolean supportsEventType(Class<? extends Event> eventType) {
    return SessionDestroyedEvent.class.isAssignableFrom(eventType);
  }

  public boolean supportsSourceType(Class<?> sourceType) {
    return true;
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public int count() {
    OqlBuilder builder = OqlBuilder.from(Sessioninfo.class, "info");
    builder.select("count(id)");
    List<Number> numbers = entityDao.search(builder);
    if (numbers.isEmpty()) return 0;
    else return (numbers.get(0)).intValue();
  }

  public void setController(SessionController controller) {
    this.controller = controller;
  }

  public SessionController getController() {
    return controller;
  }

  public void setSessioninfoBuilder(SessioninfoBuilder sessioninfoBuilder) {
    this.sessioninfoBuilder = sessioninfoBuilder;
  }

  public SessioninfoBuilder getSessioninfoBuilder() {
    return sessioninfoBuilder;
  }

  public void access(String sessionid, String resource, long accessAt) {
    if (accessAt - updatedAt > updatedInterval) {
      new Thread(new AccessUpdaterTask(this)).start();
    }
    AccessEntry entry = entries.get(sessionid);
    if (null == entry) entries.put(sessionid, new AccessEntry(resource, accessAt));
    else entry.access(resource, accessAt);
  }

  public String getResource(String sessionid) {
    AccessEntry entry = entries.get(sessionid);
    if (null == entry) return null;
    else return entry.resource;
  }

}

class AccessUpdaterTask implements Runnable {

  DbSessionRegistry registry;

  public AccessUpdaterTask(DbSessionRegistry registry) {
    super();
    this.registry = registry;
  }

  public void run() {
    EntityDao entityDao = registry.getEntityDao();
    long updatedAt = registry.updatedAt;
    List<Object[]> arguments = CollectUtils.newArrayList();
    for (Map.Entry<String, AccessEntry> entry : registry.entries.entrySet()) {
      AccessEntry accessEntry = entry.getValue();
      if (accessEntry.accessAt > updatedAt) {
        Date accessAt = new Date(entry.getValue().accessAt);
        arguments.add(new Object[] { accessAt, accessAt, entry.getKey() });
      }
    }
    if (!arguments.isEmpty()) {
      entityDao.executeUpdateHqlRepeatly("update "
          + registry.getSessioninfoBuilder().getSessioninfoType().getName()
          + " info set info.lastAccessAt=? where info.id=? and info.lastAccessAt < ? ", arguments);
    }
    registry.updatedAt = System.currentTimeMillis();
  }

}

class AccessEntry {
  String resource;
  long accessAt;

  public AccessEntry(String resource, long accessMillis) {
    super();
    this.resource = resource;
    this.accessAt = accessMillis;
  }

  public void access(String resource, long accessMillis) {
    this.resource = resource;
    this.accessAt = accessMillis;
  }
}
