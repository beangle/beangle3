/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.predicates;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

public class NotEmptyStringPredicate implements Predicate {
	public static final NotEmptyStringPredicate INSTANCE = new NotEmptyStringPredicate();

	/**
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(final Object value) {
		return (null != value) && (value instanceof String) && StringUtils.isNotEmpty((String) value);
	}

}
