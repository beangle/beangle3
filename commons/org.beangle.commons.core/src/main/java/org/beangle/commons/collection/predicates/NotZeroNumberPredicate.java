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
