/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.metadata;

import static org.testng.Assert.assertNotNull;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ModelTest {

  public void testNewInstance() {
    SimpleBean entity = Model.newInstance(SimpleBean.class);
    assertNotNull(entity);
  }

  public void testPopulate() {
    Map<String, Object> params = CollectUtils.newHashMap();
    params.put("contractInfo.id", "");
    ManagerEmployer employer = (ManagerEmployer) Model.getPopulator().populate(new ManagerEmployer(),
        ManagerEmployer.class.getName(), params);
    Assert.assertNull(employer.getContractInfo());
  }
}
