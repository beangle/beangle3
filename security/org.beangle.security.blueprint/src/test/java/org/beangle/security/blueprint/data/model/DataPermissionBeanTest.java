/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.model;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class DataPermissionBeanTest {

  public void testValidate() {
    RoleDataPermissionBean permission = new RoleDataPermissionBean();
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
