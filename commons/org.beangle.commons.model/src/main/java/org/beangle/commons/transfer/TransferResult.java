/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.transfer;

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
