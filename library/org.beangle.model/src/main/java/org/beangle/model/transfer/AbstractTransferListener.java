/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer;

public class AbstractTransferListener implements TransferListener {
	protected Transfer transfer;

	public void onFinish(TransferResult tr) {
	}

	public void onItemFinish(TransferResult tr) {
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

	public void onStart(TransferResult tr) {

	}

	public void onItemStart(TransferResult tr) {
	}

}
