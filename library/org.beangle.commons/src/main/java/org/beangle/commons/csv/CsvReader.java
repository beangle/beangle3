/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.beangle.commons.csv.internal.CsvParser;

public class CsvReader {

	private boolean hasNext = true;

	private boolean linesSkiped;

	private int skipLines;

	private BufferedReader br;

	private CsvParser parser;

	public CsvReader(Reader reader) {
		this(reader, new CsvFormat.Builder().build());
	}

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
	 *             if bad things happen during the read
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
