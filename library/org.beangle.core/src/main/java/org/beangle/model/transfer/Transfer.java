/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer;

import java.util.Locale;

/**
 * 导入导出数据转换器
 * 
 * @author chaostone
 */
public interface Transfer {

	/**
	 * 启动转换
	 */
	public void transfer(TransferResult tr);

	/**
	 * 转换一个对象
	 */
	public void transferItem();

	/**
	 * 添加转换监听器
	 * 
	 * @param listener
	 */
	public Transfer addListener(TransferListener listener);

	/**
	 * 转换数据的类型
	 * 
	 * @return
	 */
	public String getFormat();

	/**
	 * 转换使用的locale
	 * 
	 * @return
	 */
	public Locale getLocale();

	/**
	 * 转换数据的名称
	 * 
	 * @return
	 */
	public String getDataName();

	/**
	 * 得到转换过程中失败的个数
	 * 
	 * @return
	 */
	public int getFail();

	/**
	 * 得到转换过程中成功的个数
	 * 
	 * @return
	 */
	public int getSuccess();

	/**
	 * 查询正在转换的对象的次序号,从1开始
	 * 
	 * @return
	 */
	public int getTranferIndex();

	/**
	 * 返回方前正在转换成的对象
	 * 
	 * @return
	 */
	public Object getCurrent();
}
