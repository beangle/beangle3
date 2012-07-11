/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.util.seq;

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

  /** {@inheritDoc} */
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
    for (int i = 0; i < str1.length(); i++) {
      char numChar = str1.charAt(i);
      String temp = basicOf(numChar - '0');
      if (numChar - '0' > 0) {
        temp = temp + priorityOf(str1.length() - i);
      }
      sb.append(temp);
    }
    String result = sb.toString();
    result = result.replaceAll("零一十", "零十");
    result = result.replaceAll("零零", "零");
    return result;
  }
}
