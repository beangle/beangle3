/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
import org.beangle.security.blueprint.data.model.DataFieldBean;
import org.beangle.security.blueprint.data.model.UserProfileBean;
import org.beangle.security.blueprint.data.service.internal.CsvDataResolver;
import org.beangle.security.blueprint.data.service.internal.DataPermissionServiceImpl;
import org.beangle.security.blueprint.model.RoleBean;
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
    dataPermissionService.getProviders().put(resolver.getName(), resolver);
  }

  public void testApply() {
    DataResource resource = new DataResourceBean(User.class.getName(), "user");
    DataPermissionBean permission = new DataPermissionBean(new RoleBean(), resource, Resource.AllActions);
    permission.setFilters("exists(from {alias}.members as m where m.role in(:roles))");

    DataFieldBean property = new DataFieldBean(1L, "roles", RoleBean.class.getName(), "oql:from "
        + Role.class);
    property.setKeyName("id");
    property.setPropertyNames("name");

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
    Assert.assertEquals(params.get("roles"), CollectUtils.newArrayList(new RoleBean(1L, "role1")));
  }
}
