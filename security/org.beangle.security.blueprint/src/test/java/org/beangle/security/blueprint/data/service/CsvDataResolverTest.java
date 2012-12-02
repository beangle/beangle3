/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.data.service;

import static org.testng.Assert.assertEquals;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.blueprint.data.model.DataTypeBean;
import org.beangle.security.blueprint.data.model.ProfileFieldBean;
import org.beangle.security.blueprint.data.service.internal.CsvDataResolver;
import org.beangle.security.blueprint.model.RoleBean;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

@Test
public class CsvDataResolverTest {
  CsvDataResolver resolver = new CsvDataResolver();
  ProfileFieldBean field = null;

  @BeforeClass
  public void setUp() {
    DataTypeBean type = new DataTypeBean("role", RoleBean.class.getName());
    type.setKeyName("id");
    type.setProperties("name");
    field = new ProfileFieldBean(1, "role", type, "oql:from Role");
  }

  public void testMarshal() {
    String text = resolver.marshal(field,
        CollectUtils.newArrayList(new RoleBean(1, "role1"), new RoleBean(2, "role2")));
    assertEquals(text, "id;name,1;role1,2;role2");
  }

  public void testUnmarshal() throws Exception {
    List<?> rs = resolver.unmarshal(field, "id;name,1;role1,2;role2");
    List<?> objs = CollectUtils.newArrayList(new RoleBean(1, "role1"), new RoleBean(2, "role2"));
    assertEquals(rs, objs);
  }
}
