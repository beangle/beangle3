/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer;

/**
 * <p>
 * AbstractTransferListener class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class AbstractTransferListener implements TransferListener {
  protected Transfer transfer;

  /** {@inheritDoc} */
  public void onFinish(TransferResult tr) {
  }

  /** {@inheritDoc} */
  public void onItemFinish(TransferResult tr) {
  }

  /** {@inheritDoc} */
  public void setTransfer(Transfer transfer) {
    this.transfer = transfer;
  }

  /** {@inheritDoc} */
  public void onStart(TransferResult tr) {

  }

  /** {@inheritDoc} */
  public void onItemStart(TransferResult tr) {
  }

}
