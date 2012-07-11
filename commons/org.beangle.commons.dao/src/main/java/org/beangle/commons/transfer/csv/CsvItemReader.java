/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.csv;

import java.io.IOException;
import java.io.LineNumberReader;

import org.beangle.commons.lang.Strings;
import org.beangle.commons.transfer.io.ItemReader;
import org.beangle.commons.transfer.io.TransferFormats;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * CsvItemReader class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
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

  /**
   * <p>
   * Constructor for CsvItemReader.
   * </p>
   * 
   * @param reader a {@link java.io.LineNumberReader} object.
   */
  public CsvItemReader(LineNumberReader reader) {
    this.reader = reader;
  }

  /**
   * <p>
   * readDescription.
   * </p>
   * 
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] readDescription() {
    return null;
  }

  /**
   * <p>
   * readTitle.
   * </p>
   * 
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] readTitle() {
    try {
      reader.readLine();
      return Strings.split(reader.readLine(), ",");
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

  /**
   * <p>
   * read.
   * </p>
   * 
   * @return a {@link java.lang.Object} object.
   */
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
      return Strings.split(curData, ",");
    }
  }

  /**
   * <p>
   * Getter for the field <code>headIndex</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getHeadIndex() {
    return headIndex;
  }

  /** {@inheritDoc} */
  public void setHeadIndex(int headIndex) {
    if (this.dataIndex == this.headIndex + 1) {
      setDataIndex(headIndex + 1);
    }
    this.headIndex = headIndex;
  }

  /**
   * <p>
   * Getter for the field <code>dataIndex</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getDataIndex() {
    return dataIndex;
  }

  /** {@inheritDoc} */
  public void setDataIndex(int dataIndex) {
    if (this.dataIndex == this.indexInCsv) {
      this.dataIndex = dataIndex;
      this.indexInCsv = dataIndex;
    }
  }

  /**
   * <p>
   * getFormat.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getFormat() {
    return TransferFormats.CSV;
  }
}
