/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.sequence.impl;

import org.apache.commons.lang.StringUtils;
import org.beangle.db.sequence.SequenceNamePattern;

public class DefaultSequenceNamePattern implements SequenceNamePattern {

	String pattern = "SEQ_${table}";

	int begin = 0;
	String postfix = null;

	public DefaultSequenceNamePattern() {
		super();
		setPattern(pattern);
	}

	public String getTableName(String seqName) {
		int end = seqName.length();
		if (StringUtils.isNotEmpty(postfix)) {
			end = seqName.lastIndexOf(postfix);
		}
		return StringUtils.substring(seqName, begin, end);
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
		begin = pattern.indexOf("${table}");
		postfix = StringUtils.substringAfter(pattern, "${table}");
	}

}
