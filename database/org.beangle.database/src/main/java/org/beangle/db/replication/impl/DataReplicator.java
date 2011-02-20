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
import org.beangle.db.meta.Table;
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

	public void start() {
		StopWatch watch = new StopWatch();
		watch.start();
		logger.info("Start data replication.");
		for (final Table table : tables) {
			try {
				if (target.drop(table)) {
					logger.info("drop table {}", table.getName());
				}
				int count = source.count(table);
				if (count == 0) {
					target.pushData(table, Collections.emptyList());
					logger.info("replicate {} data {}", table, 0);
				} else {
					int curr = 0;
					PageLimit limit = new PageLimit(0, 5000);
					while (curr < count) {
						limit.setPageNo(limit.getPageNo() + 1);
						List<Object> data = source.getData(table, limit);
						int successed = target.pushData(table, data);
						curr += data.size();
						if (successed == data.size()) {
							logger.info("replicate {} data {}", table, successed);
						} else {
							logger.info("replicate {} data {} of {}",
									new Object[] { table, successed, data.size() });
						}
					}
				}
			} catch (Exception e) {
				logger.error("replicate  " + table.identifier(), e);
			}
		}
		logger.info("End data replication,using {}", watch.getTime());
	}
}
