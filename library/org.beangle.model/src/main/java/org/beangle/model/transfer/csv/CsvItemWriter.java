/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.csv;

import java.io.OutputStream;
import java.io.OutputStreamWriter;

import org.beangle.commons.csv.CsvFormat;
import org.beangle.commons.csv.CsvWriter;
import org.beangle.model.transfer.io.AbstractItemWriter;
import org.beangle.model.transfer.io.TransferFormats;

public class CsvItemWriter extends AbstractItemWriter {

	protected CsvWriter csvr;

	protected CsvFormat csvFormat;

	public CsvItemWriter() {
		super();
	}

	public CsvItemWriter(OutputStream outputStream) {
		setOutputStream(outputStream);
	}

	public void write(Object obj) {
		if (null == csvr) {
			if (null == csvFormat) {
				this.csvr = new CsvWriter(new OutputStreamWriter(outputStream));
			} else {
				this.csvr = new CsvWriter(new OutputStreamWriter(outputStream), csvFormat);
			}
		}
		if (null == obj) return;
		try {
			if (obj.getClass().isArray()) {
				Object[] values = (Object[]) obj;
				String[] strValues = new String[values.length];
				for (int i = 0; i < values.length; i++) {
					strValues[i] = (null == values[i]) ? "" : values[i].toString();
				}
				csvr.write(strValues);
			} else {
				csvr.write(new String[] { obj.toString() });
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public void writeTitle(String titleName, Object data) {
		write(data);
	}

	public void close() {
		try {
			csvr.close();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}
	}

	public String getFormat() {
		return TransferFormats.CSV;
	}

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public CsvFormat getCsvFormat() {
		return csvFormat;
	}

	public void setCsvFormat(CsvFormat csvFormat) {
		this.csvFormat = csvFormat;
	}

}
