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
package org.beangle.commons.lang;

/**
 * <p>
 * BitStrings class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class BitStrings {

  BitStrings() {
  }

  /**
   * 比较两个等长字符串的每一位，若都大于0，则返回结果的相应位为1，否则为0;
   * 
   * @param first a {@link java.lang.String} object.
   * @param second a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String and(final String first, final String second) {
    final StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < first.length(); i++) {
      if ('0' == first.charAt(i) || '0' == second.charAt(i)) {
        buffer.append('0');
      } else {
        buffer.append('1');
      }
    }
    return buffer.toString();
  }

  /**
   * 比较两个等长字符串的每一位，相或<br>
   * 适用于仅含有1和0的字符串.
   * 
   * @param first a {@link java.lang.String} object.
   * @param second a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String or(final String first, final String second) {
    final StringBuilder buffer = new StringBuilder();
    for (int i = 0; i < first.length(); i++) {
      if ('0' == first.charAt(i) && '0' == second.charAt(i)) {
        buffer.append('0');
      } else {
        buffer.append('1');
      }
    }
    return buffer.toString();
  }

  /**
   * 将一个字符串，按照boolString的形式进行变化. 如果boolString[i]!=0则保留str[i],否则置0
   * 
   * @param str a {@link java.lang.String} object.
   * @param boolString a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String andWith(final String str, final String boolString) {
    if (Strings.isEmpty(str)) { return null; }
    if (Strings.isEmpty(boolString)) { return str; }
    if (str.length() < boolString.length()) { return str; }
    final StringBuilder buffer = new StringBuilder(str);
    for (int i = 0; i < buffer.length(); i++) {
      if (boolString.charAt(i) == '0') {
        buffer.setCharAt(i, '0');
      }
    }
    return buffer.toString();
  }

  /**
   * 将"314213421340asdf"转换成"1111111111101111"
   * 
   * @param first a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String convertToBoolStr(final String first) {
    final StringBuilder occupyBuffer = new StringBuilder(first.length());
    for (int i = 0; i < first.length(); i++) {
      if ('0' == first.charAt(i)) {
        occupyBuffer.append('0');
      } else {
        occupyBuffer.append('1');
      }

    }
    return occupyBuffer.toString();
  }

  /**
   * 返回零一串的整型值
   * 
   * @param binaryStr a {@link java.lang.String} object.
   * @return a long.
   */
  public static long binValueOf(final String binaryStr) {
    if (Strings.isEmpty(binaryStr)) { return 0; }
    long value = 0;
    long height = 1;

    for (int i = binaryStr.length() - 1; i >= 0; i--) {
      if ('1' == binaryStr.charAt(i)) {
        value += height;
      }
      height *= 2;
    }
    return value;
  }

  /**
   * <p>
   * reverse.
   * </p>
   * 
   * @param binaryStr a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String reverse(final String binaryStr) {
    final StringBuilder occupyBuffer = new StringBuilder(binaryStr.length());
    for (int i = 0; i < binaryStr.length(); i++) {
      if ('0' == binaryStr.charAt(i)) {
        occupyBuffer.append('1');
      } else {
        occupyBuffer.append('0');
      }
    }
    return occupyBuffer.toString();
  }

}
