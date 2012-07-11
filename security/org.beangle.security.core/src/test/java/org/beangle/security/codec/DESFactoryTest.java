/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.codec;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class DESFactoryTest {

  @Test
  public void testDes() throws Exception {
    String value = "2323";
    DESFactory f = new DESFactory("ABCDEFGH".getBytes(), "ABCDEFGH".getBytes());
    String encryptText = DESFactory.toHexString(f.encypt(value.getBytes()));
    assertEquals("FDA6BEA74ABAFF69", encryptText);
    assertEquals(value, new String(f.decrypt(DESFactory.fromHexString(encryptText))));
  }
}
