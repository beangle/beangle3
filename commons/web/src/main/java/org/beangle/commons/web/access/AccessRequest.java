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
package org.beangle.commons.web.access;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.validation.constraints.NotNull;

/**
 * Access Request
 *
 * @author chaostone
 * @since 3.0.1
 */
public class AccessRequest implements Serializable {

  private static final long serialVersionUID = 1L;

  /** 会话ID */
  @NotNull
  private String sessionid;

  /** 用户名 */
  @NotNull
  private String username;

  /** Response status */
  private int status = 200;

  /** 资源 */
  @NotNull
  private String uri;

  /** 查询字符串 */
  private String params;

  /** 开始时间 */
  @NotNull
  private long beginAt;

  /** 结束时间 */
  @NotNull
  private long endAt = 0;

  public AccessRequest() {
  }

  public AccessRequest(String sessionid, String username, String resource) {
    super();
    this.sessionid = sessionid;
    this.username = username;
    this.uri = resource;
    this.beginAt = System.currentTimeMillis();
  }

  public long getDuration() {
    if (0 == endAt) return System.currentTimeMillis() - beginAt;
    else return endAt - beginAt;
  }

  public String getSessionid() {
    return sessionid;
  }

  public void setSessionid(String sessionid) {
    this.sessionid = sessionid;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public long getBeginAt() {
    return beginAt;
  }

  public void setBeginAt(long beginAt) {
    this.beginAt = beginAt;
  }

  public long getEndAt() {
    return endAt;
  }

  public void setEndAt(long endAt) {
    this.endAt = endAt;
  }

  public String getUri() {
    return uri;
  }

  public void setUri(String uri) {
    this.uri = uri;
  }

  public String getParams() {
    return params;
  }

  public void setParams(String params) {
    this.params = params;
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int status) {
    this.status = status;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder(uri);
    sb.append('(');
    DateFormat f = new SimpleDateFormat("HH:mm:ss");
    sb.append(f.format(beginAt));
    sb.append('-');
    if (0 != endAt) {
      sb.append(f.format(endAt));
      sb.append(" duration ").append((endAt - beginAt) / 1000).append(" s");
    }
    sb.append(')');
    return sb.toString();
  }

}
