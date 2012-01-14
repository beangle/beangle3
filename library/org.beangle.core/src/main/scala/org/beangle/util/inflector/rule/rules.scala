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
package org.beangle.util.inflector.rule

import org.beangle.util.inflector.Rule
import java.util.regex._

object AbstractRegexReplacementRule {
  /** Form the disjunction of the given regular expression patterns.
   *  For example if patterns contains "a" and "b" then the disjunction is "(a|b)", that is, "a or b".
   */
  def disjunction(patterns: Array[String]): String = {
    val sb = new StringBuilder("")
    for (i <- 0 until patterns.length) {
      sb ++= patterns(i);
      if (i < patterns.length - 1)
        sb += '|'
    }
    return "(?:" + sb + ")";
   }

  def disjunction(patterns: Set[String]): String = disjunction(patterns.toArray)
}

abstract class AbstractRegexReplacementRule(regex: String) extends Rule {
  val pattern = Pattern.compile(regex)

  def applies(word: String): Boolean = pattern.matcher(word).matches()
  def apply(word: String): String = {
    val matcher = pattern.matcher(word);
    if (!matcher.matches()) {
      throw new IllegalArgumentException("Word '" + word
        + "' does not match regex: " + pattern.pattern());
    }
    replace(matcher)
  }
  def replace(matcher: Matcher): String;

}

/** Construct a rule for words with suffix <code>suffix</code>
 *  where <code>singularSuffix</code> becomes <code>pluralSuffix</code> in the plural.
 */
class SuffixInflectionRule(suffix: String, val singularSuffix: String, val pluralSuffix: String) extends Rule {
  // TODO: check suffix ends with singularSuffix?
  val regex = "(?i).*" + suffix.substring(1) + "$";

  def this(singularSuffix: String, pluralSuffix: String) = this(singularSuffix, singularSuffix, pluralSuffix)
  
  def applies(word: String): Boolean = word.matches(regex)

  def apply(word: String): String = {
    val i = word.lastIndexOf(singularSuffix.substring(1))
    // TODO: check i
    // TODO: make case insensitive
    word.substring(0, i) + pluralSuffix.substring(1)
  }

}

/**Construct a rule for <code>words</code> with suffix <code>singularSuffix</code> which becomes <code>pluralSuffix</code> in the plural.
 */
class CategoryInflectionRule(words:Array[String],singularSuffix:String,pluralSuffix:String) extends Rule{
  val regex:String="(?i)" + AbstractRegexReplacementRule.disjunction(words)

  def applies(word:String)= word.matches(regex)

  def apply(word: String): String = {
    val i = word.lastIndexOf(singularSuffix.substring(1))
    word.substring(0, i) + pluralSuffix.substring(1)
  }
}

/** Construct a rule using the given regular expression and replacement string*/

class RegexReplacementRule(regex:String,val replacement:String) extends AbstractRegexReplacementRule(regex){

  @Override
  def replace(matcher:Matcher):String= matcher.replaceFirst(replacement)
}

class IrregularMappingRule(val mappings:Map[String,String],regex:String) extends AbstractRegexReplacementRule(regex){
  @Override
  def replace(m:Matcher):String=mappings.get(m.group(0).toLowerCase).get
}

