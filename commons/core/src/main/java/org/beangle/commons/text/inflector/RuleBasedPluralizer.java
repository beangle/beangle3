/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.text.inflector;

import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * RuleBasedPluralizer class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class RuleBasedPluralizer implements Pluralizer {

  static class IdentityPluralizer implements Pluralizer {
    public String pluralize(String word) {
      return word;
    }

    public String pluralize(String word, int number) {
      return word;
    }
  }

  private static final Pluralizer IDENTITY_PLURALIZER = new IdentityPluralizer();

  private List<Rule> rules;
  private Locale locale;
  private Pluralizer fallbackPluralizer;

  private static Pattern pattern = Pattern.compile("\\A(\\s*)(.+?)(\\s*)\\Z");
  private static Pattern pluPattern1 = Pattern.compile("^\\p{Lu}+$");
  private static Pattern pluPattern2 = Pattern.compile("^\\p{Lu}.*");

  /**
   * <p>
   * Constructs a pluralizer with an empty list of rules. Use the setters to configure.
   * </p>
   */
  @SuppressWarnings("unchecked")
  public RuleBasedPluralizer() {
    this(Collections.EMPTY_LIST, Locale.getDefault());
  }

  /**
   * <p>
   * Constructs a pluralizer that uses a list of rules then an identity {@link Pluralizer} if none
   * of the rules match. This is useful to build your own {@link Pluralizer} from scratch.
   * </p>
   * 
   * @param rules
   *          the rules to apply in order
   * @param locale
   *          the locale specifying the language of the pluralizer
   */
  public RuleBasedPluralizer(List<Rule> rules, Locale locale) {
    this(rules, locale, IDENTITY_PLURALIZER);
  }

  /**
   * <p>
   * Constructs a pluralizer that uses first a list of rules then a fallback {@link Pluralizer}.
   * This is useful to override the behaviour of an existing {@link Pluralizer}.
   * </p>
   * 
   * @param rules
   *          the rules to apply in order
   * @param locale
   *          the locale specifying the language of the pluralizer
   * @param fallbackPluralizer
   *          the pluralizer to use if no rules match
   */
  public RuleBasedPluralizer(List<Rule> rules, Locale locale, Pluralizer fallbackPluralizer) {
    this.rules = rules;
    this.locale = locale;
    this.fallbackPluralizer = fallbackPluralizer;
  }

  /**
   * <p>
   * Getter for the field <code>fallbackPluralizer</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.text.inflector.Pluralizer} object.
   */
  public Pluralizer getFallbackPluralizer() {
    return fallbackPluralizer;
  }

  /**
   * <p>
   * Setter for the field <code>fallbackPluralizer</code>.
   * </p>
   * 
   * @param fallbackPluralizer a {@link org.beangle.commons.text.inflector.Pluralizer} object.
   */
  public void setFallbackPluralizer(Pluralizer fallbackPluralizer) {
    this.fallbackPluralizer = fallbackPluralizer;
  }

  /**
   * <p>
   * Getter for the field <code>locale</code>.
   * </p>
   * 
   * @return a {@link java.util.Locale} object.
   */
  public Locale getLocale() {
    return locale;
  }

  /**
   * <p>
   * Setter for the field <code>locale</code>.
   * </p>
   * 
   * @param locale a {@link java.util.Locale} object.
   */
  public void setLocale(Locale locale) {
    this.locale = locale;
  }

  /**
   * <p>
   * Getter for the field <code>rules</code>.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<Rule> getRules() {
    return rules;
  }

  /**
   * <p>
   * Setter for the field <code>rules</code>.
   * </p>
   * 
   * @param rules a {@link java.util.List} object.
   */
  public void setRules(List<Rule> rules) {
    this.rules = rules;
  }

  /**
   * {@inheritDoc}
   * <p>
   * Converts a noun or pronoun to its plural form.
   * </p>
   * <p>
   * This method is equivalent to calling <code>pluralize(word, 2)</code>.
   * </p>
   * <p>
   * The return value is not defined if this method is passed a plural form.
   * </p>
   */
  public String pluralize(String word) {
    return pluralize(word, 2);
  }

  /**
   * {@inheritDoc}
   * <p>
   * Converts a noun or pronoun to its plural form for the given number of instances. If
   * <code>number</code> is 1, <code>word</code> is returned unchanged.
   * </p>
   * <p>
   * The return value is not defined if this method is passed a plural form.
   * </p>
   */
  public String pluralize(String word, int number) {
    if (number == 1) { return word; }
    Matcher matcher = pattern.matcher(word);
    if (matcher.matches()) {
      String pre = matcher.group(1);
      String trimmedWord = matcher.group(2);
      String post = matcher.group(3);
      String plural = pluralizeInternal(trimmedWord);
      if (plural == null) { return fallbackPluralizer.pluralize(word, number); }
      return pre + postProcess(trimmedWord, plural) + post;
    }
    return word;
  }

  /**
   * <p>
   * Goes through the rules in turn until a match is found at which point the rule is applied and
   * the result returned. If no rule matches, returns <code>null</code>.
   * </p>
   * 
   * @param word
   *          a singular noun
   * @return the plural form of the noun, or <code>null</code> if no rule
   *         matches
   */
  protected String pluralizeInternal(String word) {
    for (Rule rule : rules) {
      if (rule.applies(word)) { return rule.apply(word); }
    }
    return null;
  }

  /**
   * <p>
   * Apply processing to <code>pluralizedWord</code>. This implementation ensures the case of the
   * plural is consistent with the case of the input word.
   * </p>
   * <p>
   * If <code>trimmedWord</code> is all uppercase, then <code>pluralizedWord</code> is uppercased.
   * If <code>trimmedWord</code> is titlecase, then <code>pluralizedWord</code> is titlecased.
   * </p>
   * 
   * @param trimmedWord
   *          the input word, with leading and trailing whitespace removed
   * @param pluralizedWord
   *          the pluralized word
   * @return the <code>pluralizedWord</code> after processing
   */
  protected String postProcess(String trimmedWord, String pluralizedWord) {
    if (pluPattern1.matcher(trimmedWord).matches()) {
      return pluralizedWord.toUpperCase(locale);
    } else if (pluPattern2.matcher(trimmedWord).matches()) { return pluralizedWord.substring(0, 1)
        .toUpperCase(locale) + pluralizedWord.substring(1); }
    return pluralizedWord;
  }

}
