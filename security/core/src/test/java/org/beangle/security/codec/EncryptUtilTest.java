/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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

/**
 * @author chaostone
 */
public class EncryptUtilTest {

  @Test
  public void testencode() throws Exception {
    String password = "1";
    String encrypted = "c4ca4238a0b923820dcc509a6f75849b";
    Assert.assertEquals(EncryptUtil.encode(password, "MD5"), encrypted);
    Assert.assertEquals(EncryptUtil.encode("830826"), "92f9c92d38fd87bdaeae685f9be36183");
  }

}
