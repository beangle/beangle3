/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.query.builder;

import java.util.List;
import java.util.Map;

import org.beangle.collection.CollectUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ConditionUtilsTest {

	@BeforeClass
	protected void setUp() throws Exception {

	}

	@Test
	public void testGetParamMap() throws Exception {
		List<Condition> conditions = CollectUtils.newArrayList();
		conditions.add(new Condition("std.id=:std_id", 1L));
		Map<String, Object> params = ConditionUtils.getParamMap(conditions);
		for (Map.Entry<String, Object> entity : params.entrySet()) {
			System.out.println(entity.getKey() + "->" + entity.getValue());
		}
	}

	@Test
	public void testToQueryString() {
		List<Condition> conditions = CollectUtils.newArrayList();
		conditions.add(new Condition("user.id=:user_id", 1L));
		conditions.add(new Condition("user.name=:std_name", "name"));
		Assert.assertEquals("(user.id=:user_id) and (user.name=:std_name)",
				ConditionUtils.toQueryString(conditions));
	}

}
