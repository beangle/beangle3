/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.beangle.collection.CollectUtils;
import org.beangle.ems.security.model.RoleBean;
import org.beangle.ems.security.profile.model.PropertyMetaBean;
import org.beangle.ems.security.service.impl.CsvDataResolver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class CsvDataResolverTest {
	CsvDataResolver resolver = new CsvDataResolver();
	PropertyMetaBean field = null;

	@BeforeClass
	public void setUp() {
		field = new PropertyMetaBean(1L,"role", RoleBean.class.getName(), "oql:from Role");
		field.setKeyName("id");
		field.setPropertyNames("name");
	}

	public void testMarshal() {
		String text = resolver.marshal(field,
				CollectUtils.newArrayList(new RoleBean(1L, "role1"), new RoleBean(2L, "role2")));
		assertEquals(text, "id;name,1;role1,2;role2");
	}

	public void testUnmarshal() throws Exception {
		List<?> rs = resolver.unmarshal(field, "id;name,1;role1,2;role2");
		List<?> objs = CollectUtils.newArrayList(new RoleBean(1L, "role1"), new RoleBean(2L, "role2"));
		assertEquals(rs, objs);
	}
}
