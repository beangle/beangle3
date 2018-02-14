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
package org.beangle.security.blueprint.session.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.annotation.LogEntity;
import org.beangle.commons.entity.pojo.StringIdObject;

/**
 * 登录和退出日志
 *
 * @author chaostone
 */
@Entity(name = "org.beangle.security.blueprint.session.model.SessioninfoLogBean")
@LogEntity
public class SessioninfoLogBean extends StringIdObject {

  private static final long serialVersionUID = -3144771635148215917L;

  /** 系统登录用户 */
  @NotNull
  @Size(max = 40)
  private String username;

  /** 用户真实姓名 */
  @NotNull
  @Size(max = 50)
  private String fullname;

  /** 登录IP */
  @Size(max = 100)
  private String ip;

  /** OS */
  @Size(max = 50)
  private String os;

  /** agent */
  @Size(max = 50)
  private String agent;

  /** 登录时间 */
  @NotNull
  private Date loginAt;

  /** 在线时间 */
  @NotNull
  private Long onlineTime;

  /** 退出时间 */
  @NotNull
  private Date logoutAt;

  /** 备注 */
  @Size(max = 100)
  private String remark;

  public SessioninfoLogBean() {
    super();
  }

  public SessioninfoLogBean(SessioninfoBean info) {
    super();
    this.id = info.getId() + "@" + System.currentTimeMillis();
    this.username = info.getUsername();
    this.fullname = info.getFullname();
    this.loginAt = info.getLoginAt();

    if (null != info.getExpiredAt()) this.logoutAt = info.getExpiredAt();
    else this.logoutAt = new Date();

    this.calcOnlineTime();

    this.os = info.getOs();
    this.ip = info.getIp();
    this.agent = info.getAgent();
    this.remark = info.getRemark();
  }

  public String toString() {
    String str = " User:[" + getUsername() + "]";
    long onlineTime = System.currentTimeMillis() - loginAt.getTime();
    long minute = (onlineTime / 1000) / 60;
    long second = (onlineTime / 1000) % 60;
    str += "Online time:[" + minute + " minute " + second + " second]";
    return str;
  }

  public void calcOnlineTime() {
    if (null == logoutAt) {
      setOnlineTime(Long.valueOf(System.currentTimeMillis() - loginAt.getTime()));
    } else {
      setOnlineTime(Long.valueOf(logoutAt.getTime() - loginAt.getTime()));
    }
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

  public String getIp() {
    return ip;
  }

  public void setIp(String ip) {
    this.ip = ip;
  }

  public Date getLogoutAt() {
    return logoutAt;
  }

  public void setLogoutAt(Date logoutAt) {
    this.logoutAt = logoutAt;
  }

  public Date getLoginAt() {
    return loginAt;
  }

  public void setLoginAt(Date loginAt) {
    this.loginAt = loginAt;
  }

  public Long getOnlineTime() {
    return onlineTime;
  }

  public void setOnlineTime(Long onlineTime) {
    this.onlineTime = onlineTime;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getOs() {
    return os;
  }

  public void setOs(String os) {
    this.os = os;
  }

  public String getAgent() {
    return agent;
  }

  public void setAgent(String agent) {
    this.agent = agent;
  }

}
