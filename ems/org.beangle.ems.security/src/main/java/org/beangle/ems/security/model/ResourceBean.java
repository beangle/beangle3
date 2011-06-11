/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.model;

import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.ems.security.Category;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.restrict.RestrictEntity;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

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
@Entity(name = "org.beangle.ems.security.Resource")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class ResourceBean extends LongIdObject implements Resource {
	private static final long serialVersionUID = -8285208615351119572L;

	/** 模块名字 */
	@Size(max = 100)
	@NotNull
	@Column(unique = true)
	private String name;

	/** 模块标题 */
	@Size(max = 100)
	@NotNull
	private String title;

	/** 简单描述 */
	@Size(max = 100)
	private String remark;

	/** 模块是否可用 */
	@NotNull
	private boolean enabled = true;

	/** 资源访问范围 */
	@NotNull
	private int scope = Scope.PRIVATE;

	@ManyToMany
	@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
	private Set<Category> categories = CollectUtils.newHashSet();

	@ManyToMany
	@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
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

	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
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

	public String getDescription() {
		return StrUtils.concat(name, "[", title, "]");
	}
}
