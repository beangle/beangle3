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

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESDecrypt {

  public DESDecrypt(byte desKey[]) {
    this.desKey = desKey;
  }

  public byte[] doDecrypt(byte encryptText[]) throws Exception {
    SecureRandom sr = new SecureRandom();
    byte rawKeyData[] = desKey;
    DESKeySpec dks = new DESKeySpec(rawKeyData);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    javax.crypto.SecretKey key = keyFactory.generateSecret(dks);
    Cipher cipher = Cipher.getInstance("DES");
    cipher.init(2, key, sr);
    byte encryptedData[] = encryptText;
    byte decryptedData[] = cipher.doFinal(encryptedData);
    return decryptedData;
  }

  public static String toHexString(byte value[]) {
    String newString = "";
    for (int i = 0; i < value.length; i++) {
      byte b = value[i];
      String str = Integer.toHexString(b);
      if (str.length() > 2) str = str.substring(str.length() - 2);
      if (str.length() < 2) str = "0" + str;
      newString = newString + str;
    }

    return newString.toUpperCase();
  }

  private byte desKey[];
}
