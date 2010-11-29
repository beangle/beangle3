/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity;

import java.util.Map;

public interface Populator {

	public Object populate(Object target, Map<String, Object> params);

	public Object populate(Class<?> entityClass, Map<String, Object> params);

	public Object populate(Object target, String entityName, Map<String, Object> params);

	public Object populate(String entityName, Map<String, Object> params);

	public void populateValue(Object target, String attr, Object value);

	public void populateValue(Object target, String entityName, String attr, Object value);

	public ObjectAndType initProperty(Object target, String entityName, String attr);

}
