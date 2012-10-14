/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

/**
 * 认证信息
 * 
 * @author chaostone
 */
public interface Authentication extends Principal, Serializable {

  Object getPrincipal();

  Object getCredentials();

  Collection<? extends GrantedAuthority> getAuthorities();

  Object getDetails();

  boolean isAuthenticated();

  void setAuthenticated(boolean authenticated);
}
