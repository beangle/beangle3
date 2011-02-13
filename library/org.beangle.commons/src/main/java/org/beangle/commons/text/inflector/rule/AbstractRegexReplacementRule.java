/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.inflector.rule;

import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.beangle.commons.text.inflector.Rule;

public abstract class AbstractRegexReplacementRule implements Rule {

	private final Pattern pattern;

	/**
	 * <p>
	 * Construct a rule using the given regular expression.
	 * </p>
	 * 
	 * @param regex
	 *            the regular expression used to match words. Match information
	 *            is available to subclasses in the {@link #replace} method.
	 */
	public AbstractRegexReplacementRule(String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public boolean applies(String word) {
		return pattern.matcher(word).matches();
	}

	public String apply(String word) {
		Matcher matcher = pattern.matcher(word);
		if (!matcher.matches()) { throw new IllegalArgumentException("Word '" + word
				+ "' does not match regex: " + pattern.pattern()); }
		return replace(matcher);
	}

	/**
	 * <p>
	 * Use the state in the given {@link Matcher} to perform a replacement.
	 * </p>
	 * 
	 * @param matcher
	 *            the matcher used to match the word
	 * @return the transformed word
	 */
	public abstract String replace(Matcher matcher);

	/**
	 * <p>
	 * Form the disjunction of the given regular expression patterns. For example if patterns
	 * contains "a" and "b" then the disjunction is "(a|b)", that is, "a or b".
	 * </p>
	 * 
	 * @param patterns
	 *            an array of regular expression patterns
	 * @return a pattern that matches if any of the input patterns match
	 */
	public static String disjunction(String[] patterns) {
		String regex = "";
		for (int i = 0; i < patterns.length; i++) {
			regex += patterns[i];
			if (i < patterns.length - 1) {
				regex += "|";
			}
		}
		return "(?:" + regex + ")";
	}

	/**
	 * <p>
	 * Form the disjunction of the given regular expression patterns. For example if patterns
	 * contains "a" and "b" then the disjunction is "(a|b)", that is, "a or b".
	 * </p>
	 * 
	 * @param patterns
	 *            a set of regular expression patterns
	 * @return a pattern that matches if any of the input patterns match
	 */
	public static String disjunction(Set<String> patterns) {
		return disjunction(patterns.toArray(new String[0]));
	}

}
