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
package org.beangle.commons.csv;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * csv format definition
 * 
 * @author chaostone
 * @version $Id: $
 */
public class CsvFormat {

  private final char delimiter;

  private final Set<Character> separators;

  private final char escape;

  private final boolean strictQuotes = false;

  private CsvFormat(Set<Character> separators, char delimiter) {
    this.separators = Collections.unmodifiableSet(separators);
    this.delimiter = delimiter;
    this.escape = CsvConstants.ESCAPE;
  }

  private CsvFormat(Set<Character> separators, char delimiter, char escape) {
    this.separators = Collections.unmodifiableSet(separators);
    this.delimiter = delimiter;
    this.escape = escape;
  }

  /**
   * <p>
   * isStrictQuotes.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isStrictQuotes() {
    return strictQuotes;
  }

  /**
   * <p>
   * isSeparator.
   * </p>
   * 
   * @param a a char.
   * @return a boolean.
   */
  public boolean isSeparator(char a) {
    return separators.contains(a);
  }

  /**
   * <p>
   * isDelimiter.
   * </p>
   * 
   * @param a a char.
   * @return a boolean.
   */
  public boolean isDelimiter(char a) {
    return a == delimiter;
  }

  /**
   * <p>
   * isEscape.
   * </p>
   * 
   * @param a a char.
   * @return a boolean.
   */
  public boolean isEscape(char a) {
    return a == escape;
  }

  /**
   * <p>
   * Getter for the field <code>separators</code>.
   * </p>
   * 
   * @return a {@link java.util.Set} object.
   */
  public Set<Character> getSeparators() {
    return separators;
  }

  /**
   * <p>
   * Getter for the field <code>delimiter</code>.
   * </p>
   * 
   * @return a char.
   */
  public char getDelimiter() {
    return delimiter;
  }

  /**
   * <p>
   * Getter for the field <code>escape</code>.
   * </p>
   * 
   * @return a char.
   */
  public char getEscape() {
    return escape;
  }

  /**
   * <p>
   * defaultSeparator.
   * </p>
   * 
   * @return a char.
   */
  public char defaultSeparator() {
    if (separators.isEmpty()) {
      return CsvConstants.COMMA;
    } else {
      return separators.iterator().next();
    }
  }

  public static class Builder {
    char delimiter = CsvConstants.QUOTE;

    Set<Character> separators = new HashSet<Character>();

    char escape = CsvConstants.ESCAPE;

    public Builder separator(char comma) {
      separators.add(comma);
      return this;
    }

    public Builder escape(char escape) {
      this.escape = escape;
      return this;
    }

    public Builder delimiter(char delimiter) {
      this.delimiter = delimiter;
      return this;
    }

    public CsvFormat build() {
      if (separators.isEmpty()) {
        separators.add(CsvConstants.COMMA);
      }
      return new CsvFormat(separators, delimiter, escape);
    }
  }

}
