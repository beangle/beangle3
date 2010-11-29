/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.predicates;

import java.util.Collection;

import org.apache.commons.collections.Predicate;

public class CollectionHasUpto1ElementPredicate implements Predicate {

	public boolean evaluate(final Object object) {
		boolean success = true;
		if (object instanceof Collection<?>) {
			success = ((Collection<?>) object).size() < 2;
		}
		return success;
	}

}
