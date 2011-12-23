/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.util.csv.internal;

/**
 Copyright 2005 Bytecode Pty Ltd.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.beangle.util.csv.CsvFormat;

/**
 * A very simple CSV parser released under a commercial-friendly license. This
 * just implements splitting a single line into fields.
 * 
 * @author Glen Smith
 * @author Rainer Pruy
 */
public class CsvParser {

	private CsvFormat format;

	private String pending;

	private boolean inField = false;

	private final boolean ignoreLeadingWhiteSpace = true;

	public static final int INITIAL_READ_SIZE = 128;

	public CsvParser(CsvFormat format) {
		this.format = format;
	}

	/**
	 * @return true if something was left over from last call(s)
	 */
	public boolean isPending() {
		return pending != null;
	}

	public String[] parseLineMulti(String nextLine) {
		return parseLine(nextLine, true);
	}

	public String[] parseLine(String nextLine) {
		return parseLine(nextLine, false);
	}

	/**
	 * Parses an incoming String and returns an array of elements.
	 * 
	 * @param nextLine
	 *            the string to parse
	 * @param multi
	 * @return the comma-tokenized list of elements, or null if nextLine is null
	 * @throws IOException
	 *             if bad things happen during the read
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
	 * @param nextLine
	 *            the current line
	 * @param inQuotes
	 *            true if the current context is quoted
	 * @param i
	 *            current index in line
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
	 * @param nextLine
	 *            the current line
	 * @param inQuotes
	 *            true if the current context is quoted
	 * @param i
	 *            current index in line
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
	 * @param sb
	 *            A sequence of characters to examine
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
