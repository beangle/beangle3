/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import java.util.Set;

import org.beangle.model.pojo.LongIdTimeEntity;
import org.beangle.security.blueprint.restrict.RestrictionHolder;

/**
 * 系统用户组的基本信息
 * 
 * @author chaostone 2005-9-26
 */
public interface Group extends LongIdTimeEntity, RestrictionHolder {

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
	 * 上级组
	 * 
	 * @return
	 */
	public Group getParent();

	/**
	 * 设置上级组
	 * 
	 * @param parent
	 */
	public void setParent(Group parent);

	/**
	 * 下级组
	 * 
	 * @return
	 */
	public Set<Group> getChildren();

	/**
	 * 设置下级组
	 * 
	 * @param children
	 */
	public void setChildren(Set<Group> children);

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
	 * 用户组对应的类别.
	 * 
	 * @return
	 */
	public UserCategory getCategory();

	/**
	 * 设置用户组对应的类别.
	 * 
	 * @param categories
	 */
	public void setCategory(UserCategory userCategory);

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

}
