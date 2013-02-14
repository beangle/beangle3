/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.transfer.csv;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.beangle.commons.csv.CsvFormat;
import org.beangle.commons.csv.CsvWriter;
import org.beangle.commons.transfer.io.AbstractItemWriter;
import org.beangle.commons.transfer.io.TransferFormat;

/**
 * <p>
 * CsvItemWriter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class CsvItemWriter extends AbstractItemWriter {

  protected CsvWriter csvr;

  protected CsvFormat csvFormat;

  /**
   * <p>
   * Constructor for CsvItemWriter.
   * </p>
   */
  public CsvItemWriter() {
    super();
  }

  /**
   * <p>
   * Constructor for CsvItemWriter.
   * </p>
   * 
   * @param outputStream a {@link java.io.OutputStream} object.
   */
  public CsvItemWriter(OutputStream outputStream) {
    setOutputStream(outputStream);
  }

  /** {@inheritDoc} */
  public void write(Object obj) {
    if (null == csvr) {
      if (null == csvFormat) {
        this.csvr = new CsvWriter(new OutputStreamWriter(outputStream));
      } else {
        this.csvr = new CsvWriter(new OutputStreamWriter(outputStream), csvFormat);
      }
    }
    if (null == obj) return;
    try {
      if (obj.getClass().isArray()) {
        Object[] values = (Object[]) obj;
        String[] strValues = new String[values.length];
        for (int i = 0; i < values.length; i++) {
          strValues[i] = (null == values[i]) ? "" : values[i].toString();
        }
        csvr.write(strValues);
      } else {
        csvr.write(new String[] { obj.toString() });
      }
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /** {@inheritDoc} */
  public void writeTitle(String titleName, Object data) {
    write(data);
  }

  /**
   * <p>
   * close.
   * </p>
   */
  public void close() {
    try {
      csvr.close();
    } catch (Exception e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * <p>
   * getFormat.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public TransferFormat getFormat() {
    return TransferFormat.Csv;
  }

  /** {@inheritDoc} */
  public void setOutputStream(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  /**
   * <p>
   * Getter for the field <code>csvFormat</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.csv.CsvFormat} object.
   */
  public CsvFormat getCsvFormat() {
    return csvFormat;
  }

  /**
   * <p>
   * Setter for the field <code>csvFormat</code>.
   * </p>
   * 
   * @param csvFormat a {@link org.beangle.commons.csv.CsvFormat} object.
   */
  public void setCsvFormat(CsvFormat csvFormat) {
    this.csvFormat = csvFormat;
  }

}
