/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.beangle.emsapp.security.Resource;
import org.beangle.lang.StrUtils;
import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 系统资源
 * 代表一组系统功能点的集合.<br>
 * <p>
 * 系统模块之间存在基于代码表示上的父子级联关系.<br>
 * 上下级关系是通过模块代码的包含关系体现的。<br>
 * 系统模块作为权限分配的基本单位.
 * <p>
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.emsapp.security.Resource")
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

	public String getDescription() {
		return StrUtils.concat(name, "[", title, "]");
	}

	public String toString() {
		return new ToStringBuilder(this).append("name", this.name).append("id", this.id)
				.append("remark", this.remark).toString();
	}

}
