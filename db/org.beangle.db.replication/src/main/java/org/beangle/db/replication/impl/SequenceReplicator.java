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
import org.beangle.db.jdbc.meta.Sequence;
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

	private boolean reCreate(Sequence sequence) {
		if (target.drop(sequence)) {
			if (target.create(sequence)) {
				logger.info("Recreate sequence {}", sequence.getName());
				return true;
			} else {
				logger.error("Recreate sequence {} failure.", sequence.getName());
			}
		}
		return false;
	}

	public void start() {
		Collections.sort(sequences);
		StopWatch watch = new StopWatch();
		watch.start();
		logger.info("Start sequence replication...");
		for (Sequence sequence : sequences) {
			reCreate(sequence);
		}
		logger.info("End sequence replication,using {}", watch.getTime());
	}

	public void addAll(Collection<Sequence> newSequences) {
		sequences.addAll(newSequences);
	}

}
