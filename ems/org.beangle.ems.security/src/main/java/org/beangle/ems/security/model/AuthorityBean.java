/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.ems.security.Authority;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.restrict.AuthorityRestriction;
import org.beangle.ems.security.restrict.RestrictionHolder;

/**
 * 系统权限实体
 * 模块及其操作的数据范围规定
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.ems.security.Authority")
public class AuthorityBean extends LongIdObject implements RestrictionHolder<AuthorityRestriction>, Authority {

	private static final long serialVersionUID = -8956079356245507990L;

	/** 用户组 */
	@NotNull
	protected Group group;

	/** 权限实体中的模块 */
	@NotNull
	protected Resource resource;

	/** 该模块对应的数据操作范围 */
	@OneToMany(mappedBy = "holder", cascade = CascadeType.ALL)
	protected Set<AuthorityRestriction> restrictions;

	public AuthorityBean() {
		super();
	}

	public AuthorityBean(Long id) {
		super(id);
	}

	public AuthorityBean(Group group, Resource resource) {
		super();
		this.group = group;
		this.resource = resource;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = (Resource) resource;
	}

	public Set<AuthorityRestriction> getRestrictions() {
		return restrictions;
	}

	public void setRestrictions(Set<AuthorityRestriction> restrictions) {
		this.restrictions = restrictions;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Object clone() {
		AuthorityBean groupAuthority = new AuthorityBean();
		groupAuthority.setResource(resource);
		groupAuthority.setGroup(group);
		return groupAuthority;
	}

	public void merge(Authority other) {
		// TODO Auto-generated method stub
	}
}
