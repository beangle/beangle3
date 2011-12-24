/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.beangle.ems.security.restrict.RestrictEntity;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.model.pojo.LongIdObject;

/**
 * 资源访问限制
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.security.restrict.Restriction")
public class RestrictionBean extends LongIdObject implements Restriction {
	private static final long serialVersionUID = -1157873272781525823L;

	/** 限制内容 */
	@NotNull
	@Size(max = 600)
	private String content;

	/** 限制实体 */
	@NotNull
	private RestrictEntity entity;

	/** 是否启用 */
	@NotNull
	protected boolean enabled = true;

	/** 备注说明 */
	private String remark;

	public RestrictionBean() {
		super();
	}

	public RestrictionBean(RestrictEntity entity, String content) {
		super();
		this.entity = entity;
		this.content = content;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public RestrictEntity getEntity() {
		return entity;
	}

	public void setEntity(RestrictEntity entity) {
		this.entity = entity;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	private static String evictComma(String str) {
		if (StringUtils.isEmpty(str)) return str;
		else {
			if (str.startsWith(",") && str.endsWith(",")) return str.substring(1, str.length() - 1);
			else if (str.startsWith(",")) {
				return str.substring(1);
			} else if (str.endsWith(",")) {
				return str.substring(0, str.length() - 1);
			} else {
				return str;
			}
		}
	}

}
