/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.transfer;

import java.util.Map;

/**
 * 基于行的转换器
 * 
 * @author chaostone
 */
public interface ItemTransfer extends Transfer {

	/**
	 * 返回要转换的各个属性的名称
	 * 
	 * @return
	 */
	public String[] getAttrs();

	/**
	 * 将当前正在转换的数据转换成map[attr,value]
	 * 
	 * @return
	 */
	public Map<String, Object> getCurData();
}
