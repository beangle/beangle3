package org.beangle.webapp.demo;

import org.beangle.security.blueprint.service.CacheableAuthorityManager;
import org.beangle.security.core.Authentication;

public class MockAuthorityManager extends CacheableAuthorityManager {

	@Override
	public boolean isAuthorized(Authentication auth, Object resource) {
		return true;
	}

}
