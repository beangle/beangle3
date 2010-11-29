/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.db.meta.TableMetadata;
import org.beangle.db.replication.DataWrapper;
import org.beangle.db.replication.Replicator;
import org.beangle.db.replication.wrappers.DatabaseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseReplicator implements Replicator {

	private static final Logger logger = LoggerFactory.getLogger(DatabaseReplicator.class);

	List<TableMetadata> tables = CollectUtils.newArrayList();
	DatabaseWrapper source;
	DatabaseWrapper target;

	public DatabaseReplicator() {
		super();
	}

	public DatabaseReplicator(DatabaseWrapper source, DatabaseWrapper target) {
		super();
		this.source = source;
		this.target = target;
	}

	public boolean addTable(String table) {
		String newTable = table.toUpperCase();
		if (!StringUtils.contains(table, '.') && null != source.getSchema()) {
			newTable = source.getSchema() + "." + newTable;
		}
		TableMetadata tm = source.getMetadata().getTableMetadata(newTable);
		if (null == tm) {
			logger.error("cannot find metadata for {}", newTable);
		} else {
			tables.add(tm);
		}
		return tm != null;
	}

	public boolean addTables(Collection<String> tables) {
		boolean success = true;
		for (String tableName : tables) {
			success &= addTable(tableName);
		}
		return success;
	}

	public boolean addTables(String[] tables) {
		boolean success = true;
		for (int i = 0; i < tables.length; i++) {
			success &= addTable(tables[i]);
		}
		return success;
	}

	public void reset() {

	}

	public void setSource(DataWrapper source) {
		this.source = (DatabaseWrapper) source;
	}

	public void setTarget(DataWrapper target) {
		this.target = (DatabaseWrapper) target;
	}

	public void start() {
		for (final TableMetadata table : tables) {
			try {
				String sourceSchema = source.getSchema();
				int count = source.count(table);
				if (count == 0) {
					table.setSchema(target.getSchema());
					target.pushData(table, Collections.emptyList());
					logger.info("replicate {} data {}", table, 0);
				} else {
					int curr = 0;
					PageLimit limit = new PageLimit(0, 5000);
					while (curr < count) {
						table.setSchema(sourceSchema);
						limit.setPageNo(limit.getPageNo() + 1);
						List<Object> data = source.getData(table, limit);
						table.setSchema(target.getSchema());
						int successed = target.pushData(table, data);
						curr += data.size();
						if (successed == data.size()) {
							logger.info("replicate {} data {}", table, successed);
						} else {
							logger.info("replicate {} data {} of {}", new Object[] { table,
									successed, data.size() });
						}
					}
				}
				table.setSchema(sourceSchema);
			} catch (Exception e) {
				logger.error("replicate  " + table.identifier(), e);
			}
		}
	}

}
