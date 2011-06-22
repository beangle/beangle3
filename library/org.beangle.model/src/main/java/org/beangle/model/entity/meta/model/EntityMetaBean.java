/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.entity.meta.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.model.entity.meta.EntityMeta;
import org.beangle.model.pojo.LongIdObject;

/**
 * 实体元信息
 */
@Entity(name = "org.beangle.model.entity.meta.EntityMeta")
public class EntityMetaBean extends LongIdObject implements EntityMeta {
	private static final long serialVersionUID = 7143049317022571097L;

	/** 实体名称 */
	@NotNull
	private String name;
	/** 实体别名 */
	@NotNull
	private String comments;
	/** 实体备注 */
	@Size(max = 500)
	private String remark;

	public EntityMetaBean() {

	}

	public EntityMetaBean(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
