/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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

  public static void main(String args[]) throws Exception {
    String key = "ABCDEFGH";
    String value = "AABBCCDDEE";
    DESEncrypt desEncrypt = new DESEncrypt(key.getBytes());
    byte encryptText[] = desEncrypt.doEncrypt(value.getBytes());
    System.out.println("doEncrypt - " + toHexString(encryptText));
    System.out.println("doEncrypt - " + new String(encryptText));
    DESDecrypt desDecrypt = new DESDecrypt(key.getBytes());
    byte decryptText[] = desDecrypt.doDecrypt(encryptText);
    System.out.println("doDecrypt - " + new String(decryptText));
    System.out.println("doDecrypt - " + toHexString(decryptText));
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
