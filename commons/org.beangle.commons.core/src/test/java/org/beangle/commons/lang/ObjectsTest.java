/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import static org.testng.Assert.assertTrue;

import org.apache.commons.lang3.ArrayUtils;
import org.testng.annotations.Test;

@Test
public class ObjectsTest {

  public void testNullEquals() {
    assertTrue(ArrayUtils.isEquals(null, null));
    assertTrue(Objects.equals(null, null));
  }
}
