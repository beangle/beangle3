/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
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
    return !idSet.contains(entity.getId());
  }
}
