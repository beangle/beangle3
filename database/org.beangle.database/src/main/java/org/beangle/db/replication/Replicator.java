/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.db.replication;

import java.util.Collection;

public interface Replicator {

	/**
	 * 设置目标数据源
	 * 
	 * @param dataSource
	 */
	void setTarget(DataWrapper source);

	/**
	 * 设置源数据源
	 * 
	 * @param dataSource
	 */
	void setSource(DataWrapper source);

	/**
	 * 添加表
	 * 
	 * @param string
	 */
	boolean addTable(String string);

	/**
	 * 添加表
	 * 
	 * @param strings
	 */
	boolean addTables(String[] strings);

	/**
	 * 添加表
	 * 
	 * @param tables
	 */
	boolean addTables(Collection<String> tables);

	/**
	 * 重新开始
	 */
	void reset();

	/**
	 * 开始导入
	 */
	void start();

}
