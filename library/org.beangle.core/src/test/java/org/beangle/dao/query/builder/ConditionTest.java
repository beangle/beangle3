/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.query.builder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.Test;

@Test
public class ConditionTest {

	public void testGetNamedParams() {
		Condition condition = new Condition("std.id =:stAd_id1 and std.name like :name");
		for (String name : condition.getParamNames()) {
			System.out.println(name);
		}
	}

	public void testVarArgs() {
		Condition c = new Condition("entity.code =:code  entity.id in (:ids)", "aa", new Long[] { 1L });
		assertEquals(2, ConditionUtils.getParamMap(c).size());

		Condition c1 = new Condition("entity.id in (:ids)", new Long[] { 1L, 2L });
		assertEquals(1, ConditionUtils.getParamMap(c1).size());
		assertTrue((ConditionUtils.getParamMap(c1).get("ids").getClass().isArray()));
	}

}
