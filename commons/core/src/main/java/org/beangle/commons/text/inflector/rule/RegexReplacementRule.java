/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.text.inflector.rule;

import java.util.regex.Matcher;

/**
 * <p>
 * RegexReplacementRule class.
 * </p>
 *
 * @author chaostone
 * @version $Id: $
 */
public class RegexReplacementRule extends AbstractRegexReplacementRule {

  private final String replacement;

  /**
   * Construct a rule using the given regular expression and replacement
   * string.
   *
   * @param regex
   *          the regular expression used to match words
   * @param replacement
   *          the string to use during replacement. The replacement string
   *          may contain references to subsequences captured matching. See
   *          {@link Matcher#appendReplacement}.
   */
  public RegexReplacementRule(String regex, String replacement) {
    super(regex);
    this.replacement = replacement;
  }

  /** {@inheritDoc} */
  @Override
  public String replace(Matcher matcher) {
    return matcher.replaceFirst(replacement);
  }

}
