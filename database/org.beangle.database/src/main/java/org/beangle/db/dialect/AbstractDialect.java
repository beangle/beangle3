/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

public abstract class AbstractDialect implements Dialect {
	protected TypeNames typeNames = new TypeNames();
	protected SequenceSupport ss = null;

	public String getCreateTableString() {
		return "create table";
	}

	protected void registerColumnType(int code, int capacity, String name) {
		typeNames.put(code, capacity, name);
	}

	protected void registerColumnType(int code, String name) {
		typeNames.put(code, name);
	}

	public SequenceSupport getSequenceSupport() {
		return ss;
	}

	public String getTypeName(int typecode, int size, int precision, int scale) {
		return typeNames.get(typecode, size, precision, scale);
	}

	public String getTypeName(int typecode) {
		return typeNames.get(typecode);
	}

}
