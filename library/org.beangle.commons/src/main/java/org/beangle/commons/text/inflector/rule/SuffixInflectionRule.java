/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.inflector.rule;

import org.beangle.commons.text.inflector.Rule;

public class SuffixInflectionRule implements Rule {

	private final String regex;
	private final String singularSuffix;
	private final String pluralSuffix;

	/**
	 * <p>
	 * Construct a rule for words with suffix <code>singularSuffix</code> which becomes
	 * <code>pluralSuffix</code> in the plural.
	 * </p>
	 * 
	 * @param singularSuffix
	 *            the singular suffix, starting with a "-" character
	 * @param pluralSuffix
	 *            the plural suffix, starting with a "-" character
	 */
	public SuffixInflectionRule(String singularSuffix, String pluralSuffix) {
		this(singularSuffix, singularSuffix, pluralSuffix);
	}

	/**
	 * <p>
	 * Construct a rule for words with suffix <code>suffix</code>, where <code>singularSuffix</code>
	 * becomes <code>pluralSuffix</code> in the plural.
	 * 
	 * @param suffix
	 *            the suffix, starting with a "-" character, which the end of
	 *            the word must match. Note that regular expression patterns may
	 *            be used.
	 * @param singularSuffix
	 *            the singular suffix, starting with a "-" character. Note that
	 *            it must be true that <code>suffix</code> ends with <code>singularSuffix</code>.
	 * @param pluralSuffix
	 *            the plural suffix, starting with a "-" character
	 *            </p>
	 */
	public SuffixInflectionRule(String suffix, String singularSuffix, String pluralSuffix) {
		// TODO: check suffix ends with singularSuffix?
		this.regex = "(?i).*" + suffix.substring(1) + "$";
		this.singularSuffix = singularSuffix;
		this.pluralSuffix = pluralSuffix;
	}

	public boolean applies(String word) {
		return word.matches(regex);
	}

	public String apply(String word) {
		int i = word.lastIndexOf(singularSuffix.substring(1));
		// TODO: check i
		// TODO: make case insensitive
		return word.substring(0, i) + pluralSuffix.substring(1);
	}

}
