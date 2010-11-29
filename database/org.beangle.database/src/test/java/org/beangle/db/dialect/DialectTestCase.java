/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.dialect;

import java.util.Map;
import java.util.Set;

import org.beangle.db.meta.DatabaseMetadata;
import org.beangle.db.meta.SequenceMetadata;
import org.beangle.db.meta.TableMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DialectTestCase {
	protected Dialect dialect;
	protected DatabaseMetadata meta;

	protected static final Logger log = LoggerFactory.getLogger(DialectTestCase.class.getName());

	protected void listMetadata() {
		Map<String, TableMetadata> tables = meta.getTables();
		for (String name : tables.keySet()) {
			log.info("table found {}", name);
		}

		Set<SequenceMetadata> seqs = meta.getSequences();
		for (SequenceMetadata obj : seqs) {
			log.info("sequence found {}", obj);
		}
	}
}
