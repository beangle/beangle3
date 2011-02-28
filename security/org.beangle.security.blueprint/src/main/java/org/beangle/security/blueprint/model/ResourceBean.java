/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.UserCategory;
import org.beangle.security.blueprint.restrict.RestrictEntity;

/**
 * 系统模块，代表一组系统功能点的集合.<br>
 * <p>
 * 系统模块之间存在基于代码表示上的父子级联关系.<br>
 * 上下级关系是通过模块代码的包含关系体现的。<br>
 * 系统模块作为权限分配的基本单位.
 * <p>
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.security.blueprint.AdminUser")
public class ResourceBean extends LongIdObject implements Resource {
	private static final long serialVersionUID = -8285208615351119572L;

	/** 模块名字 */
	private String name;

	/** 模块标题 */
	private String title;

	/** 简单描述 */
	private String remark;

	/** 模块是否可用 */
	private boolean enabled = true;

	/** 资源访问范围 */
	private int scope = Scope.PRIVATE;

	@ManyToMany
	private Set<UserCategory> categories = CollectUtils.newHashSet();

	@ManyToMany
	private Set<RestrictEntity> entities = CollectUtils.newHashSet();

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Set<UserCategory> getCategories() {
		return categories;
	}

	public void setCategories(Set<UserCategory> categories) {
		this.categories = categories;
	}

	public int getScope() {
		return scope;
	}

	public void setScope(int scope) {
		this.scope = scope;
	}

	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).append("id", this.id)
				.append("remark", this.remark).toString();
	}

	public Set<RestrictEntity> getEntities() {
		return entities;
	}

	public void setEntities(Set<RestrictEntity> entities) {
		this.entities = entities;
	}

}
