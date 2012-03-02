/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.transfer;

/**
 * 转换监听器
 * 
 * @author chaostone
 */
public interface TransferListener {

	/**
	 * 开始转换
	 */
	public void onStart(TransferResult tr);

	/**
	 * 结束转换
	 */
	public void onFinish(TransferResult tr);

	/**
	 * 开始转换单个项目
	 */
	public void onItemStart(TransferResult tr);

	/**
	 * 结束转换单个项目
	 */
	public void onItemFinish(TransferResult tr);

	/**
	 * 设置转换器
	 * 
	 * @param transfer
	 */
	public void setTransfer(Transfer transfer);
}
