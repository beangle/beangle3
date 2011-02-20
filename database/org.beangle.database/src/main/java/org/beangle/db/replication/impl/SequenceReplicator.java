/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.impl;

import java.util.Collection;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.meta.Sequence;
import org.beangle.db.replication.DataWrapper;
import org.beangle.db.replication.Replicator;
import org.beangle.db.replication.wrappers.DatabaseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SequenceReplicator implements Replicator {
	private static final Logger logger = LoggerFactory.getLogger(SequenceReplicator.class);
	DatabaseWrapper source;
	DatabaseWrapper target;

	List<Sequence> sequences = CollectUtils.newArrayList();

	public SequenceReplicator(DatabaseWrapper source, DatabaseWrapper target) {
		super();
		this.source = source;
		this.target = target;
	}

	public void setSource(DataWrapper source) {
		this.source = (DatabaseWrapper) source;
	}

	public void setTarget(DataWrapper target) {
		this.target = (DatabaseWrapper) target;
	}

	public void reset() {

	}

	public void start() {
		for (Sequence sequence : sequences) {
			String dropSql = sequence.sqlDropString(target.getDialect());
			if (null == dropSql) continue;
			try {
				target.execute(dropSql);
			} catch (Exception e) {
				logger.warn("Cannot execute {}", dropSql);
			}
			String createSql = sequence.sqlCreateString(target.getDialect());
			try {
				target.execute(createSql);
			} catch (Exception e) {
				logger.warn("Cannot execute {}", createSql);
			}
			logger.info("Success synchronize {}", sequence.getName());
		}
	}

	public void addAll(Collection<Sequence> newSequences) {
		sequences.addAll(newSequences);
	}

}
