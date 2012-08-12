/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.service;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.blueprint.data.model.DataFieldBean;
import org.beangle.security.blueprint.data.service.internal.CsvDataResolver;
import org.beangle.security.blueprint.model.RoleBean;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class CsvDataResolverTest {
  CsvDataResolver resolver = new CsvDataResolver();
  DataFieldBean field = null;

  @BeforeClass
  public void setUp() {
    field = new DataFieldBean(1L, "role", RoleBean.class.getName(), "oql:from Role");
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
