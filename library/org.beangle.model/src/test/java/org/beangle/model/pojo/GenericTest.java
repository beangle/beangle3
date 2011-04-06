/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

import static org.testng.Assert.assertNotNull;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.example.Skill;
import org.testng.annotations.Test;

@Test
public class GenericTest {

	public void testInspect() throws Exception, NoSuchMethodException {
		Class<?> sc = Skill.class;
		Type gs = sc.getGenericSuperclass();
		System.out.println(gs);
		Method a = Skill.class.getMethod("getId");
		System.out.println(a.getReturnType());

		List<Skill> skills = CollectUtils.newArrayList();
		skills.add(new Skill());
		process(skills);
	}

	private void process(List<Skill> skills) {
		for (Skill s : skills) {
			assertNotNull(s);
		}
	}
}
