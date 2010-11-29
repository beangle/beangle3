/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.predicates;

import org.apache.commons.collections.Predicate;

/**
 * 有效整型判断谓词
 * 
 * @author chaostone
 */
public class ValidNumScopePredicate implements Predicate {

	private final int floor, upper;

	public ValidNumScopePredicate(final int floor, final int upper) {
		this.floor = floor;
		this.upper = upper;
	}

	/**
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(final Object value) {
		if (null == value) {
			return false;
		} else {
			int valueInt = ((Number) value).intValue();
			return valueInt <= upper && valueInt >= floor;
		}
	}

}
