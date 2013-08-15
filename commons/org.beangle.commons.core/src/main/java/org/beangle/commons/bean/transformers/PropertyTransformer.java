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
package org.beangle.commons.bean.transformers;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.lang.functor.Transformer;

/**
 * bean属性提取器<br>
 * CollectionUtils.transform(collections,new PropertyTransformer('myAttr'))
 * 
 * @author chaostone
 * @version $Id: $
 */
public class PropertyTransformer<R> implements Transformer<Object, R> {

  private String property;

  /**
   * <p>
   * Constructor for PropertyTransformer.
   * </p>
   * 
   * @param property a {@link java.lang.String} object.
   */
  public PropertyTransformer(final String property) {
    super();
    this.property = property;
  }

  /**
   * <p>
   * Constructor for PropertyTransformer.
   * </p>
   */
  public PropertyTransformer() {
    super();
  }

  @SuppressWarnings("unchecked")
  public R apply(final Object arg0) {
    return (R) PropertyUtils.getProperty(arg0, property);
  }

  /**
   * <p>
   * Getter for the field <code>property</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getProperty() {
    return property;
  }

  /**
   * <p>
   * Setter for the field <code>property</code>.
   * </p>
   * 
   * @param property a {@link java.lang.String} object.
   */
  public void setProperty(final String property) {
    this.property = property;
  }

}
