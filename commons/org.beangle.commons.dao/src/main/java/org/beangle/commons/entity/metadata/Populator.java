/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.metadata;

import java.util.Map;

/**
 * <p>
 * Populator interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface Populator {

  /**
   * <p>
   * populate.
   * </p>
   * 
   * @param target a {@link java.lang.Object} object.
   * @param params a {@link java.util.Map} object.
   * @return a {@link java.lang.Object} object.
   */
  Object populate(Object target, Map<String, Object> params);

  /**
   * <p>
   * populate.
   * </p>
   * 
   * @param entityClass a {@link java.lang.Class} object.
   * @param params a {@link java.util.Map} object.
   * @return a {@link java.lang.Object} object.
   */
  Object populate(Class<?> entityClass, Map<String, Object> params);

  /**
   * <p>
   * populate.
   * </p>
   * 
   * @param target a {@link java.lang.Object} object.
   * @param entityName a {@link java.lang.String} object.
   * @param params a {@link java.util.Map} object.
   * @return a {@link java.lang.Object} object.
   */
  Object populate(Object target, String entityName, Map<String, Object> params);

  /**
   * <p>
   * populate.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   * @param params a {@link java.util.Map} object.
   * @return a {@link java.lang.Object} object.
   */
  Object populate(String entityName, Map<String, Object> params);

  /**
   * Set value to target's attribute.
   * 
   * @param target
   * @param attr
   * @param value
   * @return true if success
   */
  boolean populateValue(Object target, String attr, Object value);

  /**
   * @param target
   * @param entityName
   * @param attr
   * @param value
   * @return true when success populate.
   */
  boolean populateValue(Object target, String entityName, String attr, Object value);

  /**
   * <p>
   * initProperty.
   * </p>
   * 
   * @param target a {@link java.lang.Object} object.
   * @param entityName a {@link java.lang.String} object.
   * @param attr a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.entity.metadata.ObjectAndType} object.
   */
  ObjectAndType initProperty(Object target, String entityName, String attr);

}
