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

/**
 * <p>
 * Rule interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Rule {
  /**
   * <p>
   * Tests to see if this rule applies for the given word.
   * </p>
   * 
   * @param word
   *          the word that is being tested
   * @return <code>true</code> if this rule should be applied, <code>false</code> otherwise
   */
  boolean applies(String word);

  /**
   * <p>
   * Applies this rule to the word, and transforming it into a new form.
   * </p>
   * 
   * @param word
   *          the word to apply this rule to
   * @return the transformed word
   */
  String apply(String word);
}
