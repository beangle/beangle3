/*
 * gle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, gle Software.
 *
 * gle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * gle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with gle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.service.impl;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.Query;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.blueprint.model.DataPermission;
import org.beangle.security.blueprint.model.DataResource;
import org.beangle.security.blueprint.model.DataResource;
import org.beangle.security.blueprint.model.Dimension;
import org.beangle.security.blueprint.model.Resource;
import org.beangle.security.blueprint.model.Role;
import org.beangle.security.blueprint.model.Role;
import org.beangle.security.blueprint.model.User;
import org.beangle.security.blueprint.model.UserProfile;
import org.beangle.security.blueprint.service.impl.CsvDataResolver;
import org.beangle.security.blueprint.service.impl.DataPermissionServiceImpl;
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
    // dataPermissionService.getProviders().put(resolver.getName(), resolver);
  }

  public void testApply() {
    DataResource resource = new DataResource(User.class.getName(), "user");
    DataPermission permission = new DataPermission(new Role(), resource, Resource.AllActions);
    permission.setFilters("exists(from {alias}.members as m where m.role in(:roles))");

    Dimension property = new Dimension(1, "roles", Role.class.getName(), "oql:from " + Role.class);
    property.setKeyName("id");
    property.setProperties("name");

    UserProfile upb = new UserProfile();
    upb.getProperties().put(property, "id,name\n1,role1");

    OqlBuilder<User> builder = OqlBuilder.from(User.class);
    dataPermissionService.apply(builder, permission, upb);
    Query<User> query = builder.build();
    String statement = query.getStatement();
    Map<?, ?> params = query.getParams();
    Assert.assertEquals(statement, "select user from org.beangle.security.blueprint.model.User user "
        + "where ((exists(from user.members as m where m.role in(:roles))))");
    Assert.assertNotNull(params);
    Assert.assertEquals(params.size(), 1);
    Assert.assertEquals(params.get("roles"), CollectUtils.newArrayList(new Role(1, "role1", "role1")));
  }
}
