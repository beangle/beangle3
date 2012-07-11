/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer;

/**
 * 转换调试监听器
 * 
 * @author chaostone
 * @version $Id: $
 */
public class TransferDebugListener extends AbstractTransferListener {

  /** {@inheritDoc} */
  public void onStart(TransferResult tr) {
    tr.addMessage("start", transfer.getDataName());
  }

  /** {@inheritDoc} */
  public void onFinish(TransferResult tr) {
    tr.addMessage("end", transfer.getDataName());
  }

  /** {@inheritDoc} */
  public void onItemStart(TransferResult tr) {
    tr.addMessage("start Item", transfer.getTranferIndex() + "");
  }

  /** {@inheritDoc} */
  public void onItemFinish(TransferResult tr) {
    tr.addMessage("end Item", transfer.getCurrent());
  }

  /** {@inheritDoc} */
  public void setTransfer(Transfer transfer) {
    this.transfer = transfer;
  }

}
