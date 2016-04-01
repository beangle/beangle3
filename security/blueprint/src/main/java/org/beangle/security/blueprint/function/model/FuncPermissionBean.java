/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.function.model;

import java.security.Principal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.TemporalAt;
import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.function.FuncPermission;
import org.beangle.security.blueprint.function.FuncResource;

/**
 * 系统授权实体
 * 角色和资源以及数据范围规定
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.security.blueprint.function.FuncPermission")
public class FuncPermissionBean extends IntegerIdObject implements TemporalAt, FuncPermission {

  private static final long serialVersionUID = -8956079356245507990L;

  /** 角色 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Role role;

  /** 功能资源 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected FuncResource resource;

  /** 授权的操作 */
  @Size(max = 100)
  protected String actions;

  /** 访问检查器 */
  @Size(max = 200)
  protected String restrictions;

  /** 生效时间 */
  protected Date beginAt;

  /** 失效时间 */
  protected Date endAt;

  /** 备注 */
  @Size(max = 100)
  protected String remark;

  public FuncPermissionBean() {
    super();
  }

  public FuncPermissionBean(Integer id) {
    super(id);
  }

  public FuncPermissionBean(Role role, FuncResource resource, String actions) {
    super();
    this.role = role;
    this.resource = resource;
    this.actions = actions;
  }

  public void setResource(FuncResourceBean resource) {
    this.resource = resource;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Object clone() {
    return new FuncPermissionBean(role, resource, actions);
  }

  public String getActions() {
    return actions;
  }

  public void setActions(String actions) {
    this.actions = actions;
  }

  public Date getBeginAt() {
    return beginAt;
  }

  public void setBeginAt(Date beginAt) {
    this.beginAt = beginAt;
  }

  public Date getEndAt() {
    return endAt;
  }

  public void setEndAt(Date endAt) {
    this.endAt = endAt;
  }

  public FuncResource getResource() {
    return resource;
  }

  public void setResource(FuncResource resource) {
    this.resource = resource;
  }

  public Principal getPrincipal() {
    return role;
  }

  public String getRestrictions() {
    return restrictions;
  }

  public void setRestrictions(String restrictions) {
    this.restrictions = restrictions;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
