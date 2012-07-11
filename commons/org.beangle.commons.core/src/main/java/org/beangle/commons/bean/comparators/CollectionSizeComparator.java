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
   * @param object1 a T object.
   * @param object2 a T object.
   * @param <T> a T object.
   * @return a int.
   */
  public int compare(final T object1, final T object2) {
    Collection<?> first = object1;
    Collection<?> second = object2;
    if (first.equals(second)) {
      return 0;
    } else {
      return first.size() - second.size();
    }
  }
}
