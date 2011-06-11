/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import java.util.Set;

import org.beangle.model.pojo.LongIdTimeEntity;
import org.beangle.ems.security.restrict.RestrictionHolder;
import org.beangle.ems.security.restrict.UserRestriction;

/**
 * 系统中所有用户的账号、权限、状态信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
public interface User extends LongIdTimeEntity, RestrictionHolder<UserRestriction> {

	// 新建用户的缺省密码
	public static final String DEFAULT_PASSWORD = "1";

	// 冻结
	public static final int FREEZE = 0;

	// 激活
	public static final int ACTIVE = 1;

	/**根用户id*/
	public static final Long ROOT = 1L;

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
	 * 用户真实姓名
	 * 
	 * @return
	 */
	public String getFullname();

	/**
	 * 设置用户真实姓名
	 * 
	 * @param fullname
	 */
	public void setFullname(String fullname);

	/**
	 * 用户密码(不限制是明码还是密文)
	 * 
	 * @return
	 */
	public String getPassword();

	/**
	 * 设置密码
	 * 
	 * @param password
	 */
	public void setPassword(String password);

	/**
	 * 用户邮件
	 * 
	 * @return
	 */
	public String getMail();

	/**
	 * 用户邮件
	 * 
	 * @return
	 */
	public void setMail(String mail);

	/**
	 * 对应用户组
	 * 
	 * @return
	 */
	public Set<GroupMember> getGroups();

	/**
	 * 设置对应用户组
	 * 
	 * @param groups
	 */
	public void setGroups(Set<GroupMember> groups);

	/**
	 * 状态
	 * 
	 * @return
	 */
	public int getStatus();

	/**
	 * 设置状态
	 * 
	 * @param status
	 */
	public void setStatus(int status);

	/**
	 * 类别.
	 * 
	 * @return
	 */
	public Set<Category> getCategories();

	/**
	 * 设置类别.
	 * 
	 * @param categories
	 */
	public void setCategories(Set<Category> HasSet);

	/**
	 * 缺省类别
	 * 
	 * @return
	 */
	public Category getDefaultCategory();

	/**
	 * 设置缺省类别
	 * 
	 * @param userCategory
	 */
	public void setDefaultCategory(Category userCategory);

	public boolean isCategory(Long categoryId);

	/**
	 * 创建者
	 * 
	 * @return
	 */
	public User getCreator();

	/**
	 * 设置创建者
	 * 
	 * @param creator
	 */
	public void setCreator(User creator);

	/**
	 * 是否启用
	 * 
	 * @return
	 */
	public boolean isEnabled();

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
