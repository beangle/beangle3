/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.ems.security.restrict.RestrictEntity;
import org.beangle.ems.security.restrict.RestrictPattern;
import org.beangle.model.pojo.LongIdObject;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 数据限制模式
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.security.restrict.RestrictPattern")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RestrictPatternBean extends LongIdObject implements RestrictPattern {

	private static final long serialVersionUID = 3491583230212588933L;

	/** 备注说明 */
	private String remark;

	/** 限制内容 */
	@NotNull
	@Size(max = 600)
	private String content;

	/** 限制实体 */
	@NotNull
	private RestrictEntity entity;

	public RestrictPatternBean() {
		super();
	}

	public RestrictPatternBean(RestrictEntity entity, String content) {
		this.entity = entity;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String pattern) {
		this.content = pattern;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String discription) {
		this.remark = discription;
	}

	public RestrictEntity getEntity() {
		return entity;
	}

	public void setEntity(RestrictEntity entity) {
		this.entity = entity;
	}

}
