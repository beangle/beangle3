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
package org.beangle.security.ids;

import org.beangle.commons.inject.bind.AbstractBindModule;
import org.beangle.security.ids.access.AuthorizationFilter;
import org.beangle.security.ids.access.DefaultAccessDeniedHandler;

/**
 * 权限系统web模块bean定义
 *
 * @author chaostone
 * @version $Id: DefaultModule.java Jun 17, 2011 8:01:41 PM chaostone $
 */
public class DefaultModule extends AbstractBindModule {

  @Override
  protected void doBinding() {
    bind(AuthorizationFilter.class, DefaultAccessDeniedHandler.class).shortName();

    bind("securityFilterChain", FilterChainProxy.class).property("filters", list(ref("authorizationFilter")));

    bind(IdsConfig.class, IdsEntryPoint.class).shortName();
  }

}
