/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.bean.comparators;

import org.beangle.commons.lang.Strings;

/**
 * 多个属性的比较
 * 
 * @author chaostone
 * @version $Id: $
 */
public class MultiPropertyComparator<T> extends ChainComparator<T> {

  /**
   * <p>
   * Constructor for MultiPropertyComparator.
   * </p>
   * 
   * @param propertyStr a {@link java.lang.String} object.
   * @param <T> a T object.
   */
  public MultiPropertyComparator(final String propertyStr) {
    super();
    final String[] properties = Strings.split(propertyStr, ',');
    for (int i = 0; i < properties.length; i++) {
      addComparator(new PropertyComparator<T>(properties[i].trim()));
    }
  }

}
