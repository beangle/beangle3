/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang.StringUtils;
import org.beangle.dao.pojo.LongIdObject;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.ems.security.restrict.RestrictEntity;
import org.beangle.ems.security.restrict.Restriction;

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
	@ManyToOne
	private RestrictEntity entity;

	/** 适用资源(可用正则表达式) */
	@Size(max = 200)
	@Column(name="resrc")
	private String resource;

	/** 适用用户组 */
	@ManyToOne
	private Group group;

	/** 适用用户 */
	@ManyToOne
	private User user;

	/** 是否启用 */
	@NotNull
	protected boolean enabled = true;

	/** 备注说明 */
	@Size(max = 200)
	private String remark;

	public RestrictionBean() {
		super();
	}

	public RestrictionBean(RestrictEntity entity, String content) {
		super();
		this.entity = entity;
		this.content = content;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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
