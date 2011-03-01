/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.wrappers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;

import org.beangle.commons.collection.page.PageLimit;
import org.beangle.db.jdbc.dialect.Dialect;
import org.beangle.db.jdbc.grammar.LimitGrammar;
import org.beangle.db.jdbc.meta.Column;
import org.beangle.db.jdbc.meta.Database;
import org.beangle.db.jdbc.meta.Sequence;
import org.beangle.db.jdbc.meta.Table;
import org.beangle.db.jdbc.util.StatementUtils;
import org.beangle.db.replication.DataWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.jdbc.support.JdbcUtils;

public class DatabaseWrapper extends JdbcTemplate implements DataWrapper {

	protected static final Logger logger = LoggerFactory.getLogger(DatabaseWrapper.class.getName());

	protected Database database;
	protected String productName;

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
			logger.error("Cannot build connection using:{} under dialect {}", dataSource, dialect);
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
			Table existed = database.getTables().get(tablename);
			if (null != existed) {
				database.getTables().remove(tablename);
				execute(database.getDialect().getTableGrammar().dropCascade(tablename));
			}
		} catch (Exception e) {
			logger.error("Drop table " + table.getName() + " failed", e);
			return false;
		}
		return true;
	}

	public boolean create(Table table) {
		if (null == database.getTable(table.identifier())) {
			try {
				execute(table.getCreateSql(database.getDialect()));
			} catch (Exception e) {
				logger.warn("Cannot create table {}", table.getName());
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public boolean drop(Sequence sequence) {
		boolean exists = database.getSequences().contains(sequence);
		if (exists) {
			database.getSequences().remove(sequence);
			try {
				String dropSql = sequence.sqlDropString(getDialect());
				if (null != dropSql) execute(dropSql);
			} catch (Exception e) {
				logger.error("Drop sequence " + sequence.getName() + " failed", e);
				return false;
			}
		}
		return true;
	}

	public boolean create(Sequence sequence) {
		try {
			String createSql = sequence.sqlCreateString(getDialect());
			if (null != createSql) execute(createSql);
		} catch (Exception e) {
			logger.error("cannot create sequence " + sequence.getName(), e);
			return false;
		}
		return true;
	}

	public int count(Table table) {
		String countStr = "select count(*) from (" + table.getQuerySql() + ")";
		return queryForInt(countStr);
	}

	public List<Object> getData(Table table, PageLimit limit) {
		String sql = table.getQuerySql();
		LimitGrammar grammar = database.getDialect().getLimitGrammar();
		String limitSql = grammar.limit(sql, limit.getPageNo() > 1);

		Object offset = (limit.getPageNo() - 1) * limit.getPageSize();
		Object limitOrMax = limit.getPageSize();
		if (grammar.isUseMax()) limitOrMax = limit.getPageNo() * limit.getPageSize();

		if (limit.getPageNo() == 1) {
			return query(limitSql, new Object[] { limitOrMax });
		} else {
			boolean reverse = grammar.isBindInReverseOrder();
			if (reverse) return query(limitSql, new Object[] { limitOrMax, offset });
			else return query(limitSql, new Object[] { offset, limitOrMax });
		}
	}

	public List<Object> getData(Table table) {
		return query(table.getQuerySql());
	}

	public int pushData(final Table table, List<Object> datas) {
		final String[] columnNames = table.getColumnNames();
		String insertSql = table.getInsertSql();
		int successed = 0;
		PreparedStatement ps = null;
		Connection conn = DataSourceUtils.getConnection(getDataSource());
		Object[] data = null;
		try {
			conn.setAutoCommit(false);
			ps = conn.prepareStatement(insertSql);
			for (Object item : datas) {
				data = (Object[]) item;
				for (int i = 0; i < columnNames.length; i++) {
					Column cm = table.getColumn(columnNames[i]);
					StatementUtils.setValue(ps, i + 1, data[i], cm.getTypeCode());
				}
				ps.execute();
				successed++;
			}
			return successed;
		} catch (Exception e) {
			logger.error("Cannot insert sql {}", insertSql);
			e.printStackTrace();
			return 0;
		} finally {
			try {
				if (null != conn) {
					conn.commit();
					conn.setAutoCommit(true);
				}
			} catch (SQLException e) {
				logger.error("Cannot commit connection");
			}
			JdbcUtils.closeStatement(ps);
			ps = null;
			DataSourceUtils.releaseConnection(conn, getDataSource());
			conn = null;
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
					for (int i = 1; i <= columnCount; i++) {
						Object obj;
						if (Types.TIMESTAMP == rs.getMetaData().getColumnType(i)) obj = rs.getTimestamp(i);
						else obj = rs.getObject(i);
						row[i - 1] = obj;
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
}
