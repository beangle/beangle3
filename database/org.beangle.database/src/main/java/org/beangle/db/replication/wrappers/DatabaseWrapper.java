/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.wrappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.db.dialect.Dialect;
import org.beangle.db.meta.Column;
import org.beangle.db.meta.Database;
import org.beangle.db.meta.Table;
import org.beangle.db.meta.TypeUtils;
import org.beangle.db.replication.DataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class DatabaseWrapper extends JdbcTemplate implements DataWrapper {

	protected static final Logger logger = LoggerFactory.getLogger(DatabaseWrapper.class.getName());

	protected Database database;
	protected String productName;
	protected List<String> tableNames = CollectUtils.newArrayList();
	protected List<String> viewNames = CollectUtils.newArrayList();
	protected List<String> sequenceNames = CollectUtils.newArrayList();

	public DatabaseWrapper(DataSource dataSource, Dialect dialect, String catalog, String schema) {
		this(dataSource, dialect, catalog, schema, true);
	}

	public DatabaseWrapper(DataSource dataSource, Dialect dialect, String catalog, String schema,
			boolean extras) {
		try {
			setDataSource(dataSource);
			database = new Database(dataSource.getConnection().getMetaData(), dialect, catalog, schema);
			database.loadTables(extras);
		} catch (SQLException e) {
			logger.error("cannot build connection using:{} under dialect {}", dataSource, dialect);
			throw new RuntimeException(e);
		}
	}

	public List<Object> getData(String tableName) {
		Table tableMeta = database.getTables().get(tableName);
		if (null == tableMeta) {
			return Collections.emptyList();
		} else {
			return getData(tableMeta);
		}
	}

	public boolean drop(Table table) {
		try {
			String tablename = Table.qualify(database.getSchema(), table.getName());
			database.getTables().remove(tablename);
			execute("drop table " + tablename);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public int count(Table table) {
		String sql = getQueryString(table);
		String countStr = "select count(*) from (" + sql + ")";
		return queryForInt(countStr);
	}

	public List<Object> getData(Table table, PageLimit limit) {
		String sql = getQueryString(table);
		String limitSql = database.getDialect().getLimitString(sql, limit.getPageNo() > 1);
		if (limit.getPageNo() == 1) {
			return query(limitSql, new Object[] { limit.getPageSize() });
		} else {
			return query(limitSql, new Object[] { limit.getPageNo() * limit.getPageSize(),
					(limit.getPageNo() - 1) * limit.getPageSize() });
		}
	}

	public List<Object> getData(Table table) {
		return query(getQueryString(table));
	}

	private String getQueryString(Table table) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		String[] columnNames = table.getColumnNames();
		for (String columnName : columnNames) {
			sb.append(columnName).append(',');
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ").append(table.getName());
		return sb.toString();
	}

	public int pushData(final Table table, List<Object> datas) {
		if (null == database.getTable(Table.qualify(database.getSchema(), table.getName()))) {
			try {
				execute(table.sqlCreateString(database.getDialect()));
			} catch (Exception e) {
				logger.warn("cannot create table {}", table.getName());
				e.printStackTrace();
			}
		}
		final String[] columnNames = table.getColumnNames();
		String insertSql = table.genInsertSql();
		int successed = 0;
		try {
			Connection conn = getDataSource().getConnection();
			conn.setAutoCommit(false);
			PreparedStatement ps = conn.prepareStatement(insertSql);
			for (Object item : datas) {
				try {
					final Object[] data = (Object[]) item;
					for (int i = 0; i < columnNames.length; i++) {
						Column cm = table.getColumn(columnNames[i]);
						TypeUtils.setValue(ps, i + 1, data[i], cm.getTypeCode());
					}
					ps.execute();
					successed++;
				} catch (Exception e) {

				}
			}
			ps.close();
			conn.commit();
			conn.close();
			return successed;
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

	public void close() {
	}

	public List<Object> query(String sql) {
		return query(sql, (Object[]) null);
	}

	public List<Object> query(String sql, Object[] params) {
		return query(sql, params, new RowMapper<Object>() {
			public Object mapRow(ResultSet rs, int index) throws SQLException {
				int columnCount = rs.getMetaData().getColumnCount();
				if (columnCount == 1) {
					return rs.getObject(1);
				} else {
					Object[] row = new Object[columnCount];
					for (int i = 0; i < columnCount; i++) {
						row[i] = rs.getObject(i + 1);
					}
					return row;
				}
			}
		});
	}

	public void setDatabase(Database metadata) {
		this.database = metadata;
	}

	public Database getDatabase() {
		return database;
	}

	public Dialect getDialect() {
		return database.getDialect();
	}

	public void applyIndexAndContaint(Table table) {

	}
}
