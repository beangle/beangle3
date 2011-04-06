/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text.inflector.rule;

public class CategoryInflectionRule extends SuffixInflectionRule {

	private final String regex;

	/**
	 * <p>
	 * Construct a rule for <code>words</code> with suffix <code>singularSuffix</code> which becomes
	 * <code>pluralSuffix</code> in the plural.
	 * </p>
	 * 
	 * @param words
	 *            the set of words that define this category
	 * @param singularSuffix
	 *            the singular suffix, starting with a "-" character
	 * @param pluralSuffix
	 *            the plural suffix, starting with a "-" character
	 */
	public CategoryInflectionRule(String[] words, String singularSuffix, String pluralSuffix) {
		super(singularSuffix, pluralSuffix);
		this.regex = "(?i)" + AbstractRegexReplacementRule.disjunction(words);
	}

	@Override
	public boolean applies(String word) {
		return word.matches(regex);
	}
}
