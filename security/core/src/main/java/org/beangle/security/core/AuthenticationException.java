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
package org.beangle.security.core;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.Throwables;
import org.beangle.security.BeangleSecurityException;

/**
 * 认证异常
 * 
 * @author chaostone
 */
public class AuthenticationException extends BeangleSecurityException {

  private static final long serialVersionUID = -3529782031102169004L;
  private Authentication authentication;
  private Object extraInfo;

  public AuthenticationException() {
    super();
  }

  /**
   * @param message
   */
  public AuthenticationException(String message) {
    super(message);
  }

  /**
   * @param cause
   */
  public AuthenticationException(Throwable cause) {
    super(Throwables.getStackTrace(cause));
  }

  public AuthenticationException(String msg, Object extraInfo) {
    super(msg);
    this.extraInfo = extraInfo;
  }

  public Authentication getAuthentication() {
    return authentication;
  }

  public void setAuthentication(Authentication authentication) {
    this.authentication = authentication;
  }

  public Object getExtraInfo() {
    return extraInfo;
  }

  public void clearExtraInfo() {
    this.extraInfo = null;
  }

  @Override
  public String getMessage() {
    String msg = super.getMessage();
    if (null == msg) {
      return Strings.concat("security." + Strings.substringBefore(getClass().getSimpleName(), "Exception"));
    } else {
      return msg;
    }
  }

}
