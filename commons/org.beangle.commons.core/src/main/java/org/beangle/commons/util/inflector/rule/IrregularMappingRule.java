/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.util.inflector.rule;

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
