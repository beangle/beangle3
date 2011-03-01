/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.meta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.CompareToBuilder;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.jdbc.dialect.Dialect;
import org.beangle.db.jdbc.grammar.TableGrammar;

/**
 * JDBC table metadata
 * 
 * @author chaostone
 */
public class Table implements Comparable<Table>, Cloneable {
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

	public String identifier(String givenSchema) {
		if (StringUtils.isEmpty(givenSchema)) return name;
		else return qualify(givenSchema, name);
	}

	public static String qualify(String schema, String name) {
		StringBuilder qualifiedName = new StringBuilder();
		if (StringUtils.isNotEmpty(schema)) {
			qualifiedName.append(schema).append('.');
		}
		return qualifiedName.append(name).toString();
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

	public String getInsertSql() {
		return getInsertSql(schema);
	}

	public String getInsertSql(String newSchema) {
		String[] columnNames = getColumnNames();
		StringBuilder sb = new StringBuilder("insert into ");
		sb.append(identifier(newSchema)).append("(");
		for (int i = 0; i < columnNames.length; i++) {
			sb.append(columnNames[i]).append(',');
		}
		sb.setCharAt(sb.length() - 1, ')');
		sb.append(" values(");
		sb.append(StringUtils.repeat("?,", columnNames.length));
		sb.setCharAt(sb.length() - 1, ')');
		return sb.toString();
	}

	public String getCreateSql(Dialect dialect) {
		return getCreateSql(dialect, schema);
	}

	/**
	 * @param dialect
	 * @return
	 */
	public String getCreateSql(Dialect dialect, String newSchema) {
		TableGrammar grammar = dialect.getTableGrammar();
		StringBuilder buf = new StringBuilder(grammar.getCreateString()).append(' ')
				.append(identifier(newSchema)).append(" (");
		Iterator<Column> iter = columns.values().iterator();
		while (iter.hasNext()) {
			Column col = iter.next();
			buf.append(col.getName()).append(' ');
			buf.append(col.getSqlType(dialect));

			String defaultValue = col.getDefaultValue();
			if (defaultValue != null) {
				buf.append(" default ").append(defaultValue);
			}
			if (col.isNullable()) {
				buf.append(grammar.getNullColumnString());
			} else {
				buf.append(" not null");
			}
			boolean useUniqueConstraint = col.isUnique()
					&& (!col.isNullable() || grammar.isSupportsNullUnique());
			if (useUniqueConstraint) {
				if (grammar.isSupportsUnique()) {
					buf.append(" unique");
				} else {
					UniqueKey uk = getOrCreateUniqueKey(col.getName() + '_');
					uk.addColumn(col);
				}
			}

			if (col.hasCheckConstraint() && grammar.isSupportsColumnCheck()) {
				buf.append(" check (").append(col.getCheckConstraint()).append(")");
			}
			String columnComment = col.getComment();
			if (columnComment != null) {
				buf.append(grammar.getColumnComment(columnComment));
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
			buf.append(grammar.getComment(comment));
		}
		return buf.toString();
	}

	public String getQuerySql() {
		return getQuerySql(schema);
	}

	public String getQuerySql(String newSchema) {
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		String[] columnNames = getColumnNames();
		for (String columnName : columnNames) {
			sb.append(columnName).append(',');
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append(" from ").append(identifier(newSchema));
		return sb.toString();
	}

	public Table clone() {
		Table tb = new Table(schema, name);
		tb.setComment(comment);
		for (Column col : columns.values())
			tb.addColumn(col.clone());
		if (null != primaryKey) tb.setPrimaryKey(primaryKey.clone());

		for (ForeignKey fk : foreignKeys.values())
			tb.addForeignKey(fk.clone());

		for (UniqueKey uk : uniqueKeys.values())
			tb.addUniqueKey(uk.clone());

		for (Index idx : indexes.values())
			tb.addIndex(idx.clone());
		return tb;
	}

	public void lowerCase() {
		this.schema = StringUtils.lowerCase(schema);
		this.name = StringUtils.lowerCase(name);
		Map<String, Column> newColumns = CollectUtils.newHashMap();
		for (Column col : columns.values()) {
			col.lowerCase();
			newColumns.put(col.getName(), col);
		}
		columns = newColumns;

		if (null != primaryKey) primaryKey.lowerCase();

		Map<String, ForeignKey> newKeys = CollectUtils.newHashMap();
		for (ForeignKey fk : foreignKeys.values()) {
			fk.lowerCase();
			newKeys.put(fk.getName(), fk);
		}
		foreignKeys = newKeys;
		Map<String, UniqueKey> newUniqueKeys = CollectUtils.newHashMap();
		for (UniqueKey uk : uniqueKeys.values()) {
			uk.lowerCase();
			newUniqueKeys.put(uk.getName(), uk);
		}
		uniqueKeys = newUniqueKeys;

		Map<String, Index> newIndexes = CollectUtils.newHashMap();
		for (Index idx : indexes.values()) {
			idx.lowerCase();
			newIndexes.put(idx.getName(), idx);
		}
		indexes = newIndexes;
	}

	public int compareTo(Table o) {
		return new CompareToBuilder().append(getSchema(), o.getSchema()).append(getName(), o.getName())
				.toComparison();
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
		return (Column) columns.get(columnName);
	}

	public ForeignKey getForeignKey(String keyName) {
		return foreignKeys.get(keyName);
	}

	public void addForeignKey(ForeignKey key) {
		key.setTable(this);
		foreignKeys.put(key.getName(), key);
	}

	public void addUniqueKey(UniqueKey key) {
		key.setTable(this);
		this.uniqueKeys.put(key.getName(), key);
	}

	public void addColumn(Column column) {
		columns.put(column.getName(), column);
	}

	public void addIndex(Index index) {
		indexes.put(index.getName(), index);
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
		return indexes.get(indexName);
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
