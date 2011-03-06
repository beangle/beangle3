/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUnderflowException;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.UnboundedFifoBuffer;
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
				newTable = Table.qualify(source.getDatabase().getSchema(), newTable);
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
		Collections.sort(tables);
		UnboundedFifoBuffer fifoBuffer = new UnboundedFifoBuffer();
		for (Table table : tables) {
			fifoBuffer.add(table);
		}
		Buffer tableBuffer = BufferUtils.synchronizedBuffer(fifoBuffer);
		StopWatch watch = new StopWatch();
		watch.start();
		logger.info("Start data replication...");
		List<Thread> tasks = CollectUtils.newArrayList();
		for (int i = 0; i < 9; i++) {
			Thread thread = new Thread(new ReplicatorTask(source, target, tableBuffer));
			tasks.add(thread);
			thread.start();
		}
		for (Thread task : tasks) {
			try {
				task.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		logger.info("End data replication,using {}", watch.getTime());
	}

	public static class ReplicatorTask implements Runnable {
		DatabaseWrapper source;
		DatabaseWrapper target;
		Buffer buffer;

		public void run() {
			try {
				while (!buffer.isEmpty()) {
					Table table = (Table) buffer.remove();
					replicate(table);
				}
			} catch (BufferUnderflowException e) {
				return;
			}
		}

		public ReplicatorTask(DatabaseWrapper source, DatabaseWrapper target, Buffer buffer) {
			super();
			this.source = source;
			this.target = target;
			this.buffer = buffer;
		}

		private boolean createOrReplaceTable(Table table) {
			if (target.drop(table)) {
				if (target.create(table)) {
					logger.info("Create table {}", table.getName());
					return true;
				} else {
					logger.error("Create table {} failure.", table.getName());
				}
			}
			return false;
		}

		public void replicate(Table table) {
			String tableName = table.identifier();
			// set new schema
			table.setSchema(target.getDatabase().getSchema());
			try {
				if (!createOrReplaceTable(table)) return;
				Table srcTable = source.getDatabase().getTable(tableName);
				int count = source.count(srcTable);
				if (count == 0) {
					target.pushData(table, Collections.emptyList());
					logger.info("Replicate {}(0)", table);
				} else {
					int curr = 0;
					PageLimit limit = new PageLimit(0, 1000);
					while (curr < count) {
						limit.setPageNo(limit.getPageNo() + 1);
						List<Object> data = source.getData(srcTable, limit);
						if (data.isEmpty()) {
							logger.error("Cannot fetch limit data in {} with page size {}",
									limit.getPageNo(), limit.getPageSize());
						}
						int successed = target.pushData(table, data);
						curr += data.size();
						if (successed == count) {
							logger.info("Replicate {}({})", table, successed);
						} else if (successed == data.size()) {
							logger.info("Replicate {}({}/{})", new Object[] { table, curr, count });
						} else {
							logger.warn("Replicate {}({}/{})", new Object[] { table, successed, data.size() });
						}
					}
				}
			} catch (Exception e) {
				logger.error("Replicate error " + table.identifier(), e);
			}

		}
	}
}
