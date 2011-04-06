/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.inflector.rule;

import java.util.regex.Matcher;

public class RegexReplacementRule extends AbstractRegexReplacementRule {

	private final String replacement;

	/**
	 * Construct a rule using the given regular expression and replacement
	 * string.
	 * 
	 * @param regex
	 *            the regular expression used to match words
	 * @param replacement
	 *            the string to use during replacement. The replacement string
	 *            may contain references to subsequences captured matching. See
	 *            {@link Matcher#appendReplacement}.
	 */
	public RegexReplacementRule(String regex, String replacement) {
		super(regex);
		this.replacement = replacement;
	}

	@Override
	public String replace(Matcher matcher) {
		return matcher.replaceFirst(replacement);
	}

}
