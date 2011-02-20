/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.impl;

import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.meta.Constraint;
import org.beangle.db.meta.ForeignKey;
import org.beangle.db.replication.DataWrapper;
import org.beangle.db.replication.Replicator;
import org.beangle.db.replication.wrappers.DatabaseWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConstraintReplicator implements Replicator {
	private static final Logger logger = LoggerFactory.getLogger(ConstraintReplicator.class);
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
		for (Constraint contraint : contraints) {
			if (contraint instanceof ForeignKey) {
				ForeignKey fk = (ForeignKey) contraint;
				String sql = fk.sqlConstraintString(target.getDialect());
				String sourceSchema = source.getDatabase().getSchema();
				String targetSchema = target.getDatabase().getSchema();
				sql = StringUtils.replace(sql, sourceSchema + ".", targetSchema + ".");
				try {
					target.execute(sql);
				} catch (Exception e) {
					logger.warn("Cannot execute {}", sql);
				}
				logger.info("Success apply constaint {}", fk.getName());
			}
		}
	}

	public void setSource(DataWrapper source) {
		this.source = (DatabaseWrapper) source;
	}

	public void setTarget(DataWrapper target) {
		this.target = (DatabaseWrapper) target;
	}
}
