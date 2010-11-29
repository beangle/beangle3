/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate.support;

public class BeangleDB2Dialect extends org.hibernate.dialect.DB2Dialect {

	@Override
	public String getCrossJoinSeparator() {
		return ", ";
	}

}
