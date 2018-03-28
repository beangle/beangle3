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
package org.beangle.security.core.session;

import org.beangle.security.core.Authentication;

/**
 * 记录session信息的注册表
 *
 * @author chaostone
 */
public interface SessionRegistry extends SessionRepo{

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
  Session remove(String sessionid);
}
