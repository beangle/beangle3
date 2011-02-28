/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.sequence.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.CaseInsensitiveMap;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.sequence.SequenceNamePattern;
import org.beangle.db.sequence.TableSequence;
import org.beangle.db.sequence.TableSequenceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class OracleTableSequenceDao extends JdbcTemplate implements TableSequenceDao {

	private final static Logger logger = LoggerFactory.getLogger(OracleTableSequenceDao.class);

	private SequenceNamePattern relation;

	public boolean drop(String sequence_name) {
		String sql = "drop sequence " + sequence_name;
		execute(sql);
		return true;
	}

	public List<TableSequence> getInconsistent() {
		List<TableSequence> err_seqs = CollectUtils.newArrayList();
		List<String> list = getAllNames();
		for (final String seqName : list) {
			String tempSeqSql = "select last_number from user_sequences seqs where seqs.sequence_name='"
					+ seqName + "'";
			long seqLast_number = queryForLong(tempSeqSql);
			String tableName = relation.getTableName(seqName);
			boolean exists = queryForInt("select count(*) from user_tables tbl where tbl.table_name='"
					+ tableName + "'") > 0;
			if (exists) {
				long dataCount = queryForLong("select count(*) from " + tableName);
				if (dataCount > 0) {
					long tableLMaxId = -2;
					try {
						tableLMaxId = queryForLong("select max(id) from  " + tableName);
					} catch (Exception e) {
						logger.warn("cannot find table {} ", tableName);
					}
					if (seqLast_number < tableLMaxId) {
						TableSequence seq = new TableSequence();
						seq.setSeqName(seqName);
						seq.setTableName(tableName);
						seq.setLastNumber(seqLast_number);
						seq.setMaxId(tableLMaxId);
						err_seqs.add(seq);
					}
				}
			} else {
				TableSequence seq = new TableSequence();
				seq.setSeqName(seqName);
				seq.setLastNumber(seqLast_number);
				err_seqs.add(seq);
			}
		}
		return err_seqs;
	}

	/**
	 * @param sequence
	 * @param table
	 * @param column
	 */
	public long adjust(TableSequence tableSequence) {
		String sequence = tableSequence.getSeqName();
		String getSql = "select " + sequence + ".nextval from dual";
		long current = queryForLong(getSql);
		String countSql = "select max(" + tableSequence.getIdColumnName() + ") maxid from "
				+ tableSequence.getTableName();
		List<Map<String, Object>> rs = queryForList(countSql);
		long max = 0;
		if (!rs.isEmpty()) {
			@SuppressWarnings("unchecked")
			Map<String, Number> maxNum = new CaseInsensitiveMap(rs.get(0));
			max = maxNum.get("maxid").longValue();
		}
		long repaired = 0;
		String updateIncrease = null;
		if (max > current) {
			if (max - current > 1) {
				updateIncrease = "ALTER SEQUENCE " + sequence + " INCREMENT BY   " + (max - current - 1);
				execute(updateIncrease);
				queryForLong(getSql);
				//
				updateIncrease = "ALTER SEQUENCE " + sequence + " INCREMENT BY  1";
				execute(updateIncrease);
			}
			repaired = queryForLong(getSql);
		} else {
			if (1 == current) return new Long(1);
			updateIncrease = "ALTER SEQUENCE " + sequence + " INCREMENT BY  -1";
			execute(updateIncrease);
			repaired = queryForLong(getSql);
			updateIncrease = "ALTER SEQUENCE " + sequence + " INCREMENT BY  1";
			execute(updateIncrease);
		}
		return repaired;
	}

	public List<String> getAllNames() {
		String sql = "select sequence_name from user_sequences order by sequence_name";
		List<Map<String, Object>> seqs = queryForList(sql);
		List<String> sequenceNames = CollectUtils.newArrayList(seqs.size());
		for (Map<String, Object> seq : seqs) {
			String name = (String) new CaseInsensitiveMap(seq).get("sequence_name");
			if (null == name) continue;
			sequenceNames.add(name);
		}
		return sequenceNames;
	}

	public List<String> getNoneReferenced() {
		List<String> err_seqs = CollectUtils.newArrayList();
		List<String> list = getAllNames();
		for (final String seqName : list) {
			String tableName = relation.getTableName(seqName);
			boolean exists = queryForInt("select count(*) from user_tables tbl where tbl.table_name='"
					+ tableName + "'") > 0;
			if (!exists) {
				err_seqs.add(seqName);
			}
		}
		return err_seqs;
	}

	public void setRelation(SequenceNamePattern relation) {
		this.relation = relation;
	}

}
