/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
