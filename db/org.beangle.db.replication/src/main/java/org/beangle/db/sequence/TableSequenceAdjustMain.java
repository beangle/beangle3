/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.sequence;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;
import org.beangle.db.jdbc.util.DataSourceUtil;
import org.beangle.db.sequence.impl.DefaultSequenceNamePattern;
import org.beangle.db.sequence.impl.OracleTableSequenceDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableSequenceAdjustMain {

	/**
	 * TableSequenceAdjustMain -update -drop
	 * 
	 * @param args
	 * @throws Exception
	 */
	public static void main(String args[]) throws Exception {
		Logger logger = LoggerFactory.getLogger(TableSequenceAdjustMain.class);
		boolean update = false;
		boolean remove = false;
		CommandLineParser parser = new PosixParser();
		Options options = new Options();
		options.addOption("update", false, "update sequence according to table ");
		options.addOption("drop", false, "remove sequence");
		CommandLine cmd = parser.parse(options, args);
		update = cmd.hasOption("update");
		remove = cmd.hasOption("drop");

		List<String> datasourceNames = DataSourceUtil.getDataSourceNames();
		String dialect = null;
		PrintStream ps = System.out;
		if (datasourceNames.size() < 1) {
			logger.info("without any database config");
			return;
		} else if (datasourceNames.size() > 1) {
			ps.println("select db from " + datasourceNames + ":");
			while (null == dialect || !datasourceNames.contains(dialect)) {
				if (null != dialect) {
					ps.println("incorrect! select db from " + datasourceNames + ":");
				}
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				dialect = br.readLine();
			}
		} else {
			dialect = (String) datasourceNames.iterator().next();
		}

		DataSource datasource = DataSourceUtil.getDataSource(dialect);
		OracleTableSequenceDao tableSequenceDao = new OracleTableSequenceDao();
		tableSequenceDao.setDataSource(datasource);
		tableSequenceDao.setRelation(new DefaultSequenceNamePattern());
		List<TableSequence> sequences = tableSequenceDao.getInconsistent();
		info(sequences);
		if (update) {
			adjust(tableSequenceDao, sequences);
		}
		if (remove) {
			drop(tableSequenceDao, sequences);
		}
	}

	public static void drop(TableSequenceDao tableSequenceDao, List<TableSequence> sequences) {
		PrintStream ps = System.out;
		if (!sequences.isEmpty()) {
			ps.println("start drop ...");
		}
		for (final TableSequence seq : sequences) {
			if (null == seq.getTableName()) {
				tableSequenceDao.drop(seq.getSeqName());
				ps.println("drop sequence " + seq.getSeqName());
			}
		}
	}

	public static void adjust(TableSequenceDao tableSequenceDao, List<TableSequence> sequences) {
		PrintStream ps = System.out;
		if (!sequences.isEmpty()) {
			ps.println("start adjust ...");
		}
		for (final TableSequence seq : sequences) {
			if (null != seq.getTableName()) {
				ps.println("adjust sequence " + seq.getSeqName() + " with lastnumber "
						+ tableSequenceDao.adjust(seq));
			}
		}
		ps.println("finish adjust");
	}

	public static void info(List<TableSequence> sequences) {
		PrintStream ps = System.out;
		if (sequences.isEmpty()) {
			ps.println("without any inconsistent  sequence");
		} else {
			ps.println("find inconsistent  sequence " + sequences.size());
			ps.println("sequence_name(lastnumber) table_name(max id)");
		}

		for (TableSequence seq : sequences) {
			ps.println(seq);
		}
	}
}
