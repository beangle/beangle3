/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.replacer;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Replace target with value on any input.
 * 
 * @author chaostone
 */
public class Replacer {

	Pattern pattern;

	String target;

	String value;

	public Replacer(String key, String value) {
		super();
		this.target = key;
		pattern = Pattern.compile(key);
		this.value = value;
	}

	public String process(String input) {
		return pattern.matcher(input).replaceAll(value);
	}

	public static String process(String input, List<Replacer> replacers) {
		if (null == input) { return null; }
		for (Replacer replacer : replacers) {
			input = replacer.process(input);
		}
		return input;
	}

	public String toString() {
		return target + "=" + value;
	}

}
