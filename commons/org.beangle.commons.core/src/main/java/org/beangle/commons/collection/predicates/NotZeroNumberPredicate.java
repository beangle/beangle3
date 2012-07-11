/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.predicates;

import org.apache.commons.collections.Predicate;

/**
 * <p>
 * NotZeroNumberPredicate class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class NotZeroNumberPredicate implements Predicate {

  /** {@inheritDoc} */
  public boolean evaluate(final Object value) {
    return (value instanceof Number) && (0 != ((Number) value).intValue());
  }

  /** Constant <code>INSTANCE</code> */
  public static final NotZeroNumberPredicate INSTANCE = new NotZeroNumberPredicate();

  /**
   * <p>
   * getInstance.
   * </p>
   * 
   * @return a {@link org.beangle.commons.collection.predicates.NotZeroNumberPredicate} object.
   */
  public static NotZeroNumberPredicate getInstance() {
    return INSTANCE;
  }
}
