/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import java.sql.DatabaseMetaData;
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

	@SuppressWarnings("unchecked")
	// identifer,table
	private final Map<String, Table> tables = new CaseInsensitiveMap();
	private final Set<Sequence> sequences = CollectUtils.newHashSet();
	protected String catalog;
	protected String schema;
	private Dialect dialect;

	public Database(Dialect dialect, String catalog, String schema) {
		super();
		this.dialect = dialect;
		this.catalog = catalog;
		this.schema = schema;
	}

	public void loadTables(DatabaseMetaData meta, boolean extras) {
		MetadataLoader loader = new MetadataLoader(dialect, meta);
		Set<Table> loadTables = loader.loadTables(catalog, schema, extras);
		for (Table table : loadTables) {
			tables.put(table.identifier(), table);
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
		return sequences;
	}

	public Dialect getDialect() {
		return dialect;
	}

}
