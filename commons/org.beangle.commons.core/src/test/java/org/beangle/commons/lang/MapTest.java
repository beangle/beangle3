/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.testng.annotations.Test;

@Test
public class MapTest {

  /**
   * test map's key set
   */
  public void testRemove() {
    Map<String, String> favorates = CollectUtils.newHashMap();
    favorates.put("john", "sports,music");
    favorates.put("mack", "food");
    favorates.keySet().remove("john");
    org.testng.Assert.assertTrue(favorates.size() == 1);
  }

}
