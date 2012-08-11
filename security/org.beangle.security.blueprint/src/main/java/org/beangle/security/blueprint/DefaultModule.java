/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.security.blueprint.data.service.internal.CsvDataResolver;
import org.beangle.security.blueprint.data.service.internal.DataPermissionServiceImpl;
import org.beangle.security.blueprint.data.service.internal.IdentifierDataResolver;
import org.beangle.security.blueprint.data.service.internal.OqlDataProvider;
import org.beangle.security.blueprint.function.service.internal.CacheableAuthorityManager;
import org.beangle.security.blueprint.function.service.internal.FuncPermissionServiceImpl;
import org.beangle.security.blueprint.nav.service.MenuServiceImpl;
import org.beangle.security.blueprint.service.internal.DaoUserDetailServiceImpl;
import org.beangle.security.blueprint.service.internal.RoleServiceImpl;
import org.beangle.security.blueprint.service.internal.UserServiceImpl;
import org.beangle.security.blueprint.session.service.SessionProfileServiceImpl;

/**
 * 权限缺省服务配置
 * 
 * @author chaostone
 * @version $Id: DefaultModule.java Jun 18, 2011 10:21:05 AM chaostone $
 */
public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind("userService", UserServiceImpl.class);
    bind("roleService", RoleServiceImpl.class);
    bind("authorityService", FuncPermissionServiceImpl.class);
    bind("menuService", MenuServiceImpl.class);
    bind("userDetailService", DaoUserDetailServiceImpl.class);
    bind("authorityManager", CacheableAuthorityManager.class);
    bind(SessionProfileServiceImpl.class).shortName();

    bind(IdentifierDataResolver.class, CsvDataResolver.class, OqlDataProvider.class).shortName();

    bind("restrictionService", DataPermissionServiceImpl.class).property("providers",
        map(entry("csv", ref(CsvDataResolver.class)), entry("oql", ref(OqlDataProvider.class)))).property(
        "dataResolver",ref(IdentifierDataResolver.class));
  }

}
