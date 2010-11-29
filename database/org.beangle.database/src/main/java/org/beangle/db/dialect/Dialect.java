/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

public interface Dialect {

	public SequenceSupport getSequenceSupport();

	String getTypeName(int typecode, int size, int precision, int scale);

	String getTypeName(int typecode);

	String getCreateTableString();

	String getLimitString(String sql, boolean hasOffset);

}
