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
 * 有效整型判断谓词
 * 
 * @author chaostone
 * @version $Id: $
 */
public class NumRangePredicate implements Predicate {

  private final int floor, upper;

  /**
   * <p>
   * Constructor for NumRangePredicate.
   * </p>
   * 
   * @param floor a int.
   * @param upper a int.
   */
  public NumRangePredicate(final int floor, final int upper) {
    this.floor = floor;
    this.upper = upper;
  }

  /** {@inheritDoc} */
  public boolean evaluate(final Object value) {
    if (null == value) {
      return false;
    } else {
      int valueInt = ((Number) value).intValue();
      return valueInt <= upper && valueInt >= floor;
    }
  }

}
