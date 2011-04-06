/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.predicates;

import java.util.Collection;

import org.apache.commons.collections.Predicate;

public class ContainsPredicate implements Predicate {

	private final Collection<?> objs;

	public ContainsPredicate(Collection<?> objs) {
		super();
		this.objs = objs;
	}

	public boolean evaluate(Object arg0) {
		if (arg0 instanceof Collection<?>) {
			return objs.containsAll((Collection<?>) arg0);
		} else {
			return objs.contains(arg0);
		}
	}

}
