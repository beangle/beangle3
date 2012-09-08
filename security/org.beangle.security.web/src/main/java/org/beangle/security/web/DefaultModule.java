/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.security.auth.ProviderManager;
import org.beangle.security.auth.dao.DaoAuthenticationProvider;
import org.beangle.security.web.access.DefaultAccessDeniedHandler;
import org.beangle.security.web.access.ExceptionTranslationFilter;
import org.beangle.security.web.access.intercept.FilterSecurityInterceptor;
import org.beangle.security.web.access.log.CachedResourceAccessor;
import org.beangle.security.web.auth.LoginUrlEntryPoint;
import org.beangle.security.web.auth.WebAuthenticationDetailsSource;
import org.beangle.security.web.auth.logout.LogoutHandlerStack;
import org.beangle.security.web.auth.logout.SecurityContextLogoutHandler;
import org.beangle.security.web.auth.preauth.PreauthUserDetailProvider;
import org.beangle.security.web.auth.preauth.UsernameAliveChecker;
import org.beangle.security.web.auth.preauth.UsernamePreauthFilter;
import org.beangle.security.web.auth.preauth.j2ee.RemoteUsernameSource;
import org.beangle.security.web.context.HttpSessionContextFilter;
import org.beangle.security.web.session.ConcurrentSessionFilter;
import org.beangle.security.web.session.WebSessioninfoBuilder;

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
    bind(UsernameAliveChecker.class).primary();
    bind(PreauthUserDetailProvider.class, RemoteUsernameSource.class, UsernamePreauthFilter.class,
        SecurityContextLogoutHandler.class, WebAuthenticationDetailsSource.class).shortName();

    bind(HttpSessionContextFilter.class).shortName();

    // access bean
    bind(DefaultAccessDeniedHandler.class, ExceptionTranslationFilter.class, CachedResourceAccessor.class,
        FilterSecurityInterceptor.class).shortName();

    bind(WebSessioninfoBuilder.class);
    bind(LoginUrlEntryPoint.class).property("loginUrl", "/login.action").primary();
    bind(LogoutHandlerStack.class).shortName().property("handlers",
        listref(SecurityContextLogoutHandler.class));

    bind("authenticationmanager", ProviderManager.class).property("providers",
        listref(PreauthUserDetailProvider.class, DaoAuthenticationProvider.class));

    bind("securityFilterChain", FilterChainProxy.class).property(
        "filters",
        listref(HttpSessionContextFilter.class, ExceptionTranslationFilter.class,
            UsernamePreauthFilter.class, ConcurrentSessionFilter.class, FilterSecurityInterceptor.class));
  }

}
