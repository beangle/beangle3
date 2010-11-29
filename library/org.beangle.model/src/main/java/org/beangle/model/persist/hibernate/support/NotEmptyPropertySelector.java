/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate.support;

import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.type.Type;

public class NotEmptyPropertySelector implements PropertySelector {

	private static final long serialVersionUID = 2265767236729495415L;

	/**
	 * @see org.hibernate.criterion.Example.PropertySelector#include(java.lang.Object,
	 *      java.lang.String, org.hibernate.type.Type)
	 */
	public boolean include(Object object, String propertyName, Type type) {
		if (object == null) { return false; }
		if ((object instanceof Number) && ((Number) object).longValue() == 0) { return false; }
		if ("".equals(object)) { return false; }
		return true;
	}

}
