/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import org.beangle.commons.lang.Option;
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

  /**
   * Max session number for single user
   * 
   * @param auth
   * @return -1 or positive number
   */
  public int getMaxSessions(Authentication auth);

  /**
   * User online max inactive interval
   * 
   * @param auth
   * @return None or inactive interval
   */
  Option<Integer> getInactiveInterval(Authentication auth);

}
