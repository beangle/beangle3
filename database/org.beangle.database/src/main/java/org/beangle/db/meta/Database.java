/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.dialect.Dialect;

/**
 * JDBC database metadata
 * 
 * @author chaostone
 */
public class Database {

	// identifer(schema,name)->table
	@SuppressWarnings("unchecked")
	private final Map<String, Table> tables = new CaseInsensitiveMap();
	private Set<Sequence> sequences;
	protected String catalog;
	protected String schema;
	private Dialect dialect;
	private DatabaseMetaData meta;

	public Database(DatabaseMetaData meta, Dialect dialect, String catalog, String schema) {
		super();
		this.meta = meta;
		this.dialect = dialect;
		this.catalog = catalog;
		this.schema = schema;
	}

	public void loadTables(boolean extras) {
		MetadataLoader loader = new MetadataLoader(dialect, meta);
		Set<Table> loadTables = loader.loadTables(catalog, schema, extras);
		for (Table table : loadTables) {
			tables.put(table.identifier(), table);
		}
	}

	public void loadSequences() {
		MetadataLoader loader = new MetadataLoader(dialect, meta);
		try {
			sequences = CollectUtils.newHashSet();
			sequences.addAll(loader.loadSequences(meta.getConnection(), schema));
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public Table getTable(String name) {
		return tables.get(name);
	}

	public String toString() {
		return "Database" + tables.keySet().toString() + sequences.toString();
	}

	public Map<String, Table> getTables() {
		return tables;
	}

	public Set<Sequence> getSequences() {
		if (null == sequences) {
			loadSequences();
		}
		return sequences;
	}

	public Dialect getDialect() {
		return dialect;
	}

}
