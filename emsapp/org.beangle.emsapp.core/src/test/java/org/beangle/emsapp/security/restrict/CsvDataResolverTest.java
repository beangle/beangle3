/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.restrict;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.beangle.collection.CollectUtils;
import org.beangle.emsapp.security.model.GroupBean;
import org.beangle.emsapp.security.profile.model.UserPropertyMetaBean;
import org.beangle.emsapp.security.service.CsvDataResolver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class CsvDataResolverTest {
	CsvDataResolver resolver = new CsvDataResolver();
	UserPropertyMetaBean field = null;

	@BeforeClass
	public void setUp() {
		field = new UserPropertyMetaBean(1L,"group", GroupBean.class.getName(), "oql:from Group");
		field.setKeyName("id");
		field.setPropertyNames("name");
	}

	public void testMarshal() {
		String text = resolver.marshal(field,
				CollectUtils.newArrayList(new GroupBean(1L, "group1"), new GroupBean(2L, "group2")));
		assertEquals(text, "id;name,1;group1,2;group2");
	}

	public void testUnmarshal() throws Exception {
		List<?> rs = resolver.unmarshal(field, "id;name,1;group1,2;group2");
		List<?> objs = CollectUtils.newArrayList(new GroupBean(1L, "group1"), new GroupBean(2L, "group2"));
		assertEquals(rs, objs);
	}
}
