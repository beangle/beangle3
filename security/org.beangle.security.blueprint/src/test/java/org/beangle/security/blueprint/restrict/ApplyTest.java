/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.query.Query;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.model.RoleBean;
import org.beangle.security.blueprint.profile.model.PropertyMetaBean;
import org.beangle.security.blueprint.profile.model.UserProfileBean;
import org.beangle.security.blueprint.profile.service.internal.CsvDataResolver;
import org.beangle.security.blueprint.restrict.model.RestrictEntityBean;
import org.beangle.security.blueprint.restrict.model.RestrictionBean;
import org.beangle.security.blueprint.restrict.service.RestrictionServiceImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class ApplyTest {

  RestrictionServiceImpl restrictionService = new RestrictionServiceImpl();

  @BeforeClass
  public void setUp() {
    CsvDataResolver resolver = new CsvDataResolver();
    restrictionService.setDataResolver(resolver);
    restrictionService.getProviders().put(resolver.getName(), resolver);
  }

  public void testApply() {
    RestrictEntity entity = new RestrictEntityBean("user", User.class);
    Restriction restriction = new RestrictionBean(entity,
        "exists(from {alias}.members as m where m.role in(:roles))");

    PropertyMetaBean property = new PropertyMetaBean(1L, "roles", RoleBean.class.getName(), "oql:from "
        + Role.class);
    property.setKeyName("id");
    property.setPropertyNames("name");

    UserProfileBean upb = new UserProfileBean();
    upb.setProperty(property, "id;name,1;role1");

    OqlBuilder<User> builder = OqlBuilder.from(User.class);
    restrictionService.apply(builder, CollectUtils.newArrayList(restriction), upb);
    Query<User> query = builder.build();
    String statement = query.getStatement();
    Map<?, ?> params = query.getParams();
    Assert.assertEquals(statement, "select user from org.beangle.security.blueprint.User user "
        + "where ((exists(from user.members as m where m.role in(:roles0))))");
    Assert.assertNotNull(params);
    Assert.assertEquals(params.size(), 1);
    Assert.assertEquals(params.get("roles0"), CollectUtils.newArrayList(new RoleBean(1L, "role1")));
  }
}
