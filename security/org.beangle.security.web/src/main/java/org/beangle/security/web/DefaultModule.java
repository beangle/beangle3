/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web;

import org.beangle.security.web.access.DefaultAccessDeniedHandler;
import org.beangle.security.web.access.ExceptionTranslationFilter;
import org.beangle.security.web.access.intercept.FilterSecurityInterceptor;
import org.beangle.security.web.access.log.AccessLogFilter;
import org.beangle.security.web.access.log.CachedResourceAccessor;
import org.beangle.security.web.auth.AnonymousFilter;
import org.beangle.security.web.auth.WebAuthenticationDetailsSource;
import org.beangle.security.web.auth.logout.SecurityContextLogoutHandler;
import org.beangle.security.web.auth.preauth.PreauthUserDetailProvider;
import org.beangle.security.web.auth.preauth.UsernamePreauthFilter;
import org.beangle.security.web.auth.preauth.j2ee.RemoteUsernameSource;
import org.beangle.security.web.context.HttpSessionContextIntegrationFilter;
import org.beangle.security.web.session.ConcurrentSessionFilter;
import org.beangle.spring.bind.AbstractBindModule;

/**
 * 权限系统web模块bean定义
 * 
 * @author chaostone
 * @version $Id: DefaultModule.java Jun 17, 2011 8:01:41 PM chaostone $
 */
public class DefaultModule extends AbstractBindModule {

	@Override
	protected void doBinding() {
		// session filter
		bind(ConcurrentSessionFilter.class).shortName();

		// auth bean
		bind(PreauthUserDetailProvider.class, RemoteUsernameSource.class, UsernamePreauthFilter.class,
				AnonymousFilter.class, SecurityContextLogoutHandler.class,
				WebAuthenticationDetailsSource.class).shortName();

		bind(HttpSessionContextIntegrationFilter.class).shortName();

		// access bean
		bind(DefaultAccessDeniedHandler.class, ExceptionTranslationFilter.class,
				CachedResourceAccessor.class, AccessLogFilter.class, FilterSecurityInterceptor.class)
				.shortName();

	}

}
