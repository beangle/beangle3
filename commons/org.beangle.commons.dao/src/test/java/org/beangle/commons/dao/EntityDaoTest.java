/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao;

import java.util.Collection;

import org.beangle.commons.collection.CollectUtils;
import org.testng.annotations.Test;

@Test
public class EntityDaoTest {

  public void testFoo() {
    Collection<Object> aa = CollectUtils.newArrayList();
    new EntityDaoTest().saveOrUpdate(aa);
  }

  public void saveOrUpdate(Object a) {

  }
}
