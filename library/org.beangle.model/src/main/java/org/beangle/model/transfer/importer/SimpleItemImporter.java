/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.importer;

public class SimpleItemImporter extends AbstractItemImporter {

	public void setCurrent(Object object) {

	}

	public Object getCurrent() {
		return getCurData();
	}

	public String getDataName() {
		return getCurData().getClass().getName();
	}

	public void transferItem() {
	}
}
