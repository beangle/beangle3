/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import static java.sql.Types.BIGINT;
import static java.sql.Types.CHAR;
import static java.sql.Types.INTEGER;
import static java.sql.Types.VARCHAR;

import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TypeUtils {

	private static final Logger logger = LoggerFactory.getLogger(TypeUtils.class);

	public static void setValue(PreparedStatement ps, int index, Object value, int sqlType)
			throws SQLException {
		if (null == value) {
			ps.setNull(index, sqlType);
			return;
		}
		try {
			switch (sqlType) {
			case BIGINT:
				ps.setLong(index, (Long) value);
				break;
			case INTEGER:
				ps.setInt(index, (Integer) value);
				break;
			case CHAR:
				ps.setString(index, (String) value);
				break;
			case VARCHAR:
				ps.setString(index, (String) value);
				break;
			case Types.DATE:
				ps.setDate(index, (Date) value);
				break;
			case Types.TIMESTAMP:
				if (value instanceof oracle.sql.TIMESTAMP) {
					ps.setTimestamp(index, ((oracle.sql.TIMESTAMP) value).timestampValue());
				} else {
					ps.setTimestamp(index, (Timestamp) value);
				}
				break;
			case Types.BOOLEAN:
				ps.setBoolean(index, (Boolean) value);
				break;
			case Types.FLOAT:
			case Types.DECIMAL:
				ps.setBigDecimal(index, (BigDecimal) value);
				break;
			case Types.LONGVARCHAR:
				ps.setString(index, (String) value);
				break;
			case Types.CLOB:
				ps.setClob(index, (Clob) value);
				break;
			case Types.BLOB:
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
