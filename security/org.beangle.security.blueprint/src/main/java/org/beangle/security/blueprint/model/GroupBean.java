/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdTimeObject;
import org.beangle.security.blueprint.Authority;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.GroupMember;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.Category;
import org.beangle.security.blueprint.restrict.GroupRestriction;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 系统中用户组的基本信息和账号信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.security.blueprint.Group")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class GroupBean extends LongIdTimeObject implements Group {

	private static final long serialVersionUID = -3404181949500894284L;

	/** 名称 */
	@Size(max = 100)
	@NotNull
	@Column(unique = true)
	private String name;

	/** 关联的用户 */
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	private Set<GroupMember> members = CollectUtils.newHashSet();

	/** 对应的用户类别 */
	@NotNull
	private Category category;

	/** 上级组 */
	private Group parent;

	/** 下级组 */
	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private Set<Group> children = CollectUtils.newHashSet();

	/** 创建人 */
	@NotNull
	private User owner;

	/** 备注 */
	protected String remark;

	/** 是否启用 */
	@NotNull
	public boolean enabled = true;

	/** 访问限制 */
	@OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
	protected Set<GroupRestriction> restrictions = CollectUtils.newHashSet();

	/** 权限 */
	@OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
	protected Set<Authority> authorities = CollectUtils.newHashSet();

	public GroupBean() {
		super();
	}

	public GroupBean(Long id) {
		setId(id);
	}

	public GroupBean(Long id, String name) {
		setId(id);
		this.name = name;
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

	public Set<GroupRestriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(Set<GroupRestriction> restrictions) {
		this.restrictions = (Set<GroupRestriction>) restrictions;
	}

	public void setAuthorities(Set<Authority> authorities) {
		this.authorities = authorities;
	}

	public Set<Authority> getAuthorities() {
		return authorities;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category userCategory) {
		this.category = userCategory;
	}

	public Group getParent() {
		return parent;
	}

	public void setParent(Group parent) {
		this.parent = parent;
	}

	public Set<Group> getChildren() {
		return children;
	}

	public void setChildren(Set<Group> children) {
		this.children = children;
	}

	public Set<GroupMember> getMembers() {
		return members;
	}

	public void setMembers(Set<GroupMember> members) {
		this.members = members;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public String toString() {
		return name;
	}

}
