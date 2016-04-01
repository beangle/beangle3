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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.beangle.commons.csv.internal.CsvParser;

/**
 * <p>
 * CsvReader class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class CsvReader {

  private boolean hasNext = true;

  private boolean linesSkiped;

  private int skipLines;

  private BufferedReader br;

  private CsvParser parser;

  /**
   * <p>
   * Constructor for CsvReader.
   * </p>
   * 
   * @param reader a {@link java.io.Reader} object.
   */
  public CsvReader(Reader reader) {
    this(reader, new CsvFormat.Builder().build());
  }

  /**
   * <p>
   * Constructor for CsvReader.
   * </p>
   * 
   * @param reader a {@link java.io.Reader} object.
   * @param format a {@link org.beangle.commons.csv.CsvFormat} object.
   */
  public CsvReader(Reader reader, CsvFormat format) {
    this.br = new BufferedReader(reader);
    this.parser = new CsvParser(format);
    this.skipLines = 0;
  }

  /**
   * Reads the next line from the file.
   * 
   * @return the next line from the file without trailing newline
   * @throws IOException
   *           if bad things happen during the read
   */
  private String getNextLine() {
    try {
      if (!this.linesSkiped) {
        for (int i = 0; i < skipLines; i++) {
          br.readLine();
        }
        this.linesSkiped = true;
      }
      String nextLine = br.readLine();
      if (nextLine == null) {
        hasNext = false;
      }
      return hasNext ? nextLine : null;
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  /**
   * <p>
   * readNext.
   * </p>
   * 
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] readNext() {
    String[] result = null;
    do {
      String nextLine = getNextLine();
      if (!hasNext) { return result; // should throw if still pending?
      }
      String[] r = parser.parseLineMulti(nextLine);
      if (r.length > 0) {
        if (result == null) {
          result = r;
        } else {
          String[] t = new String[result.length + r.length];
          System.arraycopy(result, 0, t, 0, result.length);
          System.arraycopy(r, 0, t, result.length, r.length);
          result = t;
        }
      }
    } while (parser.isPending());
    return result;
  }

}
