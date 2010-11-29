/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security;

import org.beangle.security.core.Authentication;

/**
 * 授权判断服务
 * 
 * @author chaostone
 */
public interface AuthorityManager {

	public boolean isAuthorized(Authentication auth, Object resource);

	// public void registerAuthorities(Long userId);
	//
	// public void registerGroupAuthorities(Group group);
	//
	// public void removeAuthorities(Long userId);
	//
	// public boolean isPublic(String resourceName);
	//
	// public Set<String> getPublicResources();
	//
	// public void refreshCache();
}
