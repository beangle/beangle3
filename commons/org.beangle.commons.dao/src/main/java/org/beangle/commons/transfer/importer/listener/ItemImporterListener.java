/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.importer.listener;

import org.beangle.commons.transfer.Transfer;
import org.beangle.commons.transfer.TransferListener;
import org.beangle.commons.transfer.TransferResult;
import org.beangle.commons.transfer.importer.AbstractItemImporter;

/**
 * <p>
 * ItemImporterListener class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class ItemImporterListener implements TransferListener {

  protected AbstractItemImporter importer;

  /** {@inheritDoc} */
  public void onFinish(TransferResult tr) {
  }

  /** {@inheritDoc} */
  public void onItemFinish(TransferResult tr) {
  }

  /** {@inheritDoc} */
  public void setTransfer(Transfer transfer) {
    if (transfer instanceof AbstractItemImporter) {
      this.importer = (AbstractItemImporter) transfer;
    }
  }

  /** {@inheritDoc} */
  public void onStart(TransferResult tr) {
  }

  /** {@inheritDoc} */
  public void onItemStart(TransferResult tr) {
  }

}
