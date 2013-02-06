/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.io;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Simple IO Utility
 * 
 * @author chaostone
 * @since 3.1
 */
public class IOs {
  private static final int DefaultBufferSize = 1024 * 4;
  private static final int Eof = -1;

  /**
   * Copy bytes from a <code>InputStream</code> to an <code>OutputStream</code>.
   * 
   * @param input the <code>InputStream</code> to read from
   * @param output the <code>OutputStream</code> to write to
   * @return the number of bytes copied
   * @throws NullPointerException if the input or output is null
   * @throws IOException if an I/O error occurs
   * @since 3.1
   */
  public static long copy(InputStream input, OutputStream output) throws IOException {
    byte[] buffer = new byte[DefaultBufferSize];
    long count = 0;
    int n = 0;
    while (Eof != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
      count += n;
    }
    return count;
  }

  /**
   * Copy chars from a <code>Reader</code> to a <code>Writer</code>.
   * 
   * @param input the <code>Reader</code> to read from
   * @param output the <code>Writer</code> to write to
   * @return the number of characters copied
   * @throws NullPointerException if the input or output is null
   * @throws IOException if an I/O error occurs
   * @since 3.1
   */
  public static long copy(Reader input, Writer output) throws IOException {
    char[] buffer = new char[DefaultBufferSize];
    long count = 0;
    int n = 0;
    while (Eof != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
      count += n;
    }
    return count;
  }

  /**
   * Get the contents of a <code>Reader</code> as a list of Strings,
   * one entry per line.
   * <p>
   * 
   * @param input the <code>Reader</code> to read from, not null
   * @return the list of Strings, never null
   * @throws IOException if an I/O error occurs
   * @since 1.1
   */
  public static List<String> readLines(Reader input) throws IOException {
    BufferedReader reader = toBufferedReader(input);
    List<String> list = new ArrayList<String>();
    String line = reader.readLine();
    while (line != null) {
      list.add(line);
      line = reader.readLine();
    }
    return list;
  }

  public static List<String> readLines(InputStream input) throws IOException {
    return readLines(new InputStreamReader(input));
  }

  public static void close(Closeable closeable) {
    try {
      if (closeable != null) {
        closeable.close();
      }
    } catch (IOException ioe) {
      // ignore
    }
  }

  private static BufferedReader toBufferedReader(Reader reader) {
    return reader instanceof BufferedReader ? (BufferedReader) reader : new BufferedReader(reader);
  }
}
