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
package org.beangle.security.codec;

import java.security.MessageDigest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 加密工具
 *
 * @author chaostone
 */
public class EncryptUtil {
  private final static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

  public static String encode(String password) {
    return encode(password, "MD5");
  }

  /**
   * Encode a string using algorithm specified in web.xml and return the
   * resulting encrypted password. If exception, the plain credentials string
   * is returned
   *
   * @param password passord string
   * @param algorithm Algorithm used to do the digest
   * @return encypted password based on the algorithm.
   */
  public static String encode(String password, String algorithm) {
    byte[] unencodedPassword = password.getBytes();
    MessageDigest md = null;
    try {
      // first create an instance, given the provider
      md = MessageDigest.getInstance(algorithm);
    } catch (Exception e) {
      logger.error("Exception:{}", e);
      return password;
    }

    md.reset();
    // call the update method one or more times
    // (useful when you don't know the size of your data, eg. stream)
    md.update(unencodedPassword);
    // now calculate the hash
    byte[] encodedPassword = md.digest();
    StringBuilder buf = new StringBuilder();
    for (int i = 0; i < encodedPassword.length; i++) {
      if ((encodedPassword[i] & 0xff) < 0x10) buf.append("0");
      buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
    }
    return buf.toString();
  }
}
