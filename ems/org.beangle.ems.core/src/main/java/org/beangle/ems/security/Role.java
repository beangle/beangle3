/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General  License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import java.util.Set;

import org.beangle.dao.pojo.HierarchyEntity;
import org.beangle.dao.pojo.LongIdTimeEntity;

/**
 * 系统角色的基本信息
 * 
 * @author chaostone 2005-9-26
 */
public interface Role extends LongIdTimeEntity, HierarchyEntity<Role, Long>, Comparable<Role> {

	/** 匿名角色id */
	static final long ANONYMOUS_ID = 1;

	/** 所有用户所在的公共组id */
	static final long ANYONE_ID = 2;

	/**
	 * 代码
	 * 
	 * @return
	 */
	String getCode();
	/**
	 * 名称
	 * 
	 * @return
	 */
	String getName();

	/**
	 * 查询权限
	 * 
	 * @return
	 */
	Set<Permission> getPermissions();

	/**
	 * 关联的系统用户
	 * 
	 * @return
	 */
	Set<Member> getMembers();

	/**
	 * Owner
	 * 
	 * @return
	 */
	User getOwner();

	/**
	 * 状态
	 * 
	 * @return
	 */
	boolean isEnabled();

	/**
	 * 备注
	 * 
	 * @return
	 */
	String getRemark();

	/**
	 * 设置备注
	 * 
	 * @param remark
	 */
	void setRemark(String remark);

	int getDepth();

}
