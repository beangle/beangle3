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
package org.beangle.commons.csv.internal;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.beangle.commons.csv.CsvFormat;

/**
 * A very simple CSV parser released under a commercial-friendly license. This
 * just implements splitting a single line into fields.
 * 
 * @author chaostone
 * @version $Id: $
 */
public class CsvParser {

  private CsvFormat format;

  private String pending;

  private boolean inField = false;

  private final boolean ignoreLeadingWhiteSpace = true;

  /** Constant <code>INITIAL_READ_SIZE=128</code> */
  public static final int INITIAL_READ_SIZE = 128;

  /**
   * <p>
   * Constructor for CsvParser.
   * </p>
   * 
   * @param format a {@link org.beangle.commons.csv.CsvFormat} object.
   */
  public CsvParser(CsvFormat format) {
    this.format = format;
  }

  /**
   * <p>
   * isPending.
   * </p>
   * 
   * @return true if something was left over from last call(s)
   */
  public boolean isPending() {
    return pending != null;
  }

  /**
   * <p>
   * parseLineMulti.
   * </p>
   * 
   * @param nextLine a {@link java.lang.String} object.
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] parseLineMulti(String nextLine) {
    return parseLine(nextLine, true);
  }

  /**
   * <p>
   * parseLine.
   * </p>
   * 
   * @param nextLine a {@link java.lang.String} object.
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] parseLine(String nextLine) {
    return parseLine(nextLine, false);
  }

  /**
   * Parses an incoming String and returns an array of elements.
   * 
   * @param nextLine
   *          the string to parse
   * @param multi
   * @return the comma-tokenized list of elements, or null if nextLine is null
   * @throws IOException
   *           if bad things happen during the read
   */
  private String[] parseLine(String nextLine, boolean multi) {

    if (!multi && pending != null) {
      pending = null;
    }

    if (nextLine == null) {
      if (pending != null) {
        String s = pending;
        pending = null;
        return new String[] { s };
      } else {
        return null;
      }
    }

    List<String> tokensOnThisLine = new ArrayList<String>();
    StringBuilder sb = new StringBuilder(INITIAL_READ_SIZE);
    boolean inQuotes = false;
    if (pending != null) {
      sb.append(pending);
      pending = null;
      inQuotes = true;
    }
    for (int i = 0; i < nextLine.length(); i++) {

      char c = nextLine.charAt(i);
      if (format.isEscape(c)) {
        if (isNextCharacterEscapable(nextLine, inQuotes || inField, i)) {
          sb.append(nextLine.charAt(i + 1));
          i++;
        }
      } else if (c == format.getDelimiter()) {
        if (isNextCharacterEscapedQuote(nextLine, inQuotes || inField, i)) {
          sb.append(nextLine.charAt(i + 1));
          i++;
        } else {
          inQuotes = !inQuotes;

          // the tricky case of an embedded quote in the middle:
          // a,bc"d"ef,g
          if (!format.isStrictQuotes()) {
            if (i > 2 // not on the beginning of the line
                // not at the beginning of an escape
                // sequence
                && !format.isSeparator(nextLine.charAt(i - 1)) && nextLine.length() > (i + 1)
                // not at the end of an escape sequence
                && !format.isSeparator(nextLine.charAt(i + 1)) // not
            ) {
              // discard white space leading up to quote
              if (ignoreLeadingWhiteSpace && sb.length() > 0 && isAllWhiteSpace(sb)) {
                sb = new StringBuilder(INITIAL_READ_SIZE);
              } else {
                sb.append(c);
              }

            }
          }
        }
        inField = !inField;
      } else if (format.isSeparator(c) && !inQuotes) {
        tokensOnThisLine.add(sb.toString());
        sb = new StringBuilder(INITIAL_READ_SIZE); // start work on next
        // token
        inField = false;
      } else {
        if (!format.isStrictQuotes() || inQuotes) {
          sb.append(c);
          inField = true;
        }
      }
    }
    // line is done - check status
    if (inQuotes) {
      if (multi) {
        // continuing a quoted section, re-append newline
        sb.append("\n");
        pending = sb.toString();
        sb = null; // this partial content is not to be added to field
        // list yet
      } else {
        throw new RuntimeException("Un-terminated quoted field at end of CSV line");
      }
    }
    if (sb != null) {
      tokensOnThisLine.add(sb.toString());
    }
    return tokensOnThisLine.toArray(new String[tokensOnThisLine.size()]);

  }

  /**
   * precondition: the current character is a quote or an escape
   * 
   * @param nextLine the current line
   * @param inQuotes true if the current context is quoted
   * @param i current index in line
   * @return true if the following character is a quote
   */
  private boolean isNextCharacterEscapedQuote(String nextLine, boolean inQuotes, int i) {
    return inQuotes // we are in quotes, therefore there can be escaped
        // quotes in here.
        && nextLine.length() > (i + 1) // there is indeed another
        // character to check.
        && format.isDelimiter(nextLine.charAt(i + 1));
  }

  /**
   * precondition: the current character is an escape
   * 
   * @param nextLine the current line
   * @param inQuotes true if the current context is quoted
   * @param i current index in line
   * @return true if the following character is a quote
   */
  protected boolean isNextCharacterEscapable(String nextLine, boolean inQuotes, int i) {
    return inQuotes // we are in quotes, therefore there can be escaped
        // quotes in here.
        && nextLine.length() > (i + 1) // there is indeed another
        // character to check.
        && (format.isDelimiter(nextLine.charAt(i + 1)) || format.isEscape(nextLine.charAt(i + 1)));
  }

  /**
   * precondition: sb.length() > 0
   * 
   * @param sb A sequence of characters to examine
   * @return true if every character in the sequence is whitespace
   */
  protected boolean isAllWhiteSpace(CharSequence sb) {
    boolean result = true;
    for (int i = 0; i < sb.length(); i++) {
      char c = sb.charAt(i);

      if (!Character.isWhitespace(c)) { return false; }
    }
    return result;
  }
}
