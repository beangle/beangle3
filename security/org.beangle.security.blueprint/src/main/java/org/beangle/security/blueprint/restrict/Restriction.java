/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict;

import java.util.Map;

import org.beangle.model.pojo.LongIdEntity;

/**
 * 资源访问限制
 * 
 * @author chaostone
 */
public interface Restriction extends LongIdEntity, Cloneable {
	public static final String ALL = "*";

	/**
	 * 数据权限持有者
	 * 
	 * @return
	 */
	public RestrictionHolder getHolder();

	/**
	 * 设置数据权限持有者
	 * 
	 * @param holder
	 */
	public void setHolder(RestrictionHolder holder);

	/**
	 * 限制模式
	 * @return
	 */
	public RestrictPattern getPattern();

	/**
	 * 设置限制模式
	 * @param pattern
	 */
	public void setPattern(RestrictPattern pattern);

	/**
	 * 限制项
	 * 
	 * @return
	 */
	public Map<Long, String> getItems();

	/**
	 * 设置限制项
	 * @param items
	 */
	public void setItems(Map<Long, String> items);

	public String getItem(String fieldName);

	public String getItem(RestrictField field);

	public void setItem(RestrictField field, String text);

	/**
	 * 是否有效
	 * 
	 * @return
	 */
	public boolean isEnabled();

	/**
	 * 设置是否有效
	 * 
	 * @param isEnabled
	 */
	public void setEnabled(boolean isEnabled);
}
