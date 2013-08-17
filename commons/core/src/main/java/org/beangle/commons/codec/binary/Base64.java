/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.commons.codec.binary;

import java.io.UnsupportedEncodingException;

import org.beangle.commons.codec.net.BCoder;

/**
 * Base64 algorithm
 * @author chaostone
 * @since 3.2.0
 */
public final class Base64 {

  public static char[] encode(byte data[]) {
    char out[] = new char[((data.length + 2) / 3) * 4];
    int i = 0;
    for (int index = 0; i < data.length; index += 4) {
      boolean quad = false;
      boolean trip = false;
      int val = 0xff & data[i];
      val <<= 8;
      if (i + 1 < data.length) {
        val |= 0xff & data[i + 1];
        trip = true;
      }
      val <<= 8;
      if (i + 2 < data.length) {
        val |= 0xff & data[i + 2];
        quad = true;
      }
      out[index + 3] = alphabet[quad ? val & 0x3f : 64];
      val >>= 6;
      out[index + 2] = alphabet[trip ? val & 0x3f : 64];
      val >>= 6;
      out[index + 1] = alphabet[val & 0x3f];
      val >>= 6;
      out[index + 0] = alphabet[val & 0x3f];
      i += 3;
    }
    return out;
  }

  public static byte[] decode(String pArray) {
    return decode(pArray.toCharArray());
  }

  public static byte[] decode(char data[]) {
    int tempLen = data.length;
    for (int ix = 0; ix < data.length; ix++)
      if (data[ix] > '\377' || codes[data[ix]] < 0) tempLen--;

    int len = (tempLen / 4) * 3;
    if (tempLen % 4 == 3) len += 2;
    if (tempLen % 4 == 2) len++;
    byte out[] = new byte[len];
    int shift = 0;
    int accum = 0;
    int index = 0;
    for (int ix = 0; ix < data.length; ix++) {
      int value = data[ix] <= '\377' ? ((int) (codes[data[ix]])) : -1;
      if (value >= 0) {
        accum <<= 6;
        shift += 6;
        accum |= value;
        if (shift >= 8) {
          shift -= 8;
          out[index++] = (byte) (accum >> shift & 0xff);
        }
      }
    }

    if (index != out.length) throw new Error("Miscalculated data length (wrote " + index + " instead of "
        + out.length + ")");
    else return out;
  }

  private static char alphabet[] = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
      .toCharArray();
  private static byte codes[];

  static {
    codes = new byte[256];
    for (int i = 0; i < 256; i++)
      codes[i] = -1;

    for (int i = 65; i <= 90; i++)
      codes[i] = (byte) (i - 65);

    for (int i = 97; i <= 122; i++)
      codes[i] = (byte) ((26 + i) - 97);

    for (int i = 48; i <= 57; i++)
      codes[i] = (byte) ((52 + i) - 48);

    codes[43] = 62;
    codes[47] = 63;
  }
  
  public static void main(String[] args) throws UnsupportedEncodingException{
    System.out.println(new BCoder().encode("汉字123"));
    System.out.println(Base64.encode("汉字123".getBytes("UTF-8")));
  }
}
