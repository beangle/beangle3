/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import org.beangle.dao.pojo.LongIdObject;
import org.beangle.ems.security.Permission;
import org.beangle.ems.security.Role;
import org.beangle.ems.security.Resource;

/**
 * 系统授权实体
 * 角色和资源以及数据范围规定
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.ems.security.Permission")
public class PermissionBean extends LongIdObject implements Permission {

	private static final long serialVersionUID = -8956079356245507990L;

	/** 角色 */
	@NotNull
	@ManyToOne
	protected Role role;

	/** 权限实体中的模块 */
	@NotNull
	@ManyToOne
	protected Resource resource;

	public PermissionBean() {
		super();
	}

	public PermissionBean(Long id) {
		super(id);
	}

	public PermissionBean(Role role, Resource resource) {
		super();
		this.role = role;
		this.resource = resource;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = (Resource) resource;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Object clone() {
		return new PermissionBean(role, resource);
	}

	public void merge(Permission other) {
		// TODO Auto-generated method stub
	}
}
