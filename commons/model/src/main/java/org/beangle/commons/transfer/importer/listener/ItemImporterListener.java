/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
