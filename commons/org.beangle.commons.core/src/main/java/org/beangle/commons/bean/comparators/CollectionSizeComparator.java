/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.bean.comparators;

import java.util.Collection;
import java.util.Comparator;

/**
 * 比较两个集合，元素多的大
 * 
 * @author chaostone
 * @version $Id: $
 */
public class CollectionSizeComparator<T extends Collection<?>> implements Comparator<T> {

  /**
   * <p>
   * compare.
   * </p>
   * 
   * @param first a T object.
   * @param second a T object.
   * @return equals : 0,first less then second : -1 or small , first greate then second : 1 or big
   */
  public int compare(final T first, final T second) {
    if (first.equals(second)) return 0;
    else return first.size() - second.size();
  }
}
