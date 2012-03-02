/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import java.util.Set;

import org.beangle.dao.pojo.HierarchyEntity;
import org.beangle.dao.pojo.LongIdTimeEntity;

/**
 * 系统用户组的基本信息
 * 
 * @author chaostone 2005-9-26
 */
public interface Group extends LongIdTimeEntity, HierarchyEntity<Group,Long>, Comparable<Group> {

	/** 匿名用户组id */
	public static final long ANONYMOUS_ID = 1;

	/** 所有用户所在的公共组id */
	public static final long ANYONE_ID = 2;

	/**
	 * 代码
	 * 
	 * @return
	 */
	public String getCode();

	/**
	 * 设置代码
	 * 
	 * @param code
	 */
	public void setCode(String code);

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
	 * 查询权限
	 * 
	 * @return
	 */
	public Set<Authority> getAuthorities();

	/**
	 * 设置权限
	 * 
	 * @param authorities
	 */
	public void setAuthorities(Set<Authority> authorities);

	/**
	 * 关联的系统用户
	 * 
	 * @return
	 */
	public Set<GroupMember> getMembers();

	/**
	 * 关联的系统用户
	 * 
	 * @param users
	 */
	public void setMembers(Set<GroupMember> members);

	/**
	 * Owner
	 * 
	 * @return
	 */
	public User getOwner();

	/**
	 * 设置创建者
	 * 
	 * @param owner
	 */
	public void setOwner(User owner);

	/**
	 * 状态
	 * 
	 * @return
	 */
	public boolean isEnabled();

	/**
	 * 设置状态
	 * 
	 * @param isEnabled
	 */
	public void setEnabled(boolean isEnabled);

	/**
	 * 备注
	 * 
	 * @return
	 */
	public String getRemark();

	/**
	 * 设置备注
	 * 
	 * @param remark
	 */
	public void setRemark(String remark);

	int getDepth();

}
