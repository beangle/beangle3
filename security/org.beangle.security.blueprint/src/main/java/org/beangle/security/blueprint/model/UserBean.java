/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdTimeObject;
import org.beangle.security.blueprint.Category;
import org.beangle.security.blueprint.GroupMember;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.restrict.RestrictionHolder;
import org.beangle.security.blueprint.restrict.UserRestriction;

/**
 * 系统中所有用户的账号、权限、状态信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.security.blueprint.User")
public class UserBean extends LongIdTimeObject implements User, RestrictionHolder<UserRestriction> {
	private static final long serialVersionUID = -3625902334772342380L;

	/** 名称 */
	@Size(max = 40)
	@NotNull
	@Column(unique = true)
	protected String name;

	/** 用户姓名 */
	@NotNull
	@Size(max = 50)
	private String fullname;

	/** 用户密文 */
	@Size(max = 100)
	@NotNull
	private String password;

	/** 用户联系email */
	@NotNull
	private String mail;

	/** 对应用户组 */
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private Set<GroupMember> groups = CollectUtils.newHashSet();

	/** 创建人 */
	private User creator;

	/** 种类 */
	@ManyToMany
	protected Set<Category> categories = CollectUtils.newHashSet();;

	/** 缺省类别 */
	@NotNull
	private Category defaultCategory;

	/** 状态 */
	@NotNull
	protected int status = User.ACTIVE;

	/** 访问限制 */
	@OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
	protected Set<UserRestriction> restrictions = CollectUtils.newHashSet();

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
		for (final Category category : categories) {
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

	public Category getDefaultCategory() {
		return defaultCategory;
	}

	public void setDefaultCategory(Category defaultCategory) {
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

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	public Set<UserRestriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(Set<UserRestriction> restrictions) {
		this.restrictions = restrictions;
	}

	public String toString() {
		return new ToStringBuilder(this).append("id", this.id).append("password", this.password)
				.append("name", this.getName()).toString();
	}
}
