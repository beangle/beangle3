/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
