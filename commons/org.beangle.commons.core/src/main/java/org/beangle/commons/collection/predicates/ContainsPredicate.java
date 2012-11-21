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
