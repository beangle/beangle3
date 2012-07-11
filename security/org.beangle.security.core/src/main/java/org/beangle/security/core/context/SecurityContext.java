/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.context;

import java.io.Serializable;

import org.beangle.security.core.Authentication;

/**
 * Interface defining the minimum security information associated with the
 * current thread of execution.
 * <p>
 * The security context is stored in a {@link SecurityContextHolder}.
 * </p>
 * 
 * @author chaostone
 * @version $Id: SecurityContext.java 2217 2007-10-27 00:45:30Z $
 */
public interface SecurityContext extends Serializable {

  /**
   * Obtains the currently authenticated principal, or an authentication
   * request token.
   * 
   * @return the <code>Authentication</code> or <code>null</code> if no
   *         authentication information is available
   */
  Authentication getAuthentication();

  /**
   * Changes the currently authenticated principal, or removes the
   * authentication information.
   * 
   * @param authentication
   *          the new <code>Authentication</code> token, or <code>null</code> if no further
   *          authentication information
   *          should be stored
   */
  void setAuthentication(Authentication authentication);
}
