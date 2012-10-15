/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.context.event.Event;
import org.beangle.commons.context.event.EventListener;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Objects;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MemSessionRegistry implements SessionRegistry, EventListener<SessionDestroyedEvent>,
    Initializing {

  protected static final Logger logger = LoggerFactory.getLogger(MemSessionRegistry.class);

  private SessionController controller;

  private SessioninfoBuilder sessioninfoBuilder = new SimpleSessioninfoBuilder();

  // <principal,SessionIdSet>
  protected Map<String, Set<String>> principals = new ConcurrentHashMap<String, Set<String>>();

  // <sessionid,Sessioninfo>
  protected Map<String, Sessioninfo> sessionids = new ConcurrentHashMap<String, Sessioninfo>();

  public void init() throws Exception {
    Assert.notNull(controller, "controller must set");
    Assert.notNull(sessioninfoBuilder, "sessioninfoBuilder must set");
  }

  public boolean isRegisted(String principal) {
    Set<String> sessionsUsedByPrincipal = principals.get(principal);
    return (null != sessionsUsedByPrincipal && !sessionsUsedByPrincipal.isEmpty());
  }

  public List<Sessioninfo> getSessioninfos(String principal, boolean includeExpiredSessions) {
    Set<String> sessionsUsedByPrincipal = principals.get(principal);
    List<Sessioninfo> list = CollectUtils.newArrayList();
    if (null == sessionsUsedByPrincipal) { return list; }
    synchronized (sessionsUsedByPrincipal) {
      for (final String sessionid : sessionsUsedByPrincipal) {
        Sessioninfo info = getSessioninfo(sessionid);
        if (info == null) {
          continue;
        }
        if (includeExpiredSessions || !info.isExpired()) {
          list.add(info);
        }
      }
    }

    return list;
  }

  public Sessioninfo getSessioninfo(String sessionid) {
    return sessionids.get(sessionid);
  }

  public void register(Authentication auth, String sessionid) throws SessionException {
    Sessioninfo existed = getSessioninfo(sessionid);
    String principal = auth.getName();
    // 是否为重复注册
    if (null != existed && Objects.equals(existed.getUsername(), principal)) return;
    // 争取名额
    boolean success = controller.onRegister(auth, sessionid, this);
    if (!success) throw new SessionException("security.OvermaxSession");
    // 注销同会话的其它账户
    if (null != existed) {
      existed.addRemark(" expired with replacement.");
      remove(sessionid);
    }
    // 新生
    sessionids.put(sessionid, sessioninfoBuilder.build(auth, sessionid));
    Set<String> sessionsUsedByPrincipal = principals.get(principal);
    if (sessionsUsedByPrincipal == null) {
      sessionsUsedByPrincipal = Collections.synchronizedSet(new HashSet<String>(4));
      principals.put(principal, sessionsUsedByPrincipal);
    }
    sessionsUsedByPrincipal.add(sessionid);
  }

  public Sessioninfo remove(String sessionid) {
    Sessioninfo info = getSessioninfo(sessionid);
    if (null == info) { return null; }
    sessionids.remove(sessionid);
    String principal = info.getUsername();
    logger.debug("Remove session {} for {}", sessionid, principal);
    Set<String> sessionsUsedByPrincipal = principals.get(principal);
    if (null != sessionsUsedByPrincipal) {
      synchronized (sessionsUsedByPrincipal) {
        sessionsUsedByPrincipal.remove(sessionid);
        // No need to keep object in principals Map anymore
        if (sessionsUsedByPrincipal.size() == 0) {
          principals.remove(principal);
          logger.debug("Remove principal {} from registry", principal);
        }
      }
    }
    controller.onLogout(info);
    return info;
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

  public void expire(String sessionid) {
    Sessioninfo info = getSessioninfo(sessionid);
    if (null != info) info.expireNow();
  }

  public SessionStatus getSessionStatus(String sessionid) {
    Sessioninfo info = getSessioninfo(sessionid);
    if (null == info) return null;
    else return new SessionStatus(info);
  }

  public int count() {
    return sessionids.size();
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

  public void access(String sessionid, String resource, long beginAt) {
    // DO nothing
  }

  public String getResource(String sessionid) {
    return null;
  }

}
