/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.security.blueprint;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.security.blueprint.data.service.internal.CsvDataResolver;
import org.beangle.security.blueprint.data.service.internal.DataPermissionServiceImpl;
import org.beangle.security.blueprint.data.service.internal.IdentifierDataResolver;
import org.beangle.security.blueprint.data.service.internal.OqlDataProvider;
import org.beangle.security.blueprint.data.service.internal.SqlDataProvider;
import org.beangle.security.blueprint.function.service.internal.CacheableAuthorityManager;
import org.beangle.security.blueprint.function.service.internal.FuncPermissionServiceImpl;
import org.beangle.security.blueprint.nav.service.MenuServiceImpl;
import org.beangle.security.blueprint.service.internal.DaoUserDetailServiceImpl;
import org.beangle.security.blueprint.service.internal.RoleServiceImpl;
import org.beangle.security.blueprint.service.internal.UserServiceImpl;
import org.beangle.security.blueprint.session.service.WebSessioninfoBuilder;
import org.beangle.security.blueprint.session.service.internal.SessionProfileServiceImpl;

/**
 * 权限缺省服务配置
 * 
 * @author chaostone
 * @version $Id: DefaultModule.java Jun 18, 2011 10:21:05 AM chaostone $
 */
public class ServiceModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind("userService", UserServiceImpl.class);
    bind("roleService", RoleServiceImpl.class);
    bind("funcPermissionService", FuncPermissionServiceImpl.class);
    bind("menuService", MenuServiceImpl.class);
    bind("userDetailService", DaoUserDetailServiceImpl.class);
    bind("authorityManager", CacheableAuthorityManager.class);
    bind(SessionProfileServiceImpl.class).shortName();
    bind(WebSessioninfoBuilder.class);
    
    bind(IdentifierDataResolver.class, CsvDataResolver.class, OqlDataProvider.class, SqlDataProvider.class)
        .shortName();

    bind("restrictionService", DataPermissionServiceImpl.class).property(
        "providers",
        map(entry("csv", ref(CsvDataResolver.class)), entry("oql", ref(OqlDataProvider.class)),
            entry("sql", ref(SqlDataProvider.class)))).property("dataResolver",
        ref(IdentifierDataResolver.class));
  }

}
