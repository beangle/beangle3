/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.util.inflector;

/**
 * <code>Pluralizer</code> converts singular word forms to their plural forms.
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Pluralizer {

  /**
   * <p>
   * pluralize.
   * </p>
   * 
   * @param word a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public String pluralize(String word);

  /**
   * <p>
   * pluralize.
   * </p>
   * 
   * @param word a {@link java.lang.String} object.
   * @param number a int.
   * @return a {@link java.lang.String} object.
   */
  public String pluralize(String word, int number);
}
