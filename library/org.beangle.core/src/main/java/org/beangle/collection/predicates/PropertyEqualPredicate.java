/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.collection.predicates;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.UnhandledException;
import org.apache.commons.lang.Validate;

/**
 * Property Equals Predicate
 * 
 * @author chaostone
 */
public class PropertyEqualPredicate {
	private String propertyName;
	private Object propertyValue;

	public PropertyEqualPredicate(String propertyName, Object propertyValue) {
		Validate.notEmpty(propertyName, "propertyName");
		this.propertyName = propertyName;
		this.propertyValue = propertyValue;
	}

	public boolean evaluate(Object arg0) {
		try {
			return ObjectUtils.equals(PropertyUtils.getProperty(arg0, propertyName), propertyValue);
		} catch (Exception e) {
			throw new UnhandledException(e);
		}
	}

}
