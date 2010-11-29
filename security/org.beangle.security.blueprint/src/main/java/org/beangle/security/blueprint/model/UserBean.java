/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import java.util.Set;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdTimeObject;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.GroupMember;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.UserCategory;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.restrict.RestrictionHolder;

/**
 * 系统中所有用户的账号、权限、状态信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
public class UserBean extends LongIdTimeObject implements User, RestrictionHolder {
	private static final long serialVersionUID = -3625902334772342380L;

	/** 名称 */
	protected String name;

	/** 用户姓名 */
	private String fullname;

	/** 用户密文 */
	private String password;

	/** 用户联系email */
	private String mail;

	/** 对应用户组 */
	private Set<GroupMember> groups = CollectUtils.newHashSet();

	/** 创建人 */
	private User creator;

	/** 向下级授权,所管理的用户组 */
	private Set<Group> mngGroups = CollectUtils.newHashSet();

	/** 种类 */
	protected Set<UserCategory> categories = CollectUtils.newHashSet();;

	/** 缺省类别 */
	private UserCategory defaultCategory;

	/** 状态 */
	protected int status = User.ACTIVE;

	/** 访问限制 */
	protected Set<Restriction> restrictions = CollectUtils.newHashSet();

	/** 备注 */
	protected String remark;

	public UserBean() {
		super();
	}

	public UserBean(Long id) {
		setId(id);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isCategory(Long categoryId) {
		for (final UserCategory category : categories) {
			if (category.getId().equals(categoryId)) return true;
		}
		return false;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public UserCategory getDefaultCategory() {
		return defaultCategory;
	}

	public void setDefaultCategory(UserCategory defaultCategory) {
		this.defaultCategory = defaultCategory;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	/**
	 * 1 激活
	 */
	public boolean isEnabled() {
		return status == 1;
	}

	public Set<GroupMember> getGroups() {
		return groups;
	}

	public void setGroups(Set<GroupMember> groups) {
		this.groups = groups;
	}

	public Set<Group> getMngGroups() {
		return mngGroups;
	}

	public void setMngGroups(Set<Group> mngGroups) {
		this.mngGroups = mngGroups;
	}

	public Set<UserCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<UserCategory> categories) {
		this.categories = categories;
	}

	public Set<Restriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(Set<Restriction> restrictions) {
		this.restrictions = restrictions;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("password", this.password)
				.append("name", this.getName()).toString();
	}
}
