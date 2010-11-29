/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.database.service;

import org.beangle.commons.collection.page.PageLimit;
import org.hibernate.dialect.Dialect;
import org.springframework.jdbc.core.JdbcTemplate;

public class SqlService extends JdbcTemplate {

	Dialect dialect;

	public int count(String sql) {
		String countSql = "select count(*) from (" + sql + ")";
		return queryForInt(countSql);
	}

	public String getLimitString(String sql, PageLimit limit) {
		try {
			int offset = (limit.getPageNo() - 1) * limit.getPageSize();
			String newSql = dialect.getLimitString(sql, offset, limit.getPageSize());
			StringBuilder sb = new StringBuilder(newSql);
			int index = sb.lastIndexOf("?");
			if (-1 != index) {
				sb.replace(index, index + 1, String.valueOf(limit.getPageSize()));
			}
			index = sb.lastIndexOf("?");
			if (-1 != index) {
				sb.replace(index, index + 1, String.valueOf(offset));
			}
			return sb.toString();
		} catch (Exception e) {
			throw new RuntimeException("cannot limit sql:" + sql, e);
		}
	}

	public Dialect getDialect() {
		return dialect;
	}

	public void setDialect(Dialect dialect) {
		this.dialect = dialect;
	}

}
