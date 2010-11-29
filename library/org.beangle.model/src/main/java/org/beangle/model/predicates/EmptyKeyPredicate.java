/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.predicates;

import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.StringUtils;

public class EmptyKeyPredicate implements Predicate {

	public boolean evaluate(final Object value) {
		boolean success = false;
		if (null != value) {
			if (value instanceof String) {
				success = StringUtils.isEmpty((String) value);
			} else if (value instanceof Number) {
				success = (0 == ((Number) value).intValue());
			} else {
				throw new RuntimeException("unsupported key type");
			}
		}
		return success;
	}

}
