package org.beangle.emsapp.demo;

import org.beangle.ems.security.service.CacheableAuthorityManager;
import org.beangle.security.core.Authentication;

public class MockAuthorityManager extends CacheableAuthorityManager {

	@Override
	public boolean isAuthorized(Authentication auth, Object resource) {
		return true;
	}

}
