/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.bean.predicates;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.functor.Predicate;

/**
 * Property Equals Predicate
 *
 * @author chaostone
 * @version $Id: $
 */
public class PropertyEqualPredicate<T> implements Predicate<T> {
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
  public Boolean apply(T arg0) {
    return Objects.equals(PropertyUtils.getProperty(arg0, propertyName), propertyValue);
  }

}
