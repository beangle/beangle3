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
package org.beangle.commons.text.seq;

/**
 * 汉字序列产生器
 * 
 * @author chaostone,zhufengbin
 * @version $Id: $
 */
public class HanZiSeqStyle implements SeqNumStyle {

  // 支持的最大数字
  /** Constant <code>MAX=99999</code> */
  public static final int MAX = 99999;

  /** Constant <code>CHINESE_NAMES="{ 零, 一, 二, 三, 四, 五, 六, 七, 八, 九, 十 }"</code> */
  public static final String[] CHINESE_NAMES = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };

  /** Constant <code>PRIORITIES="{ 十, 百, 千, 万 }"</code> */
  public static final String[] PRIORITIES = { "十", "百", "千", "万" };

  public String build(int seq) {
    if (seq > MAX) { throw new RuntimeException("seq greate than " + MAX); }
    return buildText(String.valueOf(seq));
  }

  /**
   * <p>
   * basicOf.
   * </p>
   * 
   * @param num a int.
   * @return a {@link java.lang.String} object.
   */
  public String basicOf(int num) {
    return CHINESE_NAMES[num];
  }

  /**
   * <p>
   * priorityOf.
   * </p>
   * 
   * @param index a int.
   * @return a {@link java.lang.String} object.
   */
  public String priorityOf(int index) {
    if (index < 2) {
      return "";
    } else {
      return PRIORITIES[index - 2];
    }
  }

  /**
   * <p>
   * buildText.
   * </p>
   * 
   * @param str1 a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public String buildText(String str1) {
    StringBuilder sb = new StringBuilder();
    int prev = 0;
    for (int i = 0; i < str1.length(); i++) {
      char numChar = str1.charAt(i);
      String temp = basicOf(numChar - '0');
      if (numChar - '0' > 0) {
        if (i - prev > 1) temp = CHINESE_NAMES[0] + temp;
        prev = i;
        temp = temp + priorityOf(str1.length() - i);
        sb.append(temp);
      }
    }
    String result = sb.toString();
    if (result.startsWith("一十")) result = result.substring(1);
    return result;
  }

  public static void main(String[] args) {
    HanZiSeqStyle s = new HanZiSeqStyle();
    for (int i = 1; i < 1101; i++) {
      System.out.println(s.buildText(String.valueOf(i)));
    }
  }
}
