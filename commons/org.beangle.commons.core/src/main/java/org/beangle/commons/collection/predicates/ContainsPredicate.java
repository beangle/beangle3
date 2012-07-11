/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.predicates;

import java.util.Collection;

import org.apache.commons.collections.Predicate;

/**
 * <p>
 * ContainsPredicate class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class ContainsPredicate implements Predicate {

  private final Collection<?> objs;

  /**
   * <p>
   * Constructor for ContainsPredicate.
   * </p>
   * 
   * @param objs a {@link java.util.Collection} object.
   */
  public ContainsPredicate(Collection<?> objs) {
    super();
    this.objs = objs;
  }

  /** {@inheritDoc} */
  public boolean evaluate(Object arg0) {
    if (arg0 instanceof Collection<?>) {
      return objs.containsAll((Collection<?>) arg0);
    } else {
      return objs.contains(arg0);
    }
  }

}
