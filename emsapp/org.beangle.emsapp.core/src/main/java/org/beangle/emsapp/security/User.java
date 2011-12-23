/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security;

import java.security.Principal;
import java.util.List;
import java.util.Set;

import org.beangle.model.pojo.EnabledEntity;
import org.beangle.model.pojo.LongIdTimeEntity;
import org.beangle.model.pojo.TemporalActiveEntity;

/**
 * 系统中所有用户的账号、权限、状态信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
public interface User extends LongIdTimeEntity, TemporalActiveEntity, EnabledEntity, Principal {

	// 新建用户的缺省密码
	public static final String DEFAULT_PASSWORD = "1";
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
	 * 对应用户组成员
	 * 
	 * @return
	 */
	public Set<GroupMember> getMembers();

	/**
	 * 对应用户组
	 * 
	 * @return 按照用户组代码排序的group列表
	 */
	public List<Group> getGroups();

	/**
	 * 设置对应用户组
	 * 
	 * @param groups
	 */
	public void setMembers(Set<GroupMember> members);

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

	/**
	 * 账户是否过期
	 * 
	 * @return
	 */
	public boolean isAccountExpired();

	/**
	 * 是否密码过期
	 * 
	 * @return
	 */
	public boolean isPasswordExpired();

}
