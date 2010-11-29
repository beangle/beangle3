/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.io;

public interface ItemReader extends Reader {

	public String[] readDescription();

	public String[] readTitle();

	public void setDataIndex(int dataIndex);

	public int getDataIndex();

	public void setHeadIndex(int headIndex);

	public int getHeadIndex();
}
