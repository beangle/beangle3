/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.impl;

import java.util.Date;

import org.beangle.commons.orm.pojo.StringIdObject;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.Sessioninfo;
import org.beangle.security.core.session.SessioninfoBuilder;
import org.beangle.security.core.session.category.CategorySessioninfo;

/**
 * @author chaostone
 * @version $Id: SimpleSessioninfoBuilder.java Jul 18, 2011 10:42:08 AM chaostone $
 */
public class SimpleSessioninfoBuilder implements SessioninfoBuilder {

  public Class<?> getSessioninfoClass() {
    return SimpleSessioninfo.class;
  }

  public Sessioninfo build(Authentication auth, String sessionid) {
    SimpleSessioninfo info = new SimpleSessioninfo(sessionid, auth.getName(), auth.getName());
    return info;
  }

  public Object buildLog(Sessioninfo info) {
    return null;
  }

}

class SimpleSessioninfo extends StringIdObject implements CategorySessioninfo {

  private static final long serialVersionUID = 1462323011613320213L;

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

  public void addRemark(String added) {
    if (null == remark) {
      remark = added;
    } else {
      remark += added;
    }
  }

  public long getOnlineTime() {
    if (null == expiredAt) return System.currentTimeMillis() - loginAt.getTime();
    else return expiredAt.getTime() - loginAt.getTime();
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

  public void expireNow() {
    if (null == expiredAt) {
      this.expiredAt = new Date();
    }
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
}
