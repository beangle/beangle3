/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.impl;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.replication.DataWrapper;
import org.beangle.db.replication.Replicator;

public class CompositeReplicator implements Replicator {

	List<Replicator> replicators = CollectUtils.newArrayList();

	public CompositeReplicator(Replicator... replicators) {
		super();
		this.replicators = CollectUtils.newArrayList(replicators);
	}

	public void setTarget(DataWrapper source) {
		for (Replicator replicator : replicators) {
			replicator.setTarget(source);
		}
	}

	public void setSource(DataWrapper source) {
		for (Replicator replicator : replicators) {
			replicator.setSource(source);
		}
	}

	public void reset() {
		for (Replicator replicator : replicators) {
			replicator.reset();
		}
	}

	public void start() {
		for (Replicator replicator : replicators) {
			replicator.start();
		}
	}

}
