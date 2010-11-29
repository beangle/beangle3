/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.predicates;

import org.apache.commons.collections.Predicate;

public class NotZeroNumberPredicate implements Predicate {

	/**
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(final Object value) {
		return (value instanceof Number) && (0 != ((Number) value).intValue());
	}

	public static final NotZeroNumberPredicate INSTANCE = new NotZeroNumberPredicate();

	public static NotZeroNumberPredicate getInstance() {
		return INSTANCE;
	}
}
