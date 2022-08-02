/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.app.security;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.security.ids.CasConfig;
import org.beangle.security.ids.CasEntryPoint;
import org.beangle.security.ids.SecurityFilterChain;
import org.beangle.security.ids.WebSecurityContextBuilder;
import org.beangle.security.ids.access.DefaultAccessDeniedHandler;
import org.beangle.security.ids.session.CookieSessionIdReader;
import org.beangle.ems.app.Ems;
import org.beangle.ems.app.security.service.ActionRequestConvertor;
import org.beangle.ems.app.security.service.RemoteAuthorizer;
import org.beangle.ems.app.security.service.CasHttpSessionRepo;
import org.beangle.ems.app.security.service.IdentifierDataResolver;
import org.beangle.ems.app.security.service.RemoteAuthorityService;

public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(DefaultAccessDeniedHandler.class).shortName();

    bind("securityContextBuilder", WebSecurityContextBuilder.class);

    bind("securityFilterChain", SecurityFilterChain.class);

    bind(CasConfig.class).property("casServer", Ems.getInstance().getCas());

    bind(CasEntryPoint.class).shortName();

    bind(RemoteAuthorizer.class);

    bind(CookieSessionIdReader.class).constructor("URP_SID");

    bind(ActionRequestConvertor.class);

    bind(CasHttpSessionRepo.class);

    bind("idDataResolver", IdentifierDataResolver.class);
    bind("remoteAuthorityService", RemoteAuthorityService.class).property("dataResolver",
        ref("idDataResolver"));

  }

}
