/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.dialect.Dialect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JDBC table metadata
 * 
 * @author chaostone
 */
public class TableMetadata {

	private static final Logger logger = LoggerFactory.getLogger(TableMetadata.class);

	private String catalog;
	private String schema;
	private String name;
	private Map<String, ColumnMetadata> columns = CollectUtils.newHashMap();
	private Map<String, ForeignKeyMetadata> foreignKeys = CollectUtils.newHashMap();
	private Map<String, IndexMetadata> indexes = CollectUtils.newHashMap();

	public TableMetadata() {
		super();
	}

	public TableMetadata(ResultSet rs, DatabaseMetaData meta, boolean extras) throws SQLException {
		catalog = rs.getString("TABLE_CAT");
		schema = rs.getString("TABLE_SCHEM");
		name = rs.getString("TABLE_NAME");
		initColumns(meta);
		if (extras) {
			initForeignKeys(meta);
			initIndexes(meta);
		}
		logger.debug("table found:{} {} ", qualify(catalog, schema, name), columns.keySet());
		if (extras) {
			logger.debug("foreign keys: {}", foreignKeys.keySet());
			logger.debug("indexes: {}", indexes.keySet());
		}
	}

	public String[] getColumnNames() {
		List<ColumnMetadata> columnList = new ArrayList<ColumnMetadata>(columns.values());
		Collections.sort(columnList);
		List<String> columnNames = CollectUtils.newArrayList(columnList.size());
		for (ColumnMetadata column : columnList) {
			columnNames.add(column.getName());
		}
		return columnNames.toArray(new String[columnList.size()]);
	}

	public String identifier() {
		return qualify(catalog, schema, name);
	}

	public static String qualify(String catalog, String schema, String table) {
		StringBuilder qualifiedName = new StringBuilder();
		if (catalog != null) {
			qualifiedName.append(catalog).append('.');
		}
		if (StringUtils.isNotEmpty(schema)) {
			qualifiedName.append(schema).append('.');
		}
		return qualifiedName.append(table).toString();
	}

	public String sqlInsertString() {
		String[] columnNames = getColumnNames();
		StringBuilder sb = new StringBuilder("insert into ");
		sb.append(identifier()).append("(");
		for (int i = 0; i < columnNames.length; i++) {
			sb.append(columnNames[i]).append(',');
		}
		sb.setCharAt(sb.length() - 1, ')');
		sb.append(" values(");
		sb.append(StringUtils.repeat("?,", columnNames.length));
		sb.setCharAt(sb.length() - 1, ')');
		return sb.toString();
	}

	public String sqlCreateString(Dialect dialect) {
		StringBuilder buf = new StringBuilder(dialect.getCreateTableString()).append(' ').append(
				identifier()).append(" (");
		Iterator<ColumnMetadata> iter = columns.values().iterator();
		while (iter.hasNext()) {
			ColumnMetadata col = iter.next();
			buf.append(col.getName()).append(' ');
			buf.append(col.getSqlType(dialect));

			// String defaultValue = col.getDefaultValue();
			// if (defaultValue != null) {
			// buf.append(" default ").append(defaultValue);
			// }

			// if (col.isNullable()) {
			// buf.append(dialect.getNullColumnString());
			// } else {
			// buf.append(" not null");
			// }

			// boolean useUniqueConstraint = col.isUnique()
			// && (!col.isNullable() || dialect.supportsNotNullUnique());
			// if (useUniqueConstraint) {
			// if (dialect.supportsUnique()) {
			// buf.append(" unique");
			// } else {
			// UniqueKey uk = getOrCreateUniqueKey(col.getQuotedName(dialect) +
			// '_');
			// uk.addColumn(col);
			// }
			// }
			//
			// if (col.hasCheckConstraint() && dialect.supportsColumnCheck()) {
			// buf.append(" check (").append(col.getCheckConstraint()).append(")");
			// }
			//
			// String columnComment = col.getComment();
			// if (columnComment != null) {
			// buf.append(dialect.getColumnComment(columnComment));
			// }

			if (iter.hasNext()) {
				buf.append(", ");
			}

		}
		buf.append(')');
		return buf.toString();
	}

	public String getName() {
		return name;
	}

	public String getCatalog() {
		return catalog;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, ColumnMetadata> getColumns() {
		return columns;
	}

	public String toString() {
		return qualify(catalog, schema, name);
	}

	public ColumnMetadata getColumnMetadata(String columnName) {
		return (ColumnMetadata) columns.get(columnName.toLowerCase());
	}

	public ForeignKeyMetadata getForeignKeyMetadata(String keyName) {
		return foreignKeys.get(keyName.toLowerCase());
	}

	public IndexMetadata getIndexMetadata(String indexName) {
		return indexes.get(indexName.toLowerCase());
	}

	private void addForeignKey(ResultSet rs) throws SQLException {
		String fk = rs.getString("FK_NAME");

		if (fk == null) return;

		ForeignKeyMetadata info = getForeignKeyMetadata(fk);
		if (info == null) {
			info = new ForeignKeyMetadata(rs);
			foreignKeys.put(info.getName().toLowerCase(), info);
		}

		info.addColumn(getColumnMetadata(rs.getString("FKCOLUMN_NAME")));
	}

	private void addIndex(ResultSet rs) throws SQLException {
		String index = rs.getString("INDEX_NAME");

		if (index == null) return;

		IndexMetadata info = getIndexMetadata(index);
		if (info == null) {
			info = new IndexMetadata(rs);
			indexes.put(info.getName().toLowerCase(), info);
		}

		info.addColumn(getColumnMetadata(rs.getString("COLUMN_NAME")));
	}

	public void addColumn(ResultSet rs) throws SQLException {
		String column = rs.getString("COLUMN_NAME");
		if (column == null) return;
		if (getColumnMetadata(column) == null) {
			ColumnMetadata info = new ColumnMetadata(rs);
			columns.put(info.getName().toLowerCase(), info);
		}
	}

	private void initForeignKeys(DatabaseMetaData meta) throws SQLException {
		ResultSet rs = null;

		try {
			rs = meta.getImportedKeys(catalog, schema, name);
			while (rs.next())
				addForeignKey(rs);
		} finally {
			if (rs != null) rs.close();
		}
	}

	private void initIndexes(DatabaseMetaData meta) throws SQLException {
		ResultSet rs = null;

		try {
			rs = meta.getIndexInfo(catalog, schema, name, false, true);

			while (rs.next()) {
				if (rs.getShort("TYPE") == DatabaseMetaData.tableIndexStatistic) continue;
				addIndex(rs);
			}
		} finally {
			if (rs != null) rs.close();
		}
	}

	private void initColumns(DatabaseMetaData meta) throws SQLException {
		ResultSet rs = null;
		try {
			rs = meta.getColumns(catalog, schema, name, "%");
			while (rs.next())
				addColumn(rs);
		} finally {
			if (rs != null) rs.close();
		}
	}
}
