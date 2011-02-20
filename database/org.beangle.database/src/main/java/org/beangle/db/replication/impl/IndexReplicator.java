/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication.impl;

import org.beangle.db.replication.DataWrapper;
import org.beangle.db.replication.Replicator;
import org.beangle.db.replication.wrappers.DatabaseWrapper;

public class IndexReplicator implements Replicator {
	
	DatabaseWrapper source;
	DatabaseWrapper target;

	public void reset() {
		
	}

	public void start() {
	}

	public void setSource(DataWrapper source) {
		this.source = (DatabaseWrapper) source;
	}

	public void setTarget(DataWrapper target) {
		this.target = (DatabaseWrapper) target;
	}

}
