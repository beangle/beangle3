/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.StopWatch;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.db.jdbc.meta.Table;
import org.beangle.db.replication.DataWrapper;
import org.beangle.db.replication.Replicator;
import org.beangle.db.replication.wrappers.DatabaseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataReplicator implements Replicator {

	private static final Logger logger = LoggerFactory.getLogger(DataReplicator.class);

	List<Table> tables = CollectUtils.newArrayList();
	DatabaseWrapper source;
	DatabaseWrapper target;

	public DataReplicator() {
		super();
	}

	public DataReplicator(DatabaseWrapper source, DatabaseWrapper target) {
		super();
		this.source = source;
		this.target = target;
	}

	protected void addTable(Table table) {
		tables.add(table);
	}

	public void addAll(Collection<? extends Table> newTables) {
		tables.addAll(newTables);
	}

	public boolean addTables(String... tables) {
		boolean success = true;
		for (int i = 0; i < tables.length; i++) {
			String newTable = tables[i];
			if (!StringUtils.contains(tables[i], '.') && null != source.getDatabase().getSchema()) {
				newTable = source.getDatabase().getSchema() + "." + newTable;
			}
			Table tm = source.getDatabase().getTable(newTable);
			if (null == tm) {
				logger.error("cannot find metadata for {}", newTable);
			} else {
				addTable(tm);
			}
			success &= (tm != null);
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

	private boolean reCreateTable(Table table) {
		if (target.drop(table)) {
			if (target.create(table)) {
				logger.info("Recreate table {}", table.getName());
				return true;
			} else {
				logger.error("Recreat table {} failure,try next table.", table.getName());
			}
		}
		return false;
	}

	public void start() {
		Collections.sort(tables);
		StopWatch watch = new StopWatch();
		watch.start();
		logger.info("Start data replication...");
		for (final Table table : tables) {
			try {
				if (!reCreateTable(table)) continue;
				int count = source.count(table);
				if (count == 0) {
					target.pushData(table, Collections.emptyList());
					logger.info("Replicate {}(0)", table);
				} else {
					int curr = 0;
					PageLimit limit = new PageLimit(0, 1000);
					while (curr < count) {
						limit.setPageNo(limit.getPageNo() + 1);
						List<Object> data = source.getData(table, limit);
						if (data.isEmpty()) {
							logger.error("Cannot fetch limit data in {} with page size {}",
									limit.getPageNo(), limit.getPageSize());
						}
						int successed = target.pushData(table, data);
						curr += data.size();
						if (successed == count) {
							logger.info("Replicate {}({})", table, successed);
						} else if (successed == data.size()) {
							logger.info("Replicate {}({}/{})", new Object[] { table, successed, count });
						} else {
							logger.warn("Replicate {}({}/{})", new Object[] { table, successed, data.size() });
						}
					}
				}
			} catch (Exception e) {
				logger.error("Replicate  " + table.identifier(), e);
			}
		}
		logger.info("End data replication,using {}", watch.getTime());
	}
}
