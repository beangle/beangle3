/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity.meta.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.model.entity.meta.EntityMeta;
import org.beangle.model.entity.meta.PropertyMeta;
import org.beangle.model.pojo.LongIdObject;

/**
 * 属性元数据实现
 */
@Entity(name = "org.beangle.model.entity.meta.PropertyMeta")
public class PropertyMetaBean extends LongIdObject implements PropertyMeta {
	private static final long serialVersionUID = 8581246709461219082L;

	/** 所属元数据 */
	@NotNull
	private EntityMeta meta;
	/** 属性名称 */
	@NotNull
	private String name;
	/** 类型 */
	@NotNull
	private String type;
	/** 属性说明 */
	@Size(max = 40)
	private String comments;
	/**备注*/
	@Size(max = 100)
	private String remark;
	
	public PropertyMetaBean() {
	}

	public PropertyMetaBean(Long id) {
		this.id = id;
	}

	public EntityMeta getMeta() {
		return meta;
	}

	public void setMeta(EntityMeta meta) {
		this.meta = meta;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
