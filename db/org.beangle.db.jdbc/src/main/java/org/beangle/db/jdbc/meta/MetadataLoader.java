/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.meta;

import static org.apache.commons.lang.StringUtils.lowerCase;
import static org.apache.commons.lang.StringUtils.replace;
import static org.apache.commons.lang.StringUtils.upperCase;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.jdbc.dialect.Dialect;
import org.beangle.db.jdbc.grammar.SequenceGrammar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MetadataLoader {
	private static final Logger logger = LoggerFactory.getLogger(MetadataLoader.class);
	private static final String[] TYPES = { "TABLE", "VIEW" };

	private Dialect dialect;
	private DatabaseMetaData meta;
	private Map<String, Table> tables = CollectUtils.newHashMap();

	public MetadataLoader(Dialect dialect, DatabaseMetaData meta) {
		super();
		this.dialect = dialect;
		this.meta = meta;
	}

	public Set<Table> loadTables(String catalog, String schema, boolean extras) {
		try {
			ResultSet rs = null;
			try {
				if (meta.storesLowerCaseQuotedIdentifiers() && meta.storesLowerCaseIdentifiers()) {
					rs = meta.getTables(lowerCase(catalog), lowerCase(schema), null, TYPES);
				} else if (meta.storesUpperCaseQuotedIdentifiers() && meta.storesUpperCaseIdentifiers()) {
					rs = meta.getTables(upperCase(catalog), upperCase(schema), null, TYPES);
				} else {
					rs = meta.getTables(catalog, schema, null, TYPES);
				}
				Queue<String> tableNames = CollectUtils.newConcurrentLinkedQueue();
				while (rs.next()) {
					String tableName = rs.getString("TABLE_NAME");
					tableNames.add(tableName);
					try {
						String tableSchema = rs.getString("TABLE_SCHEM");
						Table table = loadTable(tableSchema, tableName, extras);
						tables.put(table.identifier(), table);
						logger.info("load {}", tableName);
					} catch (Exception e) {
						logger.warn("cannot load metada for {}", tableName);
					}
				}
			} finally {
				if (rs != null) rs.close();
			}
		} catch (SQLException sqle) {
			throw new RuntimeException(sqle);
		}
		return CollectUtils.newHashSet(tables.values());
	}

	private void loadTableColumns(Table table) throws SQLException {
		ResultSet rs = null;
		try {
			rs = meta.getColumns(null, table.getSchema(), table.getName(), "%");
			while (rs.next()) {
				String column = rs.getString("COLUMN_NAME");
				if (column == null) return;
				if (table.getColumn(column) == null) {
					table.addColumn(new Column(rs));
				}
			}
		} finally {
			if (rs != null) rs.close();
		}
	}

	public Table loadTable(String schema, String name, boolean extras) throws SQLException {
		Table table = tables.get(Table.qualify(schema, name));
		if (null == table) table = new Table(schema, name);
		loadTableColumns(table);
		loadPrimaryKeys(table);
		if (extras) {
			loadTableForeignKeys(table);
			loadTableIndexes(table);
		}
		logger.debug("table found:{} {} ", Table.qualify(schema, name), table.getColumnNames());
		if (extras) {
			logger.debug("foreign keys: {}", table.getForeignKeys().keySet());
			logger.debug("indexes: {}", table.getIndexes().keySet());
		}
		return table;
	}

	private void loadPrimaryKeys(Table table) throws SQLException {
		ResultSet rs = null;
		try {
			rs = meta.getPrimaryKeys(null, table.getSchema(), table.getName());
			PrimaryKey pk = null;
			while (rs.next()) {
				if (null == pk) pk = new PrimaryKey();
				pk.addColumn(table.getColumn(rs.getString("COLUMN_NAME")));
			}
			if (null != pk) {
				table.setPrimaryKey(pk);
			}
		} finally {
			if (rs != null) rs.close();
		}
	}

	private void loadTableForeignKeys(Table table) throws SQLException {
		ResultSet rs = null;
		try {
			rs = meta.getImportedKeys(null, table.getSchema(), table.getName());
			while (rs.next()) {
				String fk = rs.getString("FK_NAME");
				if (fk == null) continue;
				ForeignKey info = table.getForeignKey(fk);
				if (info != null) continue;
				info = new ForeignKey(rs.getString("FK_NAME"), table.getColumn(rs.getString("FKCOLUMN_NAME")));
				info.addReferencedColumn(new Column(rs.getString("PKCOLUMN_NAME"), Types.BIGINT));
				Table referencedTable = tables.get(Table.qualify(rs.getString("PKTABLE_SCHEM"),
						rs.getString("PKTABLE_NAME")));
				if (null == referencedTable) {
					referencedTable = new Table(rs.getString("PKTABLE_SCHEM"), rs.getString("PKTABLE_NAME"));
				}
				info.setReferencedTable(referencedTable);
				info.setCascadeDelete((rs.getInt("DELETE_RULE") != 3));
				table.addForeignKey(info);
			}
		} finally {
			if (rs != null) rs.close();
		}
	}

	private void loadTableIndexes(Table table) throws SQLException {
		ResultSet rs = null;
		try {
			rs = meta.getIndexInfo(null, table.getSchema(), table.getName(), false, true);
			while (rs.next()) {
				if (rs.getShort("TYPE") == DatabaseMetaData.tableIndexStatistic) continue;
				String index = rs.getString("INDEX_NAME");
				if (index == null) return;
				Index info = table.getIndex(index);
				if (info == null) {
					info = new Index(rs.getString("INDEX_NAME"));
					table.addIndex(info);
				}
				info.addColumn(table.getColumn(rs.getString("COLUMN_NAME")));
			}
		} finally {
			if (rs != null) rs.close();
		}
	}

	public Set<Sequence> loadSequences(Connection connection, String schema) throws SQLException {
		Set<Sequence> sequences = CollectUtils.newHashSet();
		SequenceGrammar ss = dialect.getSequenceGrammar();
		if (null == ss) return sequences;
		String sql = ss.getQuerySequenceSql();
		sql = replace(sql, ":schema", schema);
		if (sql != null) {
			Statement statement = null;
			ResultSet rs = null;
			try {
				statement = connection.createStatement();
				rs = statement.executeQuery(sql);
				Set<String> columnNames = CollectUtils.newHashSet();
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					columnNames.add(rs.getMetaData().getColumnLabel(i).toLowerCase());
				}
				while (rs.next()) {
					Sequence sequence = new Sequence(rs.getString("sequence_name").toLowerCase().trim());
					if (columnNames.contains("current_value")) {
						sequence.setCurrent(Long.valueOf(rs.getString("current_value")));
					} else if (columnNames.contains("next_value")) {
						sequence.setCurrent(Long.valueOf(rs.getString("next_value")) - 1);
					}
					if (columnNames.contains("increment")) {
						sequence.setIncrement(Integer.valueOf(rs.getString("increment")));
					}
					if (columnNames.contains("cache")) {
						sequence.setCache(Integer.valueOf(rs.getString("cache")));
					}
					sequences.add(sequence);
				}
			} finally {
				if (rs != null) rs.close();
				if (statement != null) statement.close();
			}
		}
		return sequences;
	}
}
