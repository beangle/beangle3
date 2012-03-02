/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.transfer.csv;

import java.io.IOException;
import java.io.LineNumberReader;

import org.apache.commons.lang.StringUtils;
import org.beangle.transfer.io.ItemReader;
import org.beangle.transfer.io.TransferFormats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvItemReader implements ItemReader {
	/**
	 * Commons Logging instance.
	 */
	protected static final Logger logger = LoggerFactory.getLogger(CsvItemReader.class);

	LineNumberReader reader;
	/** 标题所在行 */
	private int headIndex = 0;

	/** 数据起始行 */
	private int dataIndex = 1;

	private int indexInCsv = 1;

	public CsvItemReader(LineNumberReader reader) {
		this.reader = reader;
	}

	public String[] readDescription() {
		return null;
	}

	public String[] readTitle() {
		try {
			reader.readLine();
			return StringUtils.split(reader.readLine(), ",");
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	private void preRead() {
		while (indexInCsv < dataIndex) {
			try {
				reader.readLine();
			} catch (IOException e1) {
				logger.error("read csv", e1);
			}
			indexInCsv++;
		}
	}

	public Object read() {
		preRead();
		String curData = null;
		try {
			curData = reader.readLine();
		} catch (IOException e1) {
			logger.error(curData, e1);
		}
		indexInCsv++;
		if (null == curData) {
			return null;
		} else {
			return StringUtils.split(curData, ",");
		}
	}

	public int getHeadIndex() {
		return headIndex;
	}

	public void setHeadIndex(int headIndex) {
		if (this.dataIndex == this.headIndex + 1) {
			setDataIndex(headIndex + 1);
		}
		this.headIndex = headIndex;
	}

	public int getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(int dataIndex) {
		if (this.dataIndex == this.indexInCsv) {
			this.dataIndex = dataIndex;
			this.indexInCsv = dataIndex;
		}
	}

	public String getFormat() {
		return TransferFormats.CSV;
	}
}
