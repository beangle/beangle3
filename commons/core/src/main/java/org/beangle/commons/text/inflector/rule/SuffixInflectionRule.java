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
package org.beangle.commons.text.inflector.rule;

import java.util.regex.Pattern;

import org.beangle.commons.text.inflector.Rule;

/**
 * <p>
 * SuffixInflectionRule class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SuffixInflectionRule implements Rule {

  private final Pattern regex;
  private final String singularSuffix;
  private final String pluralSuffix;

  /**
   * <p>
   * Construct a rule for words with suffix <code>singularSuffix</code> which becomes
   * <code>pluralSuffix</code> in the plural.
   * </p>
   * 
   * @param singularSuffix
   *          the singular suffix, starting with a "-" character
   * @param pluralSuffix
   *          the plural suffix, starting with a "-" character
   */
  public SuffixInflectionRule(String singularSuffix, String pluralSuffix) {
    this(singularSuffix, singularSuffix, pluralSuffix);
  }

  /**
   * <p>
   * Construct a rule for words with suffix <code>suffix</code>, where <code>singularSuffix</code>
   * becomes <code>pluralSuffix</code> in the plural.
   * 
   * @param suffix the suffix, starting with a "-" character, which the end of
   *          the word must match. Note that regular expression patterns may
   *          be used.
   * @param singularSuffix the singular suffix, starting with a "-" character. Note that
   *          it must be true that <code>suffix</code> ends with <code>singularSuffix</code>.
   * @param pluralSuffix the plural suffix, starting with a "-" character
   */
  public SuffixInflectionRule(String suffix, String singularSuffix, String pluralSuffix) {
    // TODO: check suffix ends with singularSuffix?
    this.regex = Pattern.compile("(?i).*" + suffix.substring(1) + "$");
    this.singularSuffix = singularSuffix;
    this.pluralSuffix = pluralSuffix;
  }

  public boolean applies(String word) {
    return regex.matcher(word).matches();
  }

  public String apply(String word) {
    int i = word.lastIndexOf(singularSuffix.substring(1));
    // TODO: check i
    // TODO: make case insensitive
    return word.substring(0, i) + pluralSuffix.substring(1);
  }

}
