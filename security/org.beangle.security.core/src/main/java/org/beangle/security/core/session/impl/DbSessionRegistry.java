/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.core.session.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.QueryBuilder;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Dates;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.SessionController;
import org.beangle.security.core.session.SessionException;
import org.beangle.security.core.session.SessionRegistry;
import org.beangle.security.core.session.SessionStatus;
import org.beangle.security.core.session.Sessioninfo;
import org.beangle.security.core.session.SessioninfoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 * @since 2.4
 */
public class DbSessionRegistry extends BaseServiceImpl implements SessionRegistry, Initializing {

  protected static final Logger logger = LoggerFactory.getLogger(DbSessionRegistry.class);

  private SessionController controller;

  private SessioninfoBuilder sessioninfoBuilder = new SimpleSessioninfoBuilder();

  private Map<String, SessionStatus> statusCache = CollectUtils.newConcurrentHashMap();

  /** 默认 过期时间 30分钟 */
  private int expiredTime = 30;

  /**
   * Default synchronize interval(30 sec) for update access time and reload expire time.
   */
  private int syncInterval = 30 * 1000;

  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  public void init() throws Exception {
    Assert.notNull(controller, "controller must set");
    Assert.notNull(sessioninfoBuilder, "sessioninfoBuilder must set");
    SessionSyncTask syncTask = new SessionSyncTask(this);
    // 下一次间隔开始清理，不浪费启动时间
    new Timer("Beangle Session Synchronizer", true).schedule(syncTask, new Date(System.currentTimeMillis()
        + syncInterval), syncInterval);
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
    SessionStatus status = statusCache.get(sessionid);
    if (null == status) {
      status = getStatusFromDB(sessionid);
      if (null != status) statusCache.put(sessionid, status);
    }
    return status;
  }

  private SessionStatus getStatusFromDB(String sessionid) {
    OqlBuilder<SessionStatus> builder = OqlBuilder.from(getSessioninfoTypename(), "info");
    builder.where("info.id=:sessionid", sessionid).select(
        "new " + SessionStatus.class.getName() + "(info.username,info.expiredAt)");
    List<SessionStatus> infos = entityDao.search(builder);
    if (infos.isEmpty()) return null;
    else return infos.get(0);
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
    entityDao.save(sessioninfoBuilder.build(auth, sessionId));
  }

  public Sessioninfo remove(String sessionId) {
    return remove(sessionId, null);
  }

  private Sessioninfo remove(String sessionId, String reason) {
    Sessioninfo info = getSessioninfo(sessionId);
    if (null != info) {
      // FIXME not in a transaction
      if (null != reason) info.addRemark(reason);
      entityDao.remove(info);
      controller.onLogout(info);
      statusCache.remove(info.getId());
      Object sessioninfoLog = sessioninfoBuilder.buildLog(info);
      if (null != sessioninfoLog) entityDao.save(sessioninfoLog);
      logger.debug("Remove session {} for {}", sessionId, info.getUsername());
    }
    return info;
  }

  public boolean expire(String sessionId) {
    Sessioninfo info = getSessioninfo(sessionId);
    if (null != info) {
      if (!info.isExpired()) {
        controller.onLogout(info);
        info.expireNow();
        entityDao.saveOrUpdate(info);
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
    SessionStatus status = statusCache.get(sessionid);
    if (null == status) {
      status = getStatusFromDB(sessionid);
      if (null != status) statusCache.put(sessionid, status);
    }
    status.setLastAccessedTime(accessAt);
  }

  public int getSyncInterval() {
    return syncInterval;
  }

  public void setSyncInterval(int syncInterval) {
    this.syncInterval = syncInterval;
  }

  public int getExpiredTime() {
    return expiredTime;
  }

  public void setExpiredTime(int expiredTime) {
    this.expiredTime = expiredTime;
  }

  /**
   * Reload session's expireAt time
   */
  public void reloadExpireAt() {
    Stopwatch watch = new Stopwatch(true);
    List<String> sessionIds = CollectUtils.newArrayList(statusCache.keySet());
    if (sessionIds.isEmpty()) return;
    Map<String, Object> parameterMap = CollectUtils.newHashMap();
    String hql = "select id,expiredAt from " + getSessioninfoTypename() + " where id in(:ids)";
    List<Object> results = CollectUtils.newArrayList();
    try {
      if (sessionIds.size() < 500) {
        parameterMap.put("ids", sessionIds);
        results = entityDao.search(OqlBuilder.hql(hql).params(parameterMap));
      } else {
        QueryBuilder<?> query = OqlBuilder.hql(hql);
        int i = 0;
        while (i < sessionIds.size()) {
          int end = i + 500;
          if (end > sessionIds.size()) end = sessionIds.size();
          parameterMap.put("ids", sessionIds.subList(i, end));
          results.addAll(entityDao.search(query.params(parameterMap)));
          i += 500;
        }
      }
    } catch (Exception e) {
      logger.error("Beangle session expire time reload failure.", e);
    }
    for (Object result : results) {
      Object[] data = (Object[]) result;
      SessionStatus status = statusCache.get(data[0]);
      if (null != status) status.setExpiredAt((Date) data[1]);
    }

    logger.debug("Reload {} session expire time in {}", results.size(), watch);
  }

  /**
   * Synchronize last access time and expired at.
   */
  public void updateAccessedTime(long lastUpdateAt) {
    Stopwatch watch = new Stopwatch(true);
    List<Object[]> arguments = CollectUtils.newArrayList();
    for (Map.Entry<String, SessionStatus> entry : statusCache.entrySet()) {
      SessionStatus status = entry.getValue();
      // expired session remaind to user interaction or j2ee container.
      if (!status.isExpired() && status.getLastAccessedTime() > lastUpdateAt) {
        Date accessAt = new Date(status.getLastAccessedTime());
        arguments.add(new Object[] { accessAt, entry.getKey(), accessAt });
      }
    }
    if (!arguments.isEmpty()) {
      try {
        entityDao.executeUpdateHqlRepeatly("update " + getSessioninfoTypename()
            + " info set info.lastAccessAt=? where info.id=? and info.lastAccessAt < ? ", arguments);
      } catch (Exception e) {
        logger.error("Beangle session update last accessed time failure.", e);
      }
      logger.debug("Sync {} session last access time in {}", arguments.size(), watch);
    }
  }

  private String getSessioninfoTypename() {
    return sessioninfoBuilder.getSessioninfoType().getName();
  }

  /**
   * Check expired or will expire session(now-lastAccessAt>=expiredTime),clean them
   */
  public void cleanup() {
    Stopwatch watch = new Stopwatch().start();
    logger.debug("clean up expired or over maxOnlineTime session start ...");
    Calendar calendar = Calendar.getInstance();
    try {
      OqlBuilder<? extends Sessioninfo> builder = OqlBuilder.from(sessioninfoBuilder.getSessioninfoType(),
          "info");
      builder.where(
          "info.lastAccessAt is null or info.lastAccessAt<:givenTime or info.expiredAt is not null",
          Dates.rollMinutes(calendar.getTime(), -expiredTime));
      List<? extends Sessioninfo> infos = entityDao.search(builder);
      int removed = 0;
      for (Sessioninfo info : infos) {
        remove(info.getId());
        removed++;
      }
      if (removed > 0) logger.debug("removed {} expired sessions in {}", removed, watch);
      getController().stat();
    } catch (Exception e) {
      logger.error("Beangle session cleanup failure.", e);
    }
  }
}

/**
 * Beangle session status synchronizer and expired session cleaner
 * 
 * @author chaostone
 * @since 3.1
 */
class SessionSyncTask extends TimerTask {

  private final DbSessionRegistry registry;

  private long lastCleanupAt = System.currentTimeMillis();

  private long lastUpdateAt = System.currentTimeMillis();
  /**
   * Default interval(5 minutes) for clean up expired session infos.
   */
  private final int cleanInterval = 5 * 60 * 1000;

  public SessionSyncTask(DbSessionRegistry registry) {
    super();
    this.registry = registry;
  }

  @Override
  public void run() {
    registry.reloadExpireAt();
    registry.updateAccessedTime(lastUpdateAt);
    lastUpdateAt = System.currentTimeMillis();
    if (cleanInterval <= System.currentTimeMillis() - lastCleanupAt) {
      registry.cleanup();
      lastCleanupAt = System.currentTimeMillis();
    }
  }

}
