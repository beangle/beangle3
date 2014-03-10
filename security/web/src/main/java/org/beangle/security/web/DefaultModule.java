/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.web;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.commons.web.access.AccessMonitorFilter;
import org.beangle.commons.web.access.MemAccessMonitor;
import org.beangle.security.auth.ProviderManager;
import org.beangle.security.auth.dao.DaoAuthenticationProvider;
import org.beangle.security.web.access.DefaultAccessDeniedHandler;
import org.beangle.security.web.access.ExceptionTranslationFilter;
import org.beangle.security.web.access.SecurityAccessRequestBuilder;
import org.beangle.security.web.access.intercept.FilterSecurityInterceptor;
import org.beangle.security.web.auth.AuthenticationServiceImpl;
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
import org.beangle.security.web.session.LogoutSessionCleaner;

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
    bind(DefaultAccessDeniedHandler.class, ExceptionTranslationFilter.class, FilterSecurityInterceptor.class)
        .shortName();
    // access monitor
    bind(AccessMonitorFilter.class, MemAccessMonitor.class, SecurityAccessRequestBuilder.class).shortName();

    bind(LoginUrlEntryPoint.class).property("loginUrl", "/login.action").primary();
    bind(LogoutHandlerStack.class).shortName().property("handlers",
        listref(SecurityContextLogoutHandler.class));

    bind("authenticationmanager", ProviderManager.class).property("providers",
        listref(PreauthUserDetailProvider.class, DaoAuthenticationProvider.class));

    bind(AuthenticationServiceImpl.class);

    bind(LogoutSessionCleaner.class).lazy(false);

    bind("securityFilterChain", FilterChainProxy.class).property(
        "filters",
        listref(HttpSessionContextFilter.class, ExceptionTranslationFilter.class,
            UsernamePreauthFilter.class, ConcurrentSessionFilter.class, FilterSecurityInterceptor.class));
  }

}
