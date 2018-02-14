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

import java.util.Map;
import java.util.regex.Matcher;

/**
 * <p>
 * IrregularMappingRule class.
 * </p>
 *
 * @author chaostone
 * @version $Id: $
 */
public class IrregularMappingRule extends AbstractRegexReplacementRule {

  protected final Map<String, String> mappings;

  /**
   * <p>
   * Construct a rule using the given regular expression and irregular forms map.
   * </p>
   *
   * @param wordMappings
   *          the map of singular to plural forms
   * @param regex
   *          the regular expression used to match words. Match information
   *          is available to subclasses in the {@link #replace} method.
   */
  public IrregularMappingRule(Map<String, String> wordMappings, String regex) {
    super(regex);
    this.mappings = wordMappings;
  }

  /** {@inheritDoc} */
  @Override
  public String replace(Matcher m) {
    return mappings.get(m.group(0).toLowerCase());
  }
}
