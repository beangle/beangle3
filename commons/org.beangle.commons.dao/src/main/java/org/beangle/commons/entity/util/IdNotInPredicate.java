/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.apache.commons.collections.Predicate;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Entity;

/**
 * <p>
 * IdNotInPredicate class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class IdNotInPredicate implements Predicate {

  private final Set<Serializable> idSet;

  /**
   * <p>
   * Constructor for IdNotInPredicate.
   * </p>
   * 
   * @param ids a {@link java.util.Collection} object.
   */
  public IdNotInPredicate(Collection<Serializable> ids) {
    idSet = CollectUtils.newHashSet(ids);
  }

  /** {@inheritDoc} */
  public boolean evaluate(Object arg0) {
    Entity<?> entity = (Entity<?>) arg0;
    return !idSet.contains(entity.getIdentifier());
  }
}