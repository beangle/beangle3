/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.lang;

import static java.lang.Character.isLowerCase;
import static java.lang.Character.isUpperCase;
import static java.lang.Character.toLowerCase;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;

/**
 * @author chaostone 2005-11-15
 */
public final class StrUtils {

	private StrUtils() {
	}

	/**
	 * count char in host string
	 * 
	 * @param host
	 * @param charactor
	 * @return
	 */
	public static int countChar(final String host, final char charactor) {
		int count = 0;
		for (int i = 0; i < host.length(); i++) {
			if (host.charAt(i) == charactor) {
				count++;
			}
		}
		return count;
	}

	/**
	 * count inner string in host string
	 * 
	 * @param host
	 * @param searchStr
	 * @return
	 */
	public static int countStr(final String host, final String searchStr) {
		int count = 0;
		for (int startIndex = 0; startIndex < host.length(); startIndex++) {
			int findLoc = host.indexOf(searchStr, startIndex);
			if (findLoc == -1) {
				break;
			} else {
				count++;
				startIndex = findLoc + searchStr.length() - 1;
			}
		}
		return count;
	}

	public static String insert(final String str, final String c, final int pos) {
		if (str.length() < pos) { return str; }
		return str.substring(0, pos - 1) + c + str.substring(pos);
	}

	/**
	 * replace [bigen,end] [1...end] with givenStr
	 * 可以使用StringBuilder的replace方法替换该方法
	 * 
	 * @param str
	 * @param c
	 * @param begin
	 * @param end
	 * @return
	 */
	public static String insert(final String str, final String given, final int begin, final int end) {
		if (begin < 1 || end > str.length() || end < begin) { return str; }
		return str.substring(0, begin - 1) + given + str.substring(end);
	}

	public static String unCamel(String str, char seperator) {
		return unCamel(str, seperator, true);
	}

	/**
	 * 将驼峰表示法转换为下划线小写表示
	 * 
	 * @param str
	 * @return
	 */
	public static String unCamel(String str, char seperator, boolean lowercase) {
		char[] ca = str.toCharArray();
		if (3 > ca.length) { return lowercase ? str.toLowerCase() : str; }
		// about five seperator
		StringBuilder build = new StringBuilder(ca.length + 5);
		build.append(lowercase ? toLowerCase(ca[0]) : ca[0]);

		boolean lower1 = isLowerCase(ca[0]);
		int i = 1;
		while (i < ca.length - 1) {
			char cur = ca[i];
			char next = ca[i + 1];
			boolean upper2 = isUpperCase(cur);
			boolean lower3 = isLowerCase(next);
			if (lower1 && upper2 && lower3) {
				build.append(seperator);
				build.append(toLowerCase(cur));
				build.append(next);
				i += 2;
			} else {
				if (lowercase && upper2) {
					build.append(toLowerCase(cur));
				} else {
					build.append(cur);
				}
				lower1 = !upper2;
				i++;
			}
		}
		if (i == ca.length - 1) {
			build.append(lowercase ? toLowerCase(ca[i]) : ca[i]);
		}
		return build.toString();
	}

	public static String unCamel(String str) {
		return unCamel(str, '-', true);
	}
	
	public static String[] split(String target){
		return split(target,new char[]{',',';','\r','\n',' '});
	}
	
	public static String[] split(String target,char[] separatorChars){
		if(null==target){
			return new String[0];
		}
		char[] sb=target.toCharArray();
		for(char separator:separatorChars){
			if(separator!=',')
			for(int i=0;i<sb.length;i++){
				if(sb[i]==separator){
					sb[i]= ',';
				}
			}
		}
		String [] targets=StringUtils.split(new String(sb), ',');
		List<String> list=CollectUtils.newArrayList();
		for(String one:targets){
			if(StringUtils.isNotBlank(one)){
				list.add(one.trim());
			}
		}
		String [] rs=new String[list.size()];
		list.toArray(rs);
		return rs;
	}
}
