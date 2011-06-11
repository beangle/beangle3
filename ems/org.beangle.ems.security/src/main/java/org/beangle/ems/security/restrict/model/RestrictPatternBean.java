/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.ems.security.restrict.RestrictEntity;
import org.beangle.ems.security.restrict.RestrictPattern;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "org.beangle.ems.security.restrict.RestrictPattern")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RestrictPatternBean extends LongIdObject implements RestrictPattern {

	private static final long serialVersionUID = 3491583230212588933L;

	private String remark;

	@NotNull
	@Size(max = 600)
	private String content;

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
