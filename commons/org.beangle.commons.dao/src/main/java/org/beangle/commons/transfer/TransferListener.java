/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
