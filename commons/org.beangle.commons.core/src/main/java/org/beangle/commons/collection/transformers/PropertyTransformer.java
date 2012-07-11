/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.transformers;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Transformer;

/**
 * bean属性提取器<br>
 * CollectionUtls.transform(collections,new PropertyTransformer('myAttr'))
 * 
 * @author chaostone
 * @version $Id: $
 */
public class PropertyTransformer implements Transformer {

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

  /** {@inheritDoc} */
  public Object transform(final Object arg0) {
    try {
      return PropertyUtils.getProperty(arg0, property);
    } catch (Exception e) {
      return null;
    }
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
