/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.lang.functor.Predicate;

/**
 * <p>
 * IdNotInPredicate class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class IdNotInPredicate implements Predicate<Entity<?>> {

  private final Set<Serializable> ids;

  /**
   * <p>
   * Constructor for IdNotInPredicate.
   * </p>
   * 
   * @param ids a {@link java.util.Collection} object.
   */
  public IdNotInPredicate(Collection<Serializable> ids) {
    this.ids = CollectUtils.newHashSet(ids);
  }

  public Boolean apply(Entity<?> entity) {
    return !ids.contains(entity.getId());
  }
}
