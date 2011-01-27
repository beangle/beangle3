/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.predicates;

import org.apache.commons.collections.Predicate;
import org.beangle.commons.lang.StrUtils;

public class SingleWordStringPredicate implements Predicate {

	public boolean evaluate(final Object str) {
		return (str instanceof String) && (((String) str).indexOf(StrUtils.DELIMITER) == -1);
	}

}
