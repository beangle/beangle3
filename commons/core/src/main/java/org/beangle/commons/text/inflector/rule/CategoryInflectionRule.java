/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.commons.text.inflector.rule;

import java.util.regex.Pattern;

/**
 * <p>
 * CategoryInflectionRule class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class CategoryInflectionRule extends SuffixInflectionRule {

  private final Pattern regex;

  /**
   * <p>
   * Construct a rule for <code>words</code> with suffix <code>singularSuffix</code> which becomes
   * <code>pluralSuffix</code> in the plural.
   * </p>
   * 
   * @param words the set of words that define this category
   * @param singularSuffix the singular suffix, starting with a "-" character
   * @param pluralSuffix the plural suffix, starting with a "-" character
   */
  public CategoryInflectionRule(String[] words, String singularSuffix, String pluralSuffix) {
    super(singularSuffix, pluralSuffix);
    this.regex = Pattern.compile("(?i)" + AbstractRegexReplacementRule.disjunction(words));
  }

  @Override
  public boolean applies(String word) {
    return regex.matcher(word).matches();
  }
}
