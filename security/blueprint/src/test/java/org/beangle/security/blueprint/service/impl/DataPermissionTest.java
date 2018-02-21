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

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.blueprint.model.DataPermission;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class DataPermissionTest {

  public void testValidate() {
    DataPermission permission = new DataPermission();
    permission.setRestrictions("{\"user\":admin,\"resource\"=\"/home/resource\"}");
    Map<String, String> datas = CollectUtils.newHashMap();
    datas.put("user", "admin");
    Assert.assertFalse(permission.validate(datas));

    datas.put("resource", "/home/user");
    Assert.assertFalse(permission.validate(datas));

    datas.put("resource", "/home/resource");
    Assert.assertTrue(permission.validate(datas));
  }
}
