/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.csv;

import java.util.ArrayList;
import java.util.List;

/**
 * csv document object.
 * 
 * @author chaostone
 */
public class Csv {

	private final CsvFormat format;

	private List<String[]> contents = new ArrayList<String[]>();

	public Csv() {
		this.format = new CsvFormat.Builder().build();
	}

	public Csv(CsvFormat format) {
		this.format = format;
	}

	public CsvFormat getFormat() {
		return format;
	}

	public List<String[]> getContents() {
		return contents;
	}

}
