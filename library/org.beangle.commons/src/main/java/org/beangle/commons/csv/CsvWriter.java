/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.csv;

import java.io.Closeable;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

public class CsvWriter implements Closeable {

	public static final int INITIAL_STRING_SIZE = 128;

	private Writer rawWriter;

	private PrintWriter pw;

	private CsvFormat format;

	private String lineEnd = "\n";

	/** The quote constant to use when you wish to suppress all quoting. */
	public static final char NO_QUOTE_CHARACTER = '\u0000';

	/** The escape constant to use when you wish to suppress all escaping. */
	public static final char NO_ESCAPE_CHARACTER = '\u0000';

	/** Default line terminator uses platform encoding. */
	public static final String DEFAULT_LINE_END = "\n";

	public CsvWriter(Writer writer) {
		this.rawWriter = writer;
		this.pw = new PrintWriter(writer);
		this.format = new CsvFormat.Builder().escape(NO_ESCAPE_CHARACTER).build();
	}

	public CsvWriter(Writer writer, CsvFormat format) {
		this.rawWriter = writer;
		this.pw = new PrintWriter(writer);
		this.format = format;
	}

	public void write(List<String[]> allLines) {
		for (String[] line : allLines) {
			write(line);
		}
	}

	public void write(String[] nextLine) {
		if (nextLine == null) return;
		StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
		for (int i = 0; i < nextLine.length; i++) {
			if (i != 0) {
				sb.append(format.defaultSeparator());
			}

			String nextElement = nextLine[i];
			if (nextElement == null) continue;
			if (!format.isDelimiter(NO_QUOTE_CHARACTER)) sb.append(format.getDelimiter());

			sb.append(stringContainsSpecialCharacters(nextElement) ? processLine(nextElement) : nextElement);

			if (!format.isDelimiter(NO_QUOTE_CHARACTER)) sb.append(format.getDelimiter());
		}
		sb.append(lineEnd);
		pw.write(sb.toString());

	}

	private boolean stringContainsSpecialCharacters(String line) {
		return line.indexOf(format.getDelimiter()) != -1 || line.indexOf(format.getDelimiter()) != -1;
	}

	protected StringBuilder processLine(String nextElement) {
		StringBuilder sb = new StringBuilder(INITIAL_STRING_SIZE);
		for (int j = 0; j < nextElement.length(); j++) {
			char nextChar = nextElement.charAt(j);
			if (format.getEscape() != NO_ESCAPE_CHARACTER && nextChar == format.getDelimiter()) {
				sb.append(format.getEscape()).append(nextChar);
			} else if (format.getEscape() != NO_ESCAPE_CHARACTER && nextChar == format.getEscape()) {
				sb.append(format.getEscape()).append(nextChar);
			} else {
				sb.append(nextChar);
			}
		}
		return sb;
	}

	public void flush() throws IOException {
		pw.flush();

	}

	public void close() throws IOException {
		flush();
		pw.close();
		rawWriter.close();
	}

	public boolean checkError() {
		return pw.checkError();
	}

}
