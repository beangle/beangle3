/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.session.service;

import java.util.Date;
import java.util.List;
import java.util.Timer;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.cache.Cache;
import org.beangle.commons.cache.concurrent.ConcurrentMapCache;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Objects;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.*;
import org.beangle.security.core.session.impl.SimpleSessioninfoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Database central session registry.
 * <ul>
 * <li>Support local session status cache,And update acess time and reload expire time in every
 * syncInterval(30s).</li>
 * <li>Support expire session clean task in every 5 mins.
 * </ul>
 * 
 * @author chaostone
 * @since 2.4
 */
public class DbSessionRegistry extends BaseServiceImpl implements SessionRegistry, Initializing {

  protected static final Logger logger = LoggerFactory.getLogger(DbSessionRegistry.class);

  private SessionController controller;

  private SessioninfoBuilder sessioninfoBuilder = new SimpleSessioninfoBuilder();

  private Cache<String, SessionStatus> cache = new ConcurrentMapCache<String, SessionStatus>("session status");

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public void init() throws Exception {
    Assert.notNull(controller, "controller must set");
    Assert.notNull(sessioninfoBuilder, "sessioninfoBuilder must set");

    long now = System.currentTimeMillis();
    // 下一次间隔开始清理，不浪费启动时间
    DbSessionCacheSyncDaemon cacheSync = new DbSessionCacheSyncDaemon(entityDao, cache,
        sessioninfoBuilder.getSessioninfoType());
    new Timer("Beangle Session Cache Synchronizer", true).schedule(cacheSync,
        new Date(now + cacheSync.getInterval()), cacheSync.getInterval());

    DbSessionCleanupDaemon cleaner = new DbSessionCleanupDaemon(this);
    new Timer("Beangle Session Cleaner", true).schedule(cleaner, new Date(now + cleaner.getCleanInterval()),
        cleaner.getCleanInterval());

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

  @Override
  public SessionStatus getSessionStatus(String sessionid) {
    SessionStatus status = cache.get(sessionid).orNull();
    if (null == status) {
      status = getStatusFromDB(sessionid);
      if (null != status) cache.put(sessionid, status);
    }
    return status;
  }

  private SessionStatus getStatusFromDB(String sessionid) {
    OqlBuilder<Sessioninfo> builder = OqlBuilder.from(getSessioninfoTypename(), "info");
    builder.where("info.id=:sessionid", sessionid);
    List<Sessioninfo> infos = entityDao.search(builder);
    if (infos.isEmpty()) return null;
    else return new SessionStatus(infos.get(0));
  }

  public void register(Authentication auth, String sessionId) throws SessionException {
    SessionStatus existed = getStatusFromDB(sessionId);
    String principal = auth.getName();
    // 是否为重复注册
    if (null != existed && Objects.equals(existed.getUsername(), principal)) return;
    // 争取名额
    boolean success = controller.onRegister(auth, sessionId, this);
    if (!success) throw new SessionException("security.OvermaxSession");
    // 注销同会话的其它账户
    if (null != existed) remove(sessionId, " expired with replacement.");
    // 新生
    Sessioninfo info = sessioninfoBuilder.build(auth, sessionId);
    entityDao.save(info);
    publish(new LoginEvent(info));
  }

  public Sessioninfo remove(String sessionId) {
    return remove(sessionId, null);
  }

  private Sessioninfo remove(String sessionId, String reason) {
    cache.evict(sessionId);
    Sessioninfo info = getSessioninfo(sessionId);
    if (null != info) {
      // FIXME not in a transaction
      if (null != reason) info.addRemark(reason);
      entityDao.remove(info);
      controller.onLogout(info);
      publish(new LogoutEvent(info));
      logger.debug("Remove session {} for {}", sessionId, info.getUsername());
    }
    return info;
  }

  public boolean expire(String sessionId) {
    // process local cache first
    cache.evict(sessionId);
    Sessioninfo info = getSessioninfo(sessionId);
    if (null != info) {
      if (!info.isExpired()) {
        controller.onLogout(info);
        entityDao.saveOrUpdate(info.expireNow());
        return true;
      }
    }
    return false;
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

  public void access(String sessionid, long accessAt) {
    SessionStatus status = cache.get(sessionid).orNull();
    if (null == status) {
      status = getStatusFromDB(sessionid);
      if (null != status) cache.put(sessionid, status);
    }
    status.setLastAccessedTime(accessAt);
  }

  public String getSessioninfoTypename() {
    return sessioninfoBuilder.getSessioninfoType().getName();
  }

}
