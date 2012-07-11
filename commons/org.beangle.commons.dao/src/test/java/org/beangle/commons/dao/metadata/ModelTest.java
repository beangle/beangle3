/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.metadata;

import static org.testng.Assert.assertNotNull;

import org.testng.annotations.Test;

@Test
public class ModelTest {

  public void test() {
    SimpleBean entity = Model.newInstance(SimpleBean.class);
    assertNotNull(entity);
  }
}
