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
package org.beangle.security.core.session;

import java.util.Date;

import org.beangle.commons.entity.Entity;

/**
 * 在线活动
 * 
 * @author chaostone
 */
public interface Sessioninfo extends Entity<String> {
  /**
   * 用户名
   */
  String getUsername();

  /**
   * 用户全名
   */
  String getFullname();

  /**
   * 登录时间
   */
  Date getLoginAt();

  /**
   * 是否过期
   */
  boolean isExpired();

  /**
   * 在线时间
   */
  long getOnlineTime();

  /**
   * 备注
   */
  String getRemark();

  /**
   * 使之过期
   */
  void expireNow();

  /**
   * 添加备注
   * 
   * @param added
   */
  void addRemark(String added);

  /**
   * 查询过期时间
   */
  Date getExpiredAt();

  /**
   * 查询最后访问时间
   */
  Date getLastAccessAt();

}
