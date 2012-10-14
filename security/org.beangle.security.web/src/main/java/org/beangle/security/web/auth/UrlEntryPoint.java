/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.auth;

import org.beangle.security.web.AuthenticationEntryPoint;

/**
 * @author chaostone
 * @version $Id: UrlAuthenticationEntryPoint.java Nov 6, 2010 11:48:34 AM
 *          chaostone $
 */
public interface UrlEntryPoint extends AuthenticationEntryPoint {

  String getLoginUrl();
}
