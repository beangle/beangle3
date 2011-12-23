/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.util;

import java.io.Serializable;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.Predicate;

/**
 * 有效实体判断谓词
 * 
 * @author chaostone
 */
public class ValidEntityPredicate implements Predicate {

	/**
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(final Object value) {
		if (null == value) { return false; }
		try {
			Serializable key = (Serializable) PropertyUtils.getProperty(value, "id");
			return ValidEntityKeyPredicate.getInstance().evaluate(key);
		} catch (Exception e) {
			return false;
		}
	}

	public static final ValidEntityPredicate INSTANCE = new ValidEntityPredicate();

	public static ValidEntityPredicate getInstance() {
		return INSTANCE;
	}
}
