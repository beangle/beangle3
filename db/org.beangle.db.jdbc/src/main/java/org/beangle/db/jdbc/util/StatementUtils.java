/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.util;

import static java.sql.Types.BIGINT;
import static java.sql.Types.BINARY;
import static java.sql.Types.BIT;
import static java.sql.Types.BLOB;
import static java.sql.Types.BOOLEAN;
import static java.sql.Types.CHAR;
import static java.sql.Types.CLOB;
import static java.sql.Types.DATE;
import static java.sql.Types.DECIMAL;
import static java.sql.Types.DOUBLE;
import static java.sql.Types.FLOAT;
import static java.sql.Types.INTEGER;
import static java.sql.Types.LONGVARBINARY;
import static java.sql.Types.LONGVARCHAR;
import static java.sql.Types.NUMERIC;
import static java.sql.Types.SMALLINT;
import static java.sql.Types.TIMESTAMP;
import static java.sql.Types.TINYINT;
import static java.sql.Types.VARBINARY;
import static java.sql.Types.VARCHAR;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatementUtils {

	private static final Logger logger = LoggerFactory.getLogger(StatementUtils.class);

	public static void setValue(PreparedStatement ps, int index, Object value, int sqlType)
			throws SQLException {
		if (null == value) {
			ps.setNull(index, sqlType);
			return;
		}
		try {
			switch (sqlType) {
			case CHAR:
			case VARCHAR:
				ps.setString(index, (String) value);
				break;
			case LONGVARCHAR:
				ps.setAsciiStream(index, (InputStream) value);
				break;

			case BOOLEAN:
			case BIT:
				ps.setBoolean(index, (Boolean) value);
				break;

			case TINYINT:
			case SMALLINT:
			case INTEGER:
				ps.setInt(index, (Integer) value);
				break;
			case BIGINT:
				ps.setLong(index, (Long) value);
				break;

			case FLOAT:
			case DOUBLE:
				ps.setDouble(index, (Double) value);
				break;
				
			case NUMERIC:
			case DECIMAL:
				ps.setBigDecimal(index, (BigDecimal) value);
				break;

			case DATE:
				ps.setDate(index, (Date) value);
				break;
			case TIMESTAMP:
				ps.setTimestamp(index, (Timestamp) value);
				break;

			case BINARY:
			case VARBINARY:
			case LONGVARBINARY:
				ps.setBinaryStream(index, (InputStream) value);
				break;

			case CLOB:
				ps.setClob(index, (Clob) value);
				break;
			case BLOB:
				ps.setBlob(index, (Blob) value);
				break;
			default:
				logger.warn("unsupported type {}", sqlType);
			}
		} catch (Exception e) {
			logger.error("set value error", e);
			e.printStackTrace();
		}

	}
}
