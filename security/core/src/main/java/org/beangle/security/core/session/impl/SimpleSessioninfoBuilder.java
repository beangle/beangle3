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
package org.beangle.security.core.session.impl;

import java.util.Date;

import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.Sessioninfo;
import org.beangle.security.core.session.SessioninfoBuilder;
import org.beangle.security.core.session.category.CategorySessioninfo;

/**
 * @author chaostone
 * @version $Id: SimpleSessioninfoBuilder.java Jul 18, 2011 10:42:08 AM chaostone $
 */
public class SimpleSessioninfoBuilder implements SessioninfoBuilder {

  public Class<? extends Sessioninfo> getSessioninfoType() {
    return SimpleSessioninfo.class;
  }

  public Sessioninfo build(Authentication auth, String sessionid) {
    SimpleSessioninfo info = new SimpleSessioninfo(sessionid, auth.getName(), auth.getName());
    return info;
  }

}

class SimpleSessioninfo implements CategorySessioninfo {

  private String id;

  /** 系统登录用户 */
  private String username;

  /** 用户真实姓名 */
  private String fullname;

  private String category;

  /** 登录时间 */
  private Date loginAt;

  /** 过期时间 */
  private Date expiredAt;

  /** 最后访问时间 */
  private Date lastAccessAt;

  /** 备注 */
  private String remark;

  private String server;

  public SimpleSessioninfo() {
    super();
  }

  public SimpleSessioninfo(String id, String username, String fullname) {
    super();
    this.id = id;
    this.username = username;
    this.fullname = fullname;
    this.loginAt = new Date(System.currentTimeMillis());
  }

  public String toString() {
    String str = " User:[" + getUsername() + "]";
    long onlineTime = System.currentTimeMillis() - loginAt.getTime();
    long minute = (onlineTime / 1000) / 60;
    long second = (onlineTime / 1000) % 60;
    str += "OnLine time:[" + minute + " minute " + second + " second]";
    return str;
  }

  public Sessioninfo addRemark(String added) {
    if (null == remark) remark = added;
    else remark += added;
    return this;
  }

  public long getOnlineTime() {
    if (null == expiredAt) return System.currentTimeMillis() - loginAt.getTime();
    else return expiredAt.getTime() - loginAt.getTime();
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFullname() {
    return fullname;
  }

  public void setFullname(String fullname) {
    this.fullname = fullname;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public Date getLoginAt() {
    return loginAt;
  }

  public void setLoginAt(Date loginAt) {
    this.loginAt = loginAt;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public boolean isExpired() {
    return null != expiredAt;
  }

  public Sessioninfo expireNow() {
    if (null == expiredAt) this.expiredAt = new Date();
    return this;
  }

  public Date getExpiredAt() {
    return expiredAt;
  }

  public void setExpiredAt(Date expiredAt) {
    this.expiredAt = expiredAt;
  }

  public Date getLastAccessAt() {
    return lastAccessAt;
  }

  public void setLastAccessAt(Date lastAccessAt) {
    this.lastAccessAt = lastAccessAt;
  }

  public String getServer() {
    return server;
  }

  public void setServer(String server) {
    this.server = server;
  }

}
