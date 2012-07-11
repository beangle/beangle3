/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.codec;

import java.security.SecureRandom;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DESFactory {

  public DESFactory(byte encKey[], byte desKey[]) throws Exception {
    SecureRandom sr = new SecureRandom();
    byte rawKeyData[] = encKey;
    DESKeySpec dks = new DESKeySpec(rawKeyData);
    SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
    javax.crypto.SecretKey key = keyFactory.generateSecret(dks);
    enCipher = Cipher.getInstance("DES");
    enCipher.init(1, key, sr);
    rawKeyData = desKey;
    dks = new DESKeySpec(rawKeyData);
    key = keyFactory.generateSecret(dks);
    deCipher = Cipher.getInstance("DES");
    deCipher.init(2, key, sr);
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

  public static byte[] fromHexString(String value) {
    byte rs[] = new byte[value.length() / 2];
    for (int i = 0; i < value.length(); i += 2) {
      String b = value.substring(i, i + 2);
      rs[i / 2] = Integer.valueOf(b, 16).byteValue();
    }

    return rs;
  }

  public byte[] encypt(byte value[]) {
    try {
      return enCipher.doFinal(value);
    } catch (IllegalBlockSizeException e) {
      throw new RuntimeException(e);
    } catch (BadPaddingException e) {
      throw new RuntimeException(e);
    }
  }

  public byte[] decrypt(byte value[]) {
    try {
      return deCipher.doFinal(value);
    } catch (IllegalBlockSizeException e) {
      throw new RuntimeException(e);
    } catch (BadPaddingException e) {
      throw new RuntimeException(e);
    }
  }

  private Cipher enCipher;
  private Cipher deCipher;
}
