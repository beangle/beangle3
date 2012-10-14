/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.util.inflector;

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
