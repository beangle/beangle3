/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.meta;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;

/**
 * JDBC index metadata
 * 
 * @author chaostone
 */
public class IndexMetadata {
	private String name;
	private List<ColumnMetadata> columns = CollectUtils.newArrayList();

	public IndexMetadata() {
		super();
	}

	public IndexMetadata(ResultSet rs) throws SQLException {
		name = rs.getString("INDEX_NAME");
	}

	public String getName() {
		return name;
	}

	void addColumn(ColumnMetadata column) {
		if (column != null) columns.add(column);
	}

	public ColumnMetadata[] getColumns() {
		return columns.toArray(new ColumnMetadata[columns.size()]);
	}

	public String toString() {
		return "IndexMatadata(" + name + ')';
	}
}
