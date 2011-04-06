/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.seq;

/**
 * 汉字序列产生器
 * 
 * @author chaostone,zhufengbin
 */
public class HanZiSeqStyle implements SeqNumStyle {

	// 支持的最大数字
	public static final int MAX = 99999;

	public static final String[] CHINESE_NAMES = { "零", "一", "二", "三", "四", "五", "六", "七", "八", "九", "十" };

	public static final String[] PRIORITIES = { "十", "百", "千", "万" };

	public String build(int seq) {
		if (seq > MAX) { throw new RuntimeException("seq greate than " + MAX); }
		return buildText(String.valueOf(seq));
	}

	public String basicOf(int num) {
		return CHINESE_NAMES[num];
	}

	public String priorityOf(int index) {
		if (index < 2) {
			return "";
		} else {
			return PRIORITIES[index - 2];
		}
	}

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
