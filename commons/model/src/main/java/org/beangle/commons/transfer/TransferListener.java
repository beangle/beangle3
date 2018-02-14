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
 * 转换监听器
 *
 * @author chaostone
 * @version $Id: $
 */
public interface TransferListener {

  /**
   * 开始转换
   *
   * @param tr a {@link org.beangle.commons.transfer.TransferResult} object.
   */
  void onStart(TransferResult tr);

  /**
   * 结束转换
   *
   * @param tr a {@link org.beangle.commons.transfer.TransferResult} object.
   */
  void onFinish(TransferResult tr);

  /**
   * 开始转换单个项目
   *
   * @param tr a {@link org.beangle.commons.transfer.TransferResult} object.
   */
  void onItemStart(TransferResult tr);

  /**
   * 结束转换单个项目
   *
   * @param tr a {@link org.beangle.commons.transfer.TransferResult} object.
   */
  void onItemFinish(TransferResult tr);

  /**
   * 设置转换器
   *
   * @param transfer a {@link org.beangle.commons.transfer.Transfer} object.
   */
  void setTransfer(Transfer transfer);
}
