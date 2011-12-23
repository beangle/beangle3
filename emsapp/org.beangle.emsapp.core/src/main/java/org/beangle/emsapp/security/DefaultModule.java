/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security;

import org.beangle.emsapp.security.nav.service.MenuServiceImpl;
import org.beangle.emsapp.security.service.AuthorityServiceImpl;
import org.beangle.emsapp.security.service.CacheableAuthorityManager;
import org.beangle.emsapp.security.service.CsvDataResolver;
import org.beangle.emsapp.security.service.DaoUserDetailServiceImpl;
import org.beangle.emsapp.security.service.GroupServiceImpl;
import org.beangle.emsapp.security.service.IdentifierDataResolver;
import org.beangle.emsapp.security.service.OqlDataProvider;
import org.beangle.emsapp.security.service.UserServiceImpl;
import org.beangle.emsapp.security.session.service.GroupProfileServiceImpl;
import org.beangle.security.core.session.impl.DbSessionRegistry;
import org.beangle.spring.config.AbstractBindModule;

/**
 * 权限缺省服务配置
 * @author chaostone
 * @version $Id: DefaultModule.java Jun 18, 2011 10:21:05 AM chaostone $
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		bind("userService", UserServiceImpl.class);
		bind("groupService", GroupServiceImpl.class);
		bind("authorityService", AuthorityServiceImpl.class);
		bind("menuService", MenuServiceImpl.class);
		bind("userDetailService",DaoUserDetailServiceImpl.class);
		bind("authorityManager",CacheableAuthorityManager.class);
		bind(GroupProfileServiceImpl.class).shortName();
		
		bind("sessionRegistry",DbSessionRegistry.class);
		bind(IdentifierDataResolver.class,CsvDataResolver.class,OqlDataProvider.class).shortName();
	}

}
