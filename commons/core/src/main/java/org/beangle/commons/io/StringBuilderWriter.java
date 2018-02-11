/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.io;

import java.io.Serializable;
import java.io.Writer;

/**
 * {@link Writer} implementation that outputs to a {@link StringBuilder}.
 * <p>
 * <strong>NOTE:</strong> This implementation, as an alternative to
 * <code>java.io.StringWriter</code>, provides an <i>un-synchronized</i> (i.e. for use in a single
 * thread) implementation for better performance. For safe usage with multiple {@link Thread}s then
 * <code>java.io.StringWriter</code> should be used.
 *
 * @author chaostone
 * @since 3.1
 */
public class StringBuilderWriter extends Writer implements Serializable {

  private static final long serialVersionUID = 1L;

  private final StringBuilder builder;

  /**
   * Construct a new {@link StringBuilder} instance with default capacity.
   */
  public StringBuilderWriter() {
    this.builder = new StringBuilder();
  }

  /**
   * Construct a new {@link StringBuilder} instance with the specified capacity.
   *
   * @param capacity The initial capacity of the underlying {@link StringBuilder}
   */
  public StringBuilderWriter(int capacity) {
    this.builder = new StringBuilder(capacity);
  }

  /**
   * Construct a new instance with the specified {@link StringBuilder}.
   *
   * @param builder The String builder
   */
  public StringBuilderWriter(StringBuilder builder) {
    this.builder = builder != null ? builder : new StringBuilder();
  }

  /**
   * Append a single character to this Writer.
   */
  @Override
  public Writer append(char value) {
    builder.append(value);
    return this;
  }

  /**
   * Append a character sequence to this Writer.
   */
  @Override
  public Writer append(CharSequence value) {
    builder.append(value);
    return this;
  }

  /**
   * Append a portion of a character sequence to the {@link StringBuilder}.
   */
  @Override
  public Writer append(CharSequence value, int start, int end) {
    builder.append(value, start, end);
    return this;
  }

  /**
   * Closing this writer has no effect.
   */
  @Override
  public void close() {
  }

  /**
   * Flushing this writer has no effect.
   */
  @Override
  public void flush() {
  }

  /**
   * Write a String to the {@link StringBuilder}.
   *
   * @param value The value to write
   */
  @Override
  public void write(String value) {
    if (value != null) builder.append(value);
  }

  /**
   * Write a portion of a character array to the {@link StringBuilder}.
   */
  @Override
  public void write(char[] value, int offset, int length) {
    if (value != null) builder.append(value, offset, length);
  }

  /**
   * Return the underlying builder.
   */
  public StringBuilder getBuilder() {
    return builder;
  }

  /**
   * Returns {@link StringBuilder#toString()}.
   */
  @Override
  public String toString() {
    return builder.toString();
  }
}
