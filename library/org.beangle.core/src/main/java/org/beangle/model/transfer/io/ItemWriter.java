/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.io;

public interface ItemWriter extends Writer {

	public void write(Object obj);

	public void writeTitle(String titleName, Object data);

}
