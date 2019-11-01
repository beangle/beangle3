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

import java.security.Principal;
import java.time.Instant;

/**
 * 在线活动
 *
 * @author chaostone
 */
public interface Session {

  String getId();

  /**
   * 用户名
   */
  Principal getPrincipal();

  /**
   * 登录时间
   */
  Instant getLoginAt();

  /**
   * 查询最后访问时间
   */
  Instant getLastAccessAt();

  void setLastAccessAt(Instant d);

  int getTtiSeconds();

  Agent getAgent();

  public Long access(Instant accessAt);

  public static class Agent {
    private final String name;
    private final String ip;
    private final String os;

    public Agent(String name, String ip, String os) {
      super();
      this.name = name;
      this.ip = ip;
      this.os = os;
    }

    public String getName() {
      return name;
    }

    public String getIp() {
      return ip;
    }

    public String getOs() {
      return os;
    }

  }
}
