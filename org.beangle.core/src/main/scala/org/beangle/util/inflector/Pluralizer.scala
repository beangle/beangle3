/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
 
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.util.inflector

/**<code>Pluralizer</code> converts singular word forms to their plural forms.*/
trait Pluralizer {

  /** Converts a noun or pronoun to its plural form.
   *  <p>
   *  This method is equivalent to calling <code>pluralize(word, 2)</code>.
   *  </p>
   *  <p>
   *  The return value is not defined if this method is passed a plural form.
   *  </p>
   *
   *  @param word  a singular noun
   *  @return the plural form of the noun
   */
  def pluralize(word: String): String;
  /** Converts a noun or pronoun to its plural form for the given number of instances. If
   *  <code>number</code> is 1, <code>word</code> is returned unchanged.
   *
   *  <p>
   *  The return value is not defined if this method is passed a plural form.
   *  </p>
   *
   *  @param word a singular noun
   *  @param number the number of objects being referred to in the plural
   *  @return the plural form of the noun
   */
  def pluralize(word: String, number: Int): String;
}

trait Rule {
  /**Tests to see if this rule applies for the given word.*/
  def applies(word: String): Boolean;
  /**Applies this rule to the word, and transforming it into a new form.*/
  def apply(word: String): String;
}

class IdentityPluralizer extends Pluralizer {
  def pluralize(word: String): String = word
  def pluralize(word: String, number: Int): String = word
}

import java.util.regex.{ Pattern, Matcher }
import java.util.Locale
/** Constructs a pluralizer that uses a list of rules then an identity {@link Pluralizer} if none
 *  of the rules match. This is useful to build your own {@link Pluralizer} from scratch.
 */
class RuleBasedPluralizer(var rules: List[Rule], var locale: Locale, fallbackPluralizer: Pluralizer) extends Pluralizer {

  def this() = this(List(), Locale.getDefault(), new IdentityPluralizer())

  /** @param rules  the rules to apply in order
   *  @param locale the locale specifying the language of the pluralizer
   */
  def this(rules: List[Rule], locale: Locale) = this(rules, locale, new IdentityPluralizer())

  def pluralize(word: String): String = pluralize(word, 2)

  def pluralize(word: String, number: Int): String = {
    if (number == 1) { return word; }

    val pattern = Pattern.compile("\\A(\\s*)(.+?)(\\s*)\\Z")
    val matcher = pattern.matcher(word)
    if (matcher.matches()) {
      val pre = matcher.group(1);
      val trimmedWord = matcher.group(2);
      val post = matcher.group(3);
      val plural = pluralizeInternal(trimmedWord);
      if (plural == null) fallbackPluralizer.pluralize(word, number)
      else
        pre + postProcess(trimmedWord, plural) + post
    } else word
  }

  /** <p>
   *  Goes through the rules in turn until a match is found at which point the rule is applied and
   *  the result returned. If no rule matches, returns <code>null</code>.
   *  </p>
   *
   *  @param word  a singular noun
   *  @return the plural form of the noun, or <code>null</code> if no rule  matches
   */
  private def pluralizeInternal(word: String): String = {
    var result: String = null;
    var ruleIt = rules.iterator
    while (ruleIt.hasNext && null == result) {
      val rule = ruleIt.next();
      if (rule.applies(word))
        result = rule.apply(word)
    }
    result
  }

  /** Apply processing to <code>pluralizedWord</code>. This implementation ensures the case of the
   *  plural is consistent with the case of the input word.
   *  <p>
   *  If <code>trimmedWord</code> is all uppercase, then <code>pluralizedWord</code> is uppercased.
   *  If <code>trimmedWord</code> is titlecase, then <code>pluralizedWord</code> is titlecased.
   *  </p>
   *
   *  @param trimmedWord the input word, with leading and trailing whitespace removed
   *  @param pluralizedWord the pluralized word
   *  @return the <code>pluralizedWord</code> after processing
   */
  protected def postProcess(trimmedWord: String, pluralizedWord: String): String = {
    if (trimmedWord.matches("^\\p{Lu}+$")) {
      return pluralizedWord.toUpperCase(locale);
    } else if (trimmedWord.matches("^\\p{Lu}.*")) {
      return pluralizedWord.substring(0, 1).toUpperCase(
        locale) + pluralizedWord.substring(1);
    }
    return pluralizedWord;
  }
}