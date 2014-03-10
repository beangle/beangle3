/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.security.codec;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SymmetricEncryptHelperTest {
  @Test
  public void testEncryptAndDescrypt() throws Exception {
    String value = "2005122338";
    String encryptText = SymmetricEncryptHelper.toHexString(SymmetricEncryptHelper.encypt(
        "ABCDEFGH".getBytes(), value.getBytes()));
    Assert.assertEquals("37E23121FBA00D576224161E50870CAB", encryptText);
    Assert.assertEquals(
        "2005122338",
        new String(SymmetricEncryptHelper.decrypt("ABCDEFGH".getBytes(),
            SymmetricEncryptHelper.fromHexString(encryptText))));
  }
}
