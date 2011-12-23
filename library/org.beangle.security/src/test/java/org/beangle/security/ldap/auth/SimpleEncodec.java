// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   jsende.java

package org.beangle.security.ldap.auth;

import org.beangle.lang.StrUtils;
import org.beangle.security.codec.Base64;

public class SimpleEncodec {
	public static String decode(String strCode) {
		String strDecodeMe = "";
		if (strCode.equals("")) {
			return strDecodeMe;
		} else {
			int intStrLen = strCode.length() - 1;
			String strRnd = strCode.substring(intStrLen / 2, intStrLen / 2 + 1);
			int intRnd = strRnd.hashCode() - startChar();
			strCode = strCode.substring(0, intStrLen / 2)
					+ strCode.substring(intStrLen / 2 + 1, intStrLen + 1);
			String strOriginal = loopCode(strCode, 94 - intRnd);
			strDecodeMe = strOriginal;
			return new String(Base64.decode(strDecodeMe));
		}
	}

	public static String encode(String strOriginal) {
		String strCodeMe = "";
		if (strOriginal.equals("")) return strCodeMe;
		int intRnd = (int) (Math.random() * 92D + 2D);
		String strCode = loopCode(new String(Base64.encode(strOriginal.getBytes())), intRnd);
		String rnd = String.valueOf((char) (intRnd + startChar()));
		int intStrLen = strCode.length();
		strCodeMe = StrUtils.concat(strCode.substring(0, intStrLen / 2), rnd,
				strCode.substring(intStrLen / 2, intStrLen));
		if (strCodeMe.indexOf("'") >= 0) return encode(strOriginal);
		else return strCodeMe;
	}

	private static String kaiserCode(String strOriginal) {
		int intStrLen = strOriginal.length();
		StringBuilder strCode = new StringBuilder();
		for (int i = 0; i < intStrLen; i++) {
			int intChar = strOriginal.substring(i, i + 1).hashCode();
			int intTmp = intChar - startChar();
			intTmp = (intTmp * 95 + i + 1) % maxChar() + startChar();
			strCode.append((char) intTmp);
		}
		return strCode.toString();
	}

	private static String loopCode(String strOriginal, int intLoopCount) {
		String strCode = strOriginal;
		for (int i = 0; i < intLoopCount; i++)
			strCode = kaiserCode(strCode);
		return strCode;
	}

	private static int maxChar() {
		return ("~".hashCode() - "!".hashCode()) + 1;
	}

	private static int startChar() {
		return "!".hashCode();
	}
}
