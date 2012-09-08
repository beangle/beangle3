/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import org.beangle.security.core.Authentication;

/**
 * Session limit controller
 * 
 * @author chaostone
 */
public interface SessionController {

  /**
   * reserve space for given sessionid
   * 
   * @param auth
   * @param sessionId
   * @param registry
   * @return
   */
  public boolean onRegister(Authentication auth, String sessionId, SessionRegistry registry)
      throws SessionException;

  /**
   * 释放sessioninfo对应的空间
   * 
   * @param info
   */
  public void onLogout(Sessioninfo info);

  /**
   * 统计
   */
  public void stat();

}
