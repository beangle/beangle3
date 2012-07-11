/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.csv;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.beangle.commons.csv.CsvFormat;
import org.beangle.commons.csv.CsvWriter;
import org.beangle.commons.transfer.io.AbstractItemWriter;
import org.beangle.commons.transfer.io.TransferFormats;

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
  public String getFormat() {
    return TransferFormats.CSV;
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
