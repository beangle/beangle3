/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

@Test
public class DesDecryptTest {

  private static final Logger logger = LoggerFactory.getLogger(DesDecryptTest.class);

  public void test() throws Exception {
    String key = "ABCDEFGH";
    String value = "AABBCCDDEE";
    DESEncrypt desEncrypt = new DESEncrypt(key.getBytes());
    byte encryptText[] = desEncrypt.doEncrypt(value.getBytes());
    logger.debug("doEncrypt - " + DESDecrypt.toHexString(encryptText));
    logger.debug("doEncrypt - " + new String(encryptText));
    DESDecrypt desDecrypt = new DESDecrypt(key.getBytes());
    byte decryptText[] = desDecrypt.doDecrypt(encryptText);
    logger.debug("doDecrypt - " + new String(decryptText));
    logger.debug("doDecrypt - " + DESDecrypt.toHexString(decryptText));

  }
}
