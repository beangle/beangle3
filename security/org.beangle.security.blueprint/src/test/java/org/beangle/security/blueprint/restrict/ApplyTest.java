/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.query.Query;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.model.GroupBean;
import org.beangle.security.blueprint.restrict.model.RestrictEntityBean;
import org.beangle.security.blueprint.restrict.model.RestrictFieldBean;
import org.beangle.security.blueprint.restrict.model.RestrictPatternBean;
import org.beangle.security.blueprint.restrict.model.UserRestrictionBean;
import org.beangle.security.blueprint.restrict.service.CsvDataResolver;
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
		RestrictField field = new RestrictFieldBean("groups", GroupBean.class.getName(),
				"id;name,1;group1,2;group2");
		field.setKeyName("id");
		field.setPropertyNames("name");
		RestrictEntity entity = new RestrictEntityBean("user", User.class);
		entity.getFields().add(field);
		RestrictPattern pattern = new RestrictPatternBean(entity,
				"exists(from {alias}.groups as g where g.group in(:groups))");
		Restriction restriction = new UserRestrictionBean(null, pattern);
		restriction.setItem(field, "id;name,1;group1");
		OqlBuilder<User> builder = OqlBuilder.from(User.class);
		restrictionService.apply(builder, CollectUtils.newArrayList(restriction));
		Query<User> query = builder.build();
		String statement = query.getStatement();
		Map<?, ?> params = query.getParams();
		Assert.assertEquals(statement, "select user from org.beangle.security.blueprint.User user "
				+ "where ((exists(from user.groups as g where g.group in(:groups0))))");
		Assert.assertNotNull(params);
		Assert.assertEquals(params.size(), 1);
		Assert.assertEquals(params.get("groups0"), CollectUtils.newArrayList(new GroupBean(1L, "group1")));
	}
}
