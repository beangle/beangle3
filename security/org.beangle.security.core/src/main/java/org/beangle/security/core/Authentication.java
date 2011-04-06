/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core;

import java.io.Serializable;
import java.security.Principal;
import java.util.Collection;

public interface Authentication extends Principal, Serializable {

	public Object getPrincipal();

	public Object getCredentials();

	public Collection<? extends GrantedAuthority> getAuthorities();

	public Object getDetails();

	public boolean isAuthenticated();

	public void setAuthenticated(boolean authenticated);

}
