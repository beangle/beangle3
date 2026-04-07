/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.app.util;

import org.beangle.commons.lang.Charsets;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.SecureRandom;
import java.text.Normalizer;
import java.util.Base64;
import java.util.Random;

/**
 * Password-Based Encryption utility. Supports fixed and random salt/IV.
 */
public final class PBEEncryptors {

  /**
   * The default salt size
   */
  public static final int DEFAULT_SALT_SIZE = 8;

  /**
   * The default IV size
   */
  public static final int DEFAULT_IV_SIZE = 16;

  /**
   * Generate key iterations
   */
  public static final int DEFAULT_ITERATIONS = 1000;

  /**
   * The default PBE algo
   */
  public static final String DEFAULT_ALGORITHM = "PBEWithMD5AndDES";

  private PBEEncryptors() {
  }

  /**
   * Creates encryptor/decryptor with fixed salt and IV.
   *
   * @param algorithm the PBE algorithm
   * @param password  the plain password
   * @param salt      the fixed salt bytes
   * @param iv        the fixed initialization vector
   * @return the FixedPBEEncryptor
   */
  public static FixedPBEEncryptor fixed(String algorithm, String password, byte[] salt, byte[] iv)
      throws Exception {
    Cipher encryptor = Cipher.getInstance(algorithm);
    Cipher decryptor = Cipher.getInstance(algorithm);
    int saltSize = getSaltSize(encryptor);
    int ivSize = getIVSize(encryptor);

    if (salt.length < saltSize) {
      throw new IllegalArgumentException("salt contains " + salt.length + " bytes, less than " + saltSize + ".");
    }
    if (iv.length < ivSize) {
      throw new IllegalArgumentException("iv contains " + iv.length + " bytes, less than " + ivSize + ".");
    }

    byte[] saltBytes = salt.length == saltSize ? salt : left(salt, saltSize);
    byte[] ivBytes = iv.length == ivSize ? iv : left(iv, ivSize);
    SecretKey key = buildKey(algorithm, password);
    PBEParameterSpec parameter = buildParameter(saltBytes, ivBytes, DEFAULT_ITERATIONS);
    init(key, parameter, encryptor, decryptor);
    return new FixedPBEEncryptor(encryptor, decryptor);
  }

  /**
   * Creates RandomPBEEncryptor with default algorithm.
   */
  public static RandomPBEEncryptor random(String password) throws Exception {
    return random(DEFAULT_ALGORITHM, password);
  }

  /**
   * Creates encryptor/decryptor with random salt and IV per encryption.
   *
   * @param algorithm the PBE algorithm
   * @param password  the plain password
   * @return the RandomPBEEncryptor
   */
  public static RandomPBEEncryptor random(String algorithm, String password) throws Exception {
    Cipher encryptor = Cipher.getInstance(algorithm);
    Cipher decryptor = Cipher.getInstance(algorithm);
    int saltSize = getSaltSize(encryptor);
    int ivSize = getIVSize(encryptor);
    return new RandomPBEEncryptor(encryptor, decryptor, buildKey(algorithm, password), saltSize, ivSize);
  }

  /**
   * Generates random salt and IV strings for the given algorithm.
   */
  public static SaltAndIv generateSaltAndIv(String algorithm) throws Exception {
    Cipher encryptor = Cipher.getInstance(algorithm);
    char[] salt = new char[getSaltSize(encryptor)];
    char[] iv = new char[getIVSize(encryptor)];
    Random rnd = new Random();
    for (int i = 0; i < salt.length; i++) {
      salt[i] = nextPrintableChar(rnd);
    }
    for (int i = 0; i < iv.length; i++) {
      iv[i] = nextPrintableChar(rnd);
    }
    return new SaltAndIv(new String(salt), new String(iv));
  }

  /**
   * Converts a plain password to PBE key format. Does not retain plaintext.
   *
   * @param algorithm the PBE algorithm
   * @param password  the plain password
   * @return the SecretKey
   */
  public static SecretKey buildKey(String algorithm, String password) throws Exception {
    return SecretKeyFactory.getInstance(algorithm).generateSecret(new PBEKeySpec(normalize(password)));
  }

  /**
   * Builds PBE parameters from salt and IV.
   *
   * @param salt       the salt bytes
   * @param iv         the initialization vector bytes
   * @param iterations the key derivation iteration count
   * @return the PBEParameterSpec
   */
  public static PBEParameterSpec buildParameter(byte[] salt, byte[] iv, int iterations) {
    return new PBEParameterSpec(salt, iterations, new IvParameterSpec(iv));
  }

  /**
   * Salt and IV strings from {@link #generateSaltAndIv(String)}.
   */
  public record SaltAndIv(String salt, String iv) {
  }

  /**
   * PBE encryptor with fixed salt and IV.
   */
  public static final class FixedPBEEncryptor implements PBEEncryptor {

    private final Cipher encryptor;
    private final Cipher decryptor;

    FixedPBEEncryptor(Cipher encryptor, Cipher decryptor) {
      this.encryptor = encryptor;
      this.decryptor = decryptor;
    }

    @Override
    public String encrypt(String message) {
      try {
        byte[] encrypted = encryptor.doFinal(message.getBytes(Charsets.UTF_8));
        return new String(Base64.getEncoder().encode(encrypted));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public String decrypt(String message) {
      try {
        byte[] encrypted = Base64.getDecoder().decode(message);
        return new String(decryptor.doFinal(encrypted));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
  }

  /**
   * PBE encryptor that generates random salt and IV per encryption.
   */
  public static final class RandomPBEEncryptor implements PBEEncryptor {

    /**
     * Key derivation iteration count.
     */
    public int iterations = DEFAULT_ITERATIONS;

    private final Cipher encryptor;
    private final Cipher decryptor;
    private final SecretKey key;
    private final int saltSize;
    private final int ivSize;
    private final SecureRandom random = new SecureRandom();

    RandomPBEEncryptor(Cipher encryptor, Cipher decryptor, SecretKey key, int saltSize, int ivSize) {
      this.encryptor = encryptor;
      this.decryptor = decryptor;
      this.key = key;
      this.saltSize = saltSize;
      this.ivSize = ivSize;
    }

    /**
     * Encrypts input. Output format is base64(salt + iv + encrypted).
     */
    @Override
    public String encrypt(String message) {
      try {
        byte[] salt = randomBytes(saltSize);
        byte[] iv = randomBytes(ivSize);
        PBEParameterSpec parameter = buildParameter(salt, iv, iterations);
        encryptor.init(Cipher.ENCRYPT_MODE, key, parameter);
        byte[] encrypted = encryptor.doFinal(message.getBytes(Charsets.UTF_8));
        byte[] prefix = concat(salt, iv);
        return new String(Base64.getEncoder().encode(concat(prefix, encrypted)));
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    @Override
    public String decrypt(String message) {
      try {
        byte[] encrypted = Base64.getDecoder().decode(message);
        int msgSize = encrypted.length - saltSize - ivSize;
        if (msgSize <= 0) {
          throw new IllegalArgumentException("message is too short");
        }

        byte[] salt = new byte[saltSize];
        byte[] iv = new byte[ivSize];
        byte[] msg = new byte[msgSize];
        System.arraycopy(encrypted, 0, salt, 0, saltSize);
        System.arraycopy(encrypted, saltSize, iv, 0, ivSize);
        System.arraycopy(encrypted, saltSize + ivSize, msg, 0, msgSize);
        PBEParameterSpec parameter = buildParameter(salt, iv, iterations);

        decryptor.init(Cipher.DECRYPT_MODE, key, parameter);
        byte[] decrypted = decryptor.doFinal(msg);
        return new String(decrypted, Charsets.UTF_8);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }

    private byte[] randomBytes(int len) {
      byte[] buf = new byte[len];
      random.nextBytes(buf);
      return buf;
    }

    private static byte[] concat(byte[] first, byte[] second) {
      byte[] result = new byte[first.length + second.length];
      System.arraycopy(first, 0, result, 0, first.length);
      System.arraycopy(second, 0, result, first.length, second.length);
      return result;
    }
  }

  /**
   * Initializes encryptor and decryptor with key and parameters.
   */
  private static void init(SecretKey key, PBEParameterSpec parameter, Cipher encryptor, Cipher decryptor)
      throws Exception {
    encryptor.init(Cipher.ENCRYPT_MODE, key, parameter);
    decryptor.init(Cipher.DECRYPT_MODE, key, parameter);
  }

  private static char[] normalize(String password) {
    return Normalizer.normalize(password, Normalizer.Form.NFC).toCharArray();
  }

  private static int getSaltSize(Cipher encryptor) {
    int blockSize = encryptor.getBlockSize();
    return blockSize > 0 ? blockSize : DEFAULT_SALT_SIZE;
  }

  private static int getIVSize(Cipher encryptor) {
    int blockSize = encryptor.getBlockSize();
    return blockSize > 0 ? blockSize : DEFAULT_IV_SIZE;
  }

  private static byte[] left(byte[] data, int len) {
    byte[] rs = new byte[len];
    System.arraycopy(data, 0, rs, 0, len);
    return rs;
  }

  /**
   * Same range as {@code scala.util.Random#nextPrintableChar()}.
   */
  private static char nextPrintableChar(Random rnd) {
    return (char) (rnd.nextInt(127 - 33) + 33);
  }
}
