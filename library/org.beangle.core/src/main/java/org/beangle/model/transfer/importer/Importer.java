/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.transfer.importer;

import java.util.Map;

import org.beangle.model.transfer.Transfer;
import org.beangle.model.transfer.io.Reader;

/**
 * 数据转换接口
 * 
 * @author chaostone
 */
public interface Importer extends Transfer {
	/**
	 * 是否忽略空值
	 * 
	 * @return
	 */
	public boolean ignoreNull();

	/**
	 * 设置数据读取对象
	 */
	public void setReader(Reader reader);

	/**
	 * 获取reader
	 * 
	 * @return
	 */
	public Reader getReader();

	/**
	 * 读取数据，设置内部步进状态等
	 * 
	 * @return
	 */
	public boolean read();

	/**
	 * 当前读入的数据是否有效
	 * 
	 * @return
	 */
	public boolean isDataValid();

	/**
	 * 设置当前正在转换的对象
	 * 
	 * @param object
	 */
	public void setCurrent(Object object);

	/**
	 * 返回现在正在转换的原始数据
	 * 
	 * @return
	 */
	public Map<String, Object> getCurData();

	/**
	 * 设置正在转换的对象
	 * 
	 * @param curData
	 */
	public void setCurData(Map<String, Object> curData);

	public ImportPrepare getPrepare();

	public void setPrepare(ImportPrepare prepare);
}
