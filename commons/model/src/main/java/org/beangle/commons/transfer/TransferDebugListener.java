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
