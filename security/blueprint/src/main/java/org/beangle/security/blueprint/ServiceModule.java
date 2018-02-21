/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.security.blueprint.service.impl.CsvDataResolver;
import org.beangle.security.blueprint.service.impl.DataPermissionServiceImpl;
import org.beangle.security.blueprint.service.impl.IdentifierDataResolver;
import org.beangle.security.blueprint.service.impl.OqlDataProvider;
import org.beangle.security.blueprint.service.impl.ProfileServiceImpl;
import org.beangle.security.blueprint.service.impl.SqlDataProvider;

/**
 * 权限缺省服务配置
 *
 * @author chaostone
 * @version $Id: DefaultModule.java Jun 18, 2011 10:21:05 AM chaostone $
 */
public class ServiceModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    // bind("userService", UserServiceImpl.class);
    // bind("roleService", RoleServiceImpl.class);
    // bind("funcPermissionService", FuncPermissionServiceImpl.class);
    // bind("menuService", MenuServiceImpl.class);

    bind(IdentifierDataResolver.class, CsvDataResolver.class, OqlDataProvider.class, SqlDataProvider.class)
        .shortName();

    bind("restrictionService", DataPermissionServiceImpl.class).property("dataResolver",
        ref(IdentifierDataResolver.class));

    bind("userProfileService", ProfileServiceImpl.class)
        .property("providers",
            map(entry("csv", ref(CsvDataResolver.class)), entry("oql", ref(OqlDataProvider.class)),
                entry("sql", ref(SqlDataProvider.class))))
        .property("dataResolver", ref(IdentifierDataResolver.class));
  }

}
