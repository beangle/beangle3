/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.predicates;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Throwables;

/**
 * Property Equals Predicate
 * 
 * @author chaostone
 * @version $Id: $
 */
public class PropertyEqualPredicate {
  private String propertyName;
  private Object propertyValue;

  /**
   * <p>
   * Constructor for PropertyEqualPredicate.
   * </p>
   * 
   * @param propertyName a {@link java.lang.String} object.
   * @param propertyValue a {@link java.lang.Object} object.
   */
  public PropertyEqualPredicate(String propertyName, Object propertyValue) {
    Assert.notEmpty(propertyName);
    this.propertyName = propertyName;
    this.propertyValue = propertyValue;
  }

  /**
   * <p>
   * evaluate.
   * </p>
   * 
   * @param arg0 a {@link java.lang.Object} object.
   * @return a boolean.
   */
  public boolean evaluate(Object arg0) {
    try {
      return Objects.equals(PropertyUtils.getProperty(arg0, propertyName), propertyValue);
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }

}
