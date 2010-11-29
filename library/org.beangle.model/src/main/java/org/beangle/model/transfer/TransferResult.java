/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer;

import java.util.ArrayList;
import java.util.List;

/**
 * 转换结果
 * 
 * @author chaostone
 */
public class TransferResult {

	List<TransferMessage> msgs = new ArrayList<TransferMessage>();

	List<TransferMessage> errs = new ArrayList<TransferMessage>();

	Transfer transfer;

	public void addFailure(String message, Object value) {
		errs.add(new TransferMessage(transfer.getTranferIndex(), message, value));
	}

	public void addMessage(String message, Object value) {
		msgs.add(new TransferMessage(transfer.getTranferIndex(), message, value));
	}

	public boolean hasErrors() {
		return !errs.isEmpty();
	}

	public void printResult() {
		for (final TransferMessage msg : msgs) {
			System.out.println(msg);
		}
	}

	public int errors() {
		return errs.size();
	}

	public List<TransferMessage> getMsgs() {
		return msgs;
	}

	public void setMsgs(List<TransferMessage> msgs) {
		this.msgs = msgs;
	}

	public List<TransferMessage> getErrs() {
		return errs;
	}

	public void setErrs(List<TransferMessage> errs) {
		this.errs = errs;
	}

	public Transfer getTransfer() {
		return transfer;
	}

	public void setTransfer(Transfer transfer) {
		this.transfer = transfer;
	}

}
