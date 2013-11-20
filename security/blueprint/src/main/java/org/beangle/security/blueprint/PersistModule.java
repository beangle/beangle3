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

import org.beangle.commons.entity.orm.AbstractPersistModule;
import org.beangle.security.blueprint.data.model.DataFieldBean;
import org.beangle.security.blueprint.data.model.DataPermissionBean;
import org.beangle.security.blueprint.data.model.DataResourceBean;
import org.beangle.security.blueprint.function.model.FuncPermissionBean;
import org.beangle.security.blueprint.function.model.FuncResourceBean;
import org.beangle.security.blueprint.model.FieldBean;
import org.beangle.security.blueprint.model.MemberBean;
import org.beangle.security.blueprint.model.RoleBean;
import org.beangle.security.blueprint.model.RolePropertyBean;
import org.beangle.security.blueprint.model.UserBean;
import org.beangle.security.blueprint.model.UserProfileBean;
import org.beangle.security.blueprint.model.UserPropertyBean;
import org.beangle.security.blueprint.nav.model.MenuBean;
import org.beangle.security.blueprint.nav.model.MenuProfileBean;
import org.beangle.security.blueprint.session.model.SessionProfileBean;

public class PersistModule extends AbstractPersistModule {

  @SuppressWarnings("unchecked")
  protected void doConfig() {

    defaultCache("beangle", "read-write");

    add(FuncPermissionBean.class, MemberBean.class, UserBean.class, UserProfileBean.class,
        UserPropertyBean.class, SessionProfileBean.class);

    add(RoleBean.class, FuncResourceBean.class, DataPermissionBean.class, DataResourceBean.class,
        DataFieldBean.class, RolePropertyBean.class, FieldBean.class, MenuBean.class, MenuProfileBean.class)
        .cacheable();

    cache()
        .add(collection(MenuBean.class, "children", "resources"), collection(RoleBean.class, "properties"));

  }

}
