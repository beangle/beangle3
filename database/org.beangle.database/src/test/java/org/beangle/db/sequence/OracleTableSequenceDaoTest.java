/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.sequence;

import java.util.List;

import javax.sql.DataSource;

import org.beangle.db.sequence.impl.DefaultSequenceNamePattern;
import org.beangle.db.sequence.impl.OracleTableSequenceDao;
import org.beangle.db.util.DataSourceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;

public class OracleTableSequenceDaoTest {
	final Logger logger = LoggerFactory.getLogger(OracleTableSequenceDaoTest.class);

	OracleTableSequenceDao tableSequenceDao;

	protected String getDialect() {
		return "oracle";
	}

	@BeforeClass
	public void setUp() throws Exception {
		DataSource datasource = DataSourceUtil.getDataSource("oracle");
		if (null != datasource) {
			tableSequenceDao = new OracleTableSequenceDao();
			tableSequenceDao.setDataSource(datasource);
			tableSequenceDao.setRelation(new DefaultSequenceNamePattern());
		}
	}

	public void testGetSequences() {
		if (null != tableSequenceDao) {
			List<?> sequences = tableSequenceDao.getAllNames();
			logger.info("find sequence {}", new Integer(sequences.size()));
		}
	}

	public void testGetNoneReferenced() {
		if (null != tableSequenceDao) {
			List<?> sequences = tableSequenceDao.getNoneReferenced();
			logger.info("find none referenced sequence {}", new Integer(sequences.size()));
			logger.info("they are {}", sequences);
		}
	}

	public void testGetInconsistent() {
		if (null != tableSequenceDao) {
			List<TableSequence> sequences = tableSequenceDao.getInconsistent();
			logger.info("find inconsistent  sequence {}", new Integer(sequences.size()));
			for (TableSequence seq : sequences) {
				logger.info("{}", seq);
			}
		}
	}

	public void testAdjust() {
		if (null != tableSequenceDao) {
			List<TableSequence> sequences = tableSequenceDao.getInconsistent();
			logger.info("addjust sequence {}", new Integer(sequences.size()));
			for (TableSequence seq : sequences) {
				if (null != seq.getTableName()) {
					tableSequenceDao.adjust(seq);
				}
			}
		}
	}

	public void testDrop() {
		if (null != tableSequenceDao) {
			List<String> sequences = tableSequenceDao.getNoneReferenced();
			logger.info("drop sequence {}", new Integer(sequences.size()));
			for (String seq : sequences) {
				tableSequenceDao.drop(seq);
			}
		}
	}
}
