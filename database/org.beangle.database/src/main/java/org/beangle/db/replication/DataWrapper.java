/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

import java.util.List;

import org.beangle.db.meta.TableMetadata;

public interface DataWrapper {
	/**
	 * 查询单独表的数据
	 * 
	 * @param tableName
	 * @return
	 */
	public List<Object> getData(String tableName);

	/**
	 * 查询单独表的数据
	 * 
	 * @param table
	 * @return
	 */
	public List<Object> getData(TableMetadata tableMetadata);

	/**
	 * 插入单独表的数据
	 * 
	 * @param table
	 * @param data
	 * @return 返回成功数量
	 */
	public int pushData(TableMetadata tableMetadata, List<Object> data);

	/**
	 * 关闭数据链接
	 */
	public void close();

	/**
	 * 得到数据的数量
	 * 
	 * @param table
	 * @return
	 */
	public int count(TableMetadata table);

}
