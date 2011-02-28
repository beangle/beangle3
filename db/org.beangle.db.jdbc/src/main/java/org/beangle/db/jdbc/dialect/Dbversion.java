/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.dialect;

import org.apache.commons.lang.StringUtils;

/**
 * User [a,b] or (a,b) or a,b to discribe db version range.
 * a,b should not all empty.
 * 
 * @author chaostone
 */
class Dbversion {

	private final boolean containStart;
	private final boolean containEnd;
	private final String start;
	private final String end;

	public Dbversion(String version) {
		super();
		containStart = !version.startsWith("(");
		containEnd = !version.endsWith(")");
		int commaIndex = version.indexOf(',');
		if (-1 == commaIndex) {
			start = version;
			end = version;
		} else {
			if ('[' == version.charAt(0) || '(' == version.charAt(0)) {
				start = StringUtils.substring(version, 1, commaIndex);
			} else {
				start = version.substring(0, commaIndex);
			}
			if (']' == version.charAt(version.length() - 1) || ')' == version.charAt(version.length() - 1)) {
				end = StringUtils.substring(version, commaIndex + 1, version.length() - 1);
			} else {
				end = version.substring(commaIndex + 1);
			}

		}
	}

	public boolean contains(String given) {
		if (StringUtils.isNotEmpty(start)) {
			int rs = start.compareTo(given);
			if ((!containStart && 0 == rs) || rs > 0) return false;
		}
		if (StringUtils.isNotEmpty(end)) {
			int rs = end.compareTo(given);
			if ((!containEnd && 0 == rs) || rs < 0) return false;
		}
		return true;
	}
}
