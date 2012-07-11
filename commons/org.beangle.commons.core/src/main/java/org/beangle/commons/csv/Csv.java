/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * csv document object.
 * 
 * @author chaostone
 * @version $Id: $
 */
public class Csv {

  private final CsvFormat format;

  private List<String[]> contents = new ArrayList<String[]>();

  /**
   * <p>
   * Constructor for Csv.
   * </p>
   */
  public Csv() {
    this.format = new CsvFormat.Builder().build();
  }

  /**
   * <p>
   * Constructor for Csv.
   * </p>
   * 
   * @param format a {@link org.beangle.commons.csv.CsvFormat} object.
   */
  public Csv(CsvFormat format) {
    this.format = format;
  }

  /**
   * <p>
   * Getter for the field <code>format</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.csv.CsvFormat} object.
   */
  public CsvFormat getFormat() {
    return format;
  }

  /**
   * <p>
   * Getter for the field <code>contents</code>.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<String[]> getContents() {
    return contents;
  }

}
