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
  def pluralize(word: String): String;
  def pluralize(word: String, number: Int): String;
}

trait Rule {
  /**Tests to see if this rule applies for the given word.*/
  def applies(word: String): Boolean;
  /**Applies this rule to the word, and transforming it into a new form.*/
  def apply(word: String): String;
}