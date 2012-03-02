/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.transfer.importer.listener;

import org.beangle.transfer.Transfer;
import org.beangle.transfer.TransferListener;
import org.beangle.transfer.TransferResult;
import org.beangle.transfer.importer.AbstractItemImporter;

public class ItemImporterListener implements TransferListener {

	protected AbstractItemImporter importer;

	public void onFinish(TransferResult tr) {
	}

	public void onItemFinish(TransferResult tr) {
	}

	public void setTransfer(Transfer transfer) {
		if (transfer instanceof AbstractItemImporter) {
			this.importer = (AbstractItemImporter) transfer;
		}
	}

	public void onStart(TransferResult tr) {
	}

	public void onItemStart(TransferResult tr) {
	}

}
