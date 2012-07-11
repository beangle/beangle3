/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.auth;

/**
 * 账户禁用异常
 * 
 * @author chaostone
 */
public class DisabledException extends AccountStatusException {
  private static final long serialVersionUID = 1L;

  public DisabledException(String msg) {
    super(msg);
  }

  public DisabledException(String msg, Object extraInformation) {
    super(msg, extraInformation);
  }
}
