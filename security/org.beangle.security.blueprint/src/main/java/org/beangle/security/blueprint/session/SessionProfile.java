/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.session;

import java.util.Map;

import org.beangle.model.pojo.LongIdEntity;

/**
 * 在线会话配置
 * 
 * @author chaostone
 */
public interface SessionProfile extends LongIdEntity {

	/**
	 * 名称
	 * 
	 * @return
	 */
	public String getName();

	/**
	 * 设置名称
	 * 
	 * @param name
	 */
	public void setName(String name);

	/**
	 * 各身份用户的配置
	 * 
	 * @return
	 */
	public Map<Long, CategoryProfile> getCategoryProfiles();

	/**
	 * 设置各身份用户的配置
	 */
	public void setCategoryProfiles(Map<Long, CategoryProfile> categoryProfiles);

	/**
	 * 最大session数
	 * 
	 * @param sessions
	 * @return
	 */
	public int getCapacity();

	/**
	 * 设置最大session数
	 * 
	 * @param sessions
	 */
	public void setCapacity(int sessions);

	/**
	 * 过期时间(以分计(min))
	 * 
	 * @return
	 */
	public int getInactiveInterval();

	/**
	 * 设置过期时间(以分计(min))
	 * 
	 * @param second
	 */
	public void setInactiveInterval(int second);

	/**
	 * 单个用户的最大session数
	 * 
	 * @param sessions
	 * @return
	 */
	public int getUserMaxSessions();

	/**
	 * 设置单个用户的最大session数
	 * 
	 * @param sessions
	 */
	public void setUserMaxSessions(int sessions);
}
