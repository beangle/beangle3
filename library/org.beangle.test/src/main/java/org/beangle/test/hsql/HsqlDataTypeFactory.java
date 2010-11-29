/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.test.hsql;

import java.sql.Types;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;

public class HsqlDataTypeFactory extends DefaultDataTypeFactory {

	/**
	 * since jdk 1.4
	 */
	public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
		if (sqlType == Types.BOOLEAN) { return DataType.BOOLEAN; }
		return super.createDataType(sqlType, sqlTypeName);
	}

}
