/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.commons.bean.comparators;

import java.util.Comparator;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;

/**
 * 组合比较器
 * 
 * @author chaostone
 * @version $Id: $
 */
public class ChainComparator<T> implements Comparator<T> {

  private List<Comparator<T>> comparators;

  /**
   * <p>
   * compare.
   * </p>
   * 
   * @param first a T object.
   * @param second a T object.
   * @return 0 is equals,-1 first &lt; second ,1 first &gt; second
   */
  public int compare(final T first, final T second) {
    int cmpRs = 0;
    for (final Comparator<T> com : comparators) {
      cmpRs = com.compare(first, second);
      if (0 == cmpRs) {
        continue;
      } else {
        break;
      }
    }
    return cmpRs;
  }

  /**
   * <p>
   * Constructor for ChainComparator.
   * </p>
   */
  public ChainComparator() {
    this.comparators = CollectUtils.newArrayList();
  }

  /**
   * <p>
   * Constructor for ChainComparator.
   * </p>
   * 
   * @param comparators a {@link java.util.List} object.
   */
  public ChainComparator(final List<Comparator<T>> comparators) {
    super();
    this.comparators = comparators;
  }

  /**
   * <p>
   * addComparator.
   * </p>
   * 
   * @param com a {@link java.util.Comparator} object.
   */
  public void addComparator(final Comparator<T> com) {
    this.comparators.add(com);
  }

  /**
   * <p>
   * Getter for the field <code>comparators</code>.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<Comparator<T>> getComparators() {
    return comparators;
  }

  /**
   * <p>
   * Setter for the field <code>comparators</code>.
   * </p>
   * 
   * @param comparators a {@link java.util.List} object.
   */
  public void setComparators(final List<Comparator<T>> comparators) {
    this.comparators = comparators;
  }

}
