/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.security.blueprint.data.service;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.Query;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.data.DataResource;
import org.beangle.security.blueprint.data.model.DataPermissionBean;
import org.beangle.security.blueprint.data.model.DataResourceBean;
import org.beangle.security.blueprint.data.service.internal.DataPermissionServiceImpl;
import org.beangle.security.blueprint.model.DimensionBean;
import org.beangle.security.blueprint.model.RoleBean;
import org.beangle.security.blueprint.model.UserProfileBean;
import org.beangle.security.blueprint.service.impl.CsvDataResolver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class ApplyTest {

  DataPermissionServiceImpl dataPermissionService = new DataPermissionServiceImpl();

  @BeforeClass
  public void setUp() {
    CsvDataResolver resolver = new CsvDataResolver();
    dataPermissionService.setDataResolver(resolver);
    //dataPermissionService.getProviders().put(resolver.getName(), resolver);
  }

  public void testApply() {
    DataResource resource = new DataResourceBean(User.class.getName(), "user");
    DataPermissionBean permission = new DataPermissionBean(new RoleBean(), resource, Resource.AllActions);
    permission.setFilters("exists(from {alias}.members as m where m.role in(:roles))");

    DimensionBean property = new DimensionBean(1, "roles", RoleBean.class.getName(), "oql:from " + Role.class);
    property.setKeyName("id");
    property.setProperties("name");

    UserProfileBean upb = new UserProfileBean();
    upb.setProperty(property, "id;name,1;role1");

    OqlBuilder<User> builder = OqlBuilder.from(User.class);
    dataPermissionService.apply(builder, permission, upb);
    Query<User> query = builder.build();
    String statement = query.getStatement();
    Map<?, ?> params = query.getParams();
    Assert.assertEquals(statement, "select user from org.beangle.security.blueprint.User user "
        + "where ((exists(from user.members as m where m.role in(:roles))))");
    Assert.assertNotNull(params);
    Assert.assertEquals(params.size(), 1);
    Assert.assertEquals(params.get("roles"), CollectUtils.newArrayList(new RoleBean(1,"role1", "role1")));
  }
}
