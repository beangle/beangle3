/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import org.beangle.commons.context.inject.AbstractBindModule;
import org.beangle.security.blueprint.nav.service.MenuServiceImpl;
import org.beangle.security.blueprint.profile.service.internal.CsvDataResolver;
import org.beangle.security.blueprint.profile.service.internal.IdentifierDataResolver;
import org.beangle.security.blueprint.profile.service.internal.OqlDataProvider;
import org.beangle.security.blueprint.restrict.service.RestrictionServiceImpl;
import org.beangle.security.blueprint.service.internal.AuthorityServiceImpl;
import org.beangle.security.blueprint.service.internal.CacheableAuthorityManager;
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
    bind("authorityService", AuthorityServiceImpl.class);
    bind("menuService", MenuServiceImpl.class);
    bind("userDetailService", DaoUserDetailServiceImpl.class);
    bind("authorityManager", CacheableAuthorityManager.class);
    bind(SessionProfileServiceImpl.class).shortName();

    bind(IdentifierDataResolver.class, CsvDataResolver.class, OqlDataProvider.class).shortName();

    bind("restrictionService", RestrictionServiceImpl.class).property("providers",
        map(entry("csv", CsvDataResolver.class), entry("oql", OqlDataProvider.class))).property(
        "dataResolver",IdentifierDataResolver.class);
  }

}
