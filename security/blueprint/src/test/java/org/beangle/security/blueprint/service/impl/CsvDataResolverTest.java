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

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.blueprint.model.Dimension;
import org.beangle.security.blueprint.model.Role;
import org.beangle.security.blueprint.service.impl.CsvDataResolver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class CsvDataResolverTest {
  CsvDataResolver resolver = new CsvDataResolver();
  Dimension field = null;

  @BeforeClass
  public void setUp() {
    field = new Dimension(1, "role", Role.class.getName(), "oql:from Role");
    field.setKeyName("id");
    field.setProperties("name");
  }

  public void testMarshal() {
    String text = resolver.marshal(field,
        CollectUtils.newArrayList(new Role(1, "role1", "role1"), new Role(2, "role2", "role2")));
    assertEquals(text, "id,name\n1,role1\n2,role2");
  }

  public void testUnmarshal() throws Exception {
    List<?> rs = resolver.unmarshal(field, "id,name\n1,role1\n2,role2");
    List<?> objs = CollectUtils.newArrayList(new Role(1, "role1", "role1"), new Role(2, "role2", "role2"));
    assertEquals(rs, objs);
  }
}
