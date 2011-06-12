/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dictionary.service;

import java.util.List;

import org.beangle.ems.dictionary.model.BaseCode;

/**
 * @author chaostone
 * @version $Id: BaseCodeService.java May 4, 2011 7:49:02 PM chaostone $
 */
public interface BaseCodeService {
	/**
	 * 依据code的类型和主键查找,无效时返回null.
	 * 
	 * @param type
	 * @param codeId
	 * @return
	 */
	public <T extends BaseCode<?>> T getCode(Class<T> type, Long codeId);

	/**
	 * 依据code的类型和代码查找,无效时返回null.
	 * 
	 * @param type
	 * @param code
	 * @return
	 */
	public <T extends BaseCode<?>> T getCode(Class<T> type, String code);

	/**
	 * 返回现有的有效使用的代码
	 * 
	 * @param type
	 * @return
	 */
	public <T extends BaseCode<?>> List<T> getCodes(Class<T> type);

	/**
	 * 查询指定id的基础代码
	 * 
	 * @param type
	 * @param ids
	 * @return
	 */
	public <T extends BaseCode<?>> List<T> getCodes(Class<T> type, Long... ids);

	/**
	 * 查找指定名称基础代码
	 * 
	 * @param name
	 * @return
	 */
	public Class<? extends BaseCode<?>> getCodeType(String name);

	/**
	 * 新增代码 如果新的代码已经存在，则抛出异常.
	 * 
	 * @param code
	 */
	public void saveOrUpdate(BaseCode<?> code);

	/**
	 * 删除基础代码
	 * 
	 * @param codeClass
	 * @param codeIds
	 */
	public void removeCodes(Class<? extends BaseCode<?>> codeClass, Long... codeIds);
}
