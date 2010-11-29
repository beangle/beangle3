/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import org.apache.commons.lang.StringUtils;

public final class BitStrUtils {

	private BitStrUtils() {
	}

	/**
	 * 比较两个等长字符串的每一位，若都大于0，则返回结果的相应位为1，否则为0;
	 * 
	 * @param first
	 * @param second
	 * @return
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
	 * @param first
	 * @param second
	 * @return
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
	 * @param str
	 * @param boolString
	 * @return
	 */
	public static String andWith(final String str, final String boolString) {
		if (StringUtils.isEmpty(str)) { return null; }
		if (StringUtils.isEmpty(boolString)) { return str; }
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
	 * @param first
	 * @return
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
	 * @param binaryStr
	 * @return
	 */
	public static long binValueOf(final String binaryStr) {
		if (StringUtils.isEmpty(binaryStr)) { return 0; }
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
