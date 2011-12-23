/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.io;

import java.io.OutputStream;

import org.beangle.model.transfer.exporter.Context;

public abstract class AbstractItemWriter implements ItemWriter {
	protected OutputStream outputStream;

	protected Context context;

	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}

	public OutputStream getOutputStream() {
		return outputStream;
	}

	public void setContext(Context context) {
		this.context = context;
	}
}
