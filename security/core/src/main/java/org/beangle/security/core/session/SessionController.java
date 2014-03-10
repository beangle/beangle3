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

import org.beangle.commons.lang.Option;
import org.beangle.security.core.Authentication;

/**
 * Session limit controller
 * 
 * @author chaostone
 */
public interface SessionController {

  /**
   * reserve space for given sessionid
   * 
   * @param auth
   * @param sessionId
   * @param registry
   */
  boolean onRegister(Authentication auth, String sessionId, SessionRegistry registry) throws SessionException;

  /**
   * 释放sessioninfo对应的空间
   * 
   * @param info
   */
  void onLogout(Sessioninfo info);

  /**
   * 统计
   */
  void stat();

  /**
   * Max session number for single user
   * 
   * @param auth
   * @return -1 or positive number
   */
  int getMaxSessions(Authentication auth);

  /**
   * User online max inactive interval
   * 
   * @param auth
   * @return None or inactive interval
   */
  Option<Short> getInactiveInterval(Authentication auth);

}
