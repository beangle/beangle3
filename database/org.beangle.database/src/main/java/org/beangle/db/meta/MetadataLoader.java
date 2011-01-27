/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.dialect.Dialect;
import org.beangle.db.dialect.SequenceSupport;
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
				if (meta.storesUpperCaseQuotedIdentifiers() && meta.storesUpperCaseIdentifiers()) {
					rs = meta.getTables(StringUtils.upperCase(catalog),
							StringUtils.upperCase(schema), null, TYPES);
				} else if (meta.storesLowerCaseQuotedIdentifiers()
						&& meta.storesLowerCaseIdentifiers()) {
					rs = meta.getTables(StringUtils.lowerCase(catalog),
							StringUtils.lowerCase(schema), null, TYPES);
				} else {
					rs = meta.getTables(catalog, schema, null, TYPES);
				}
				while (rs.next()) {
					String tableName = rs.getString("TABLE_NAME");
					try {
						String tableSchema=rs.getString("TABLE_SCHEM");
						Table table = loadTable(tableSchema, tableName, extras);
						tables.put(table.identifier(), table);
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
			while (rs.next()) {
				table.setPrimaryKey(new PrimaryKey(rs.getString("PK_NAME"), table.getColumn(rs
						.getString("COLUMN_NAME"))));
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
				info = new ForeignKey(rs.getString("FK_NAME"), table.getColumn(rs
						.getString("FKCOLUMN_NAME")));
				info.addReferencedColumn(new Column(rs.getString("PKCOLUMN_NAME")));
				Table referencedTable = tables.get(Table.qualify(rs.getString("PKTABLE_SCHEM"),
						rs.getString("PKTABLE_NAME")));
				if (null == referencedTable) {
					referencedTable = new Table(rs.getString("PKTABLE_SCHEM"),
							rs.getString("PKTABLE_NAME"));
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

	public Set<Sequence> loadSequences(Connection connection) throws SQLException {
		Set<Sequence> sequences = CollectUtils.newHashSet();
		SequenceSupport ss = dialect.getSequenceSupport();
		if (null == ss) return sequences;
		String sql = ss.getQuerySequenceSql();
		if (sql != null) {
			Statement statement = null;
			ResultSet rs = null;
			try {
				statement = connection.createStatement();
				rs = statement.executeQuery(sql);
				while (rs.next()) {
					sequences.add(new Sequence(rs.getString(1).toLowerCase().trim()));
				}
			} finally {
				if (rs != null) rs.close();
				if (statement != null) statement.close();
			}
		}
		return sequences;
	}
}
