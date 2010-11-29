/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.predicates;

import org.apache.commons.collections.Predicate;

public class InStrPredicate implements Predicate {

	private final String str;

	public InStrPredicate(final String str) {
		this.str = str;
	}

	public boolean evaluate(final Object arg0) {
		String target = arg0.toString();
		return -1 != str.indexOf(target);
	}

}
