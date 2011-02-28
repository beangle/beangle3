/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.grammar;

public interface LimitGrammar {

	/**
	 * ANSI SQL defines the LIMIT clause to be in the form LIMIT offset, limit.
	 * Does this dialect require us to bind the parameters in reverse order?
	 * 
	 * @return true if the correct order is limit, offset
	 */
	public boolean isBindInReverseOrder();

	public boolean isBindFirst();

	public boolean isUseMax();

	public String limit(String query, boolean hasOffset);

}
