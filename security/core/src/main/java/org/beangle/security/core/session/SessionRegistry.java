/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.security.core.session;

import java.util.List;

import org.beangle.security.core.Authentication;

/**
 * 记录session信息的注册表
 * 
 * @author chaostone
 */
public interface SessionRegistry {

  /**
   * 注册
   * 
   * @param authentication
   * @param sessionid
   */
  void register(Authentication authentication, String sessionid) throws SessionException;

  /**
   * 注销指定sessionid
   * 
   * @param sessionid
   */
  Sessioninfo remove(String sessionid);

  /**
   * 过期指定会话
   * Return true is expire success
   * 
   * @param sessionid
   */
  boolean expire(String sessionid);

  /**
   * 查询某帐号的在线信息
   * 
   * @param principal
   * @param includeExpiredSessions
   */
  List<Sessioninfo> getSessioninfos(String principal, boolean includeExpiredSessions);

  /**
   * 查询对应sessionid的信息
   * 
   * @param sessionid
   */
  Sessioninfo getSessioninfo(String sessionid);

  /**
   * 查询会话状态
   * 
   * @param sessionid
   */
  SessionStatus getSessionStatus(String sessionid);

  /**
   * 查询帐号是否还有没有过期的在线记录
   * 
   * @param principal
   */
  boolean isRegisted(String principal);

  /**
   * session count
   */
  int count();

  /**
   * 更新对应sessionId的最后访问时间
   * 
   * @param sessionid
   */
  void access(String sessionid, long accessAt);

  /**
   * 查询控制器
   */
  SessionController getController();
}
