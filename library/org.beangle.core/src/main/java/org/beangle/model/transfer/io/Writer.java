/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.io;

import java.io.OutputStream;

import org.beangle.model.transfer.exporter.Context;

public interface Writer {

	public String getFormat();

	public void setContext(Context context);

	public OutputStream getOutputStream();

	public void setOutputStream(OutputStream outputStream);

	public void close();
}
