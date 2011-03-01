/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.time.StopWatch;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.jdbc.meta.Constraint;
import org.beangle.db.jdbc.meta.ForeignKey;
import org.beangle.db.replication.DataWrapper;
import org.beangle.db.replication.Replicator;
import org.beangle.db.replication.wrappers.DatabaseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstraintReplicator implements Replicator {
	private static final Logger logger = LoggerFactory.getLogger(ConstraintReplicator.class);
	@SuppressWarnings("unused")
	private DatabaseWrapper source;
	private DatabaseWrapper target;

	private List<Constraint> contraints = CollectUtils.newArrayList();

	public ConstraintReplicator(DatabaseWrapper source, DatabaseWrapper target) {
		super();
		this.source = source;
		this.target = target;
	}

	public void addAll(Collection<? extends Constraint> newContraints) {
		contraints.addAll(newContraints);
	}

	public void reset() {

	}

	public void start() {
		Collections.sort(contraints);
		StopWatch watch = new StopWatch();
		watch.start();
		logger.info("Start constraint replication...");
		String targetSchema = target.getDatabase().getSchema();
		for (Constraint contraint : contraints) {
			if (contraint instanceof ForeignKey) {
				ForeignKey fk = (ForeignKey) contraint;
				String sql = fk.getAlterSql(target.getDialect(), targetSchema);
				try {
					target.execute(sql);
					logger.info("Apply constaint {}", fk.getName());
				} catch (Exception e) {
					logger.warn("Cannot execute {}", sql);
				}
			}
		}
		logger.info("End constraint replication,using {}", watch.getTime());
	}

	public void setSource(DataWrapper source) {
		this.source = (DatabaseWrapper) source;
	}

	public void setTarget(DataWrapper target) {
		this.target = (DatabaseWrapper) target;
	}
}
