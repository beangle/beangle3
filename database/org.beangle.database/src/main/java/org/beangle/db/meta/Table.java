/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.dialect.Dialect;

/**
 * JDBC table metadata
 * 
 * @author chaostone
 */
public class Table {
	private String schema;
	private String name;
	private PrimaryKey primaryKey;
	private String comment;
	private Map<String, Column> columns = CollectUtils.newHashMap();
	private Map<String, UniqueKey> uniqueKeys = CollectUtils.newHashMap();
	private Map<String, ForeignKey> foreignKeys = CollectUtils.newHashMap();
	private Map<String, Index> indexes = CollectUtils.newHashMap();

	public Table() {
		super();
	}

	public Table(String schema, String name) {
		super();
		this.schema = schema;
		this.name = name;
	}

	public Table(String name) {
		super();
		this.name = name;
	}

	public String[] getColumnNames() {
		List<Column> columnList = new ArrayList<Column>(columns.values());
		Collections.sort(columnList);
		List<String> columnNames = CollectUtils.newArrayList(columnList.size());
		for (Column column : columnList) {
			columnNames.add(column.getName());
		}
		return columnNames.toArray(new String[columnList.size()]);
	}

	public String identifier() {
		return qualify(schema, name);
	}

	public static String qualify(String schema, String name) {
		StringBuilder qualifiedName = new StringBuilder();
		if (StringUtils.isNotEmpty(schema)) {
			qualifiedName.append(schema).append('.');
		}
		return qualifiedName.append(name).toString();
	}

	public String genInsertSql() {
		String[] columnNames = getColumnNames();
		StringBuilder sb = new StringBuilder("insert into ");
		sb.append(name).append("(");
		for (int i = 0; i < columnNames.length; i++) {
			sb.append(columnNames[i]).append(',');
		}
		sb.setCharAt(sb.length() - 1, ')');
		sb.append(" values(");
		sb.append(StringUtils.repeat("?,", columnNames.length));
		sb.setCharAt(sb.length() - 1, ')');
		return sb.toString();
	}

	public UniqueKey getOrCreateUniqueKey(String keyName) {
		UniqueKey uk = uniqueKeys.get(keyName);
		if (uk == null) {
			uk = new UniqueKey(keyName);
			uk.setTable(this);
			uniqueKeys.put(keyName, uk);
		}
		return uk;
	}

	/**
	 * @param dialect
	 * @return
	 */
	public String sqlCreateString(Dialect dialect) {
		StringBuilder buf = new StringBuilder(dialect.getCreateTableString()).append(' ')
				.append(name).append(" (");
		Iterator<Column> iter = columns.values().iterator();
		while (iter.hasNext()) {
			Column col = iter.next();
			// FIXME reserved key words
			// if(col.getName().equals("RESOURCE")){
			// continue;
			// }
			buf.append(col.getName()).append(' ');
			buf.append(col.getSqlType(dialect));

			String defaultValue = col.getDefaultValue();
			if (defaultValue != null) {
				buf.append(" default ").append(defaultValue);
			}
			if (col.isNullable()) {
				buf.append(dialect.getNullColumnString());
			} else {
				buf.append(" not null");
			}
			boolean useUniqueConstraint = col.isUnique()
					&& (!col.isNullable() || dialect.supportsNullUnique());
			if (useUniqueConstraint) {
				if (dialect.supportsUnique()) {
					buf.append(" unique");
				} else {
					UniqueKey uk = getOrCreateUniqueKey(col.getName() + '_');
					uk.addColumn(col);
				}
			}

			if (col.hasCheckConstraint() && dialect.supportsColumnCheck()) {
				buf.append(" check (").append(col.getCheckConstraint()).append(")");
			}
			String columnComment = col.getComment();
			if (columnComment != null) {
				buf.append(dialect.getColumnComment(columnComment));
			}
			if (iter.hasNext()) {
				buf.append(", ");
			}
		}
		if (hasPrimaryKey()) {
			buf.append(", ").append(getPrimaryKey().sqlConstraintString());
		}
		buf.append(')');

		if (StringUtils.isNotBlank(comment)) {
			buf.append(dialect.getTableComment(comment));
		}
		return buf.toString();
	}

	private boolean hasPrimaryKey() {
		return null != primaryKey;
	}

	public String getName() {
		return name;
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

	public Map<String, Column> getColumns() {
		return columns;
	}

	public PrimaryKey getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(PrimaryKey primaryKey) {
		this.primaryKey = primaryKey;
		if (null != primaryKey) {
			primaryKey.setTable(this);
		}
	}

	public String toString() {
		return qualify(schema, name);
	}

	public Column getColumn(String columnName) {
		return (Column) columns.get(columnName.toLowerCase());
	}

	public ForeignKey getForeignKey(String keyName) {
		return foreignKeys.get(keyName.toLowerCase());
	}

	public void addForeignKey(ForeignKey key) {
		key.setTable(this);
		foreignKeys.put(key.getName().toLowerCase(), key);
	}

	public void addColumn(Column column) {
		columns.put(column.getName().toLowerCase(), column);
	}

	public void addIndex(Index index) {
		indexes.put(index.getName().toLowerCase(), index);
	}

	public Map<String, ForeignKey> getForeignKeys() {
		return foreignKeys;
	}

	public Map<String, Index> getIndexes() {
		return indexes;
	}

	public void setForeignKeys(Map<String, ForeignKey> foreignKeys) {
		this.foreignKeys = foreignKeys;
	}

	public Index getIndex(String indexName) {
		return indexes.get(indexName.toLowerCase());
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
