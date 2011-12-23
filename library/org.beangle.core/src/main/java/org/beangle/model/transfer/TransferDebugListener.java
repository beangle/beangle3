/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer;

/**
 * 转换调试监听器
 * 
 * @author chaostone
 */
public class TransferDebugListener extends AbstractTransferListener {

	public void onStart(TransferResult tr) {
		tr.addMessage("start", transfer.getDataName());
	}

	public void onFinish(TransferResult tr) {
		tr.addMessage("end", transfer.getDataName());
	}

	public void onItemStart(TransferResult tr) {
		tr.addMessage("start Item", transfer.getTranferIndex() + "");
	}

	public void onItemFinish(TransferResult tr) {
		tr.addMessage("end Item", transfer.getCurrent());
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

}
