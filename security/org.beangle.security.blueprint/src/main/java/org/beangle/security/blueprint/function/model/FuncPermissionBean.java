/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.function.model;

import java.security.Principal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.TemporalActiveEntity;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.function.FuncPermission;
import org.beangle.security.blueprint.function.FuncResource;

/**
 * 系统授权实体
 * 角色和资源以及数据范围规定
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.security.blueprint.function.model.FuncPermissionBean")
public class FuncPermissionBean extends LongIdObject implements TemporalActiveEntity,
    FuncPermission {

  private static final long serialVersionUID = -8956079356245507990L;

  /** 角色 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected Role role;

  /** 功能资源 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected FuncResource resource;

  @Size(max = 100)
  /** 授权的操作 */
  protected String actions;

  @Size(max=200)
  /** 访问检查器 */
  protected String restrictions;

  /** 生效时间 */
  protected Date effectiveAt;

  /** 失效时间 */
  protected Date invalidAt;

  /**备注*/
  @Size(max = 100)
  protected String remark;
  
  public FuncPermissionBean() {
    super();
  }

  public FuncPermissionBean(Long id) {
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

  public Date getEffectiveAt() {
    return effectiveAt;
  }

  public void setEffectiveAt(Date effectiveAt) {
    this.effectiveAt = effectiveAt;
  }

  public Date getInvalidAt() {
    return invalidAt;
  }

  public void setInvalidAt(Date invalidAt) {
    this.invalidAt = invalidAt;
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
