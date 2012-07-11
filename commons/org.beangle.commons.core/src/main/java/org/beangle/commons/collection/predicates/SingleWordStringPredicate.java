/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.predicates;

import org.apache.commons.collections.Predicate;
import org.beangle.commons.lang.Strings;

/**
 * <p>
 * SingleWordStringPredicate class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SingleWordStringPredicate implements Predicate {

  /** {@inheritDoc} */
  public boolean evaluate(final Object str) {
    return (str instanceof String) && (((String) str).indexOf(Strings.DELIMITER) == -1);
  }

}
