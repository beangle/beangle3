/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.csv;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * csv format definition
 * 
 * @author chaostone
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

	public boolean isStrictQuotes() {
		return strictQuotes;
	}

	public boolean isSeparator(char a) {
		return separators.contains(a);
	}

	public boolean isDelimiter(char a) {
		return a == delimiter;
	}

	public boolean isEscape(char a) {
		return a == escape;
	}

	public Set<Character> getSeparators() {
		return separators;
	}

	public char getDelimiter() {
		return delimiter;
	}

	public char getEscape() {
		return escape;
	}

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
