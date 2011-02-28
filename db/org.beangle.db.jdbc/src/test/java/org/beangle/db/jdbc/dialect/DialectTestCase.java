/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.jdbc.dialect;

import java.util.Map;
import java.util.Set;

import org.beangle.db.jdbc.meta.Database;
import org.beangle.db.jdbc.meta.Sequence;
import org.beangle.db.jdbc.meta.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DialectTestCase {
	protected Dialect dialect;
	protected Database database;

	protected static final Logger logger = LoggerFactory.getLogger(DialectTestCase.class.getName());

	protected void listTableAndSequences() {
		Map<String, Table> tables = database.getTables();
		for (String name : tables.keySet()) {
			logger.info("table {}", name);
		}

		Set<Sequence> seqs = database.getSequences();
		for (Sequence obj : seqs) {
			logger.info("sequence {}", obj);
		}
	}
}
