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
