/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.predicates;

import org.apache.commons.collections.Predicate;
import org.beangle.commons.lang.Strings;

/**
 * <p>
 * NotEmptyStringPredicate class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class NotEmptyStringPredicate implements Predicate {
  /** Constant <code>INSTANCE</code> */
  public static final NotEmptyStringPredicate INSTANCE = new NotEmptyStringPredicate();

  /** {@inheritDoc} */
  public boolean evaluate(final Object value) {
    return (null != value) && (value instanceof String) && Strings.isNotEmpty((String) value);
  }

}
