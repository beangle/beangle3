/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.function;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.dao.entity.TemporalActiveEntity;
import org.beangle.commons.orm.pojo.LongIdObject;
import org.beangle.security.blueprint.Permission;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.Role;

/**
 * 系统授权实体
 * 角色和资源以及数据范围规定
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.security.blueprint.Permission")
public class FuncPermissionBean extends LongIdObject implements Permission, TemporalActiveEntity {

  private static final long serialVersionUID = -8956079356245507990L;

  /** 角色 */
  @NotNull
  @ManyToOne
  protected Role role;

  /** 权限实体中的模块 */
  @NotNull
  @ManyToOne
  protected FuncResourceBean resource;

  @Size(max = 100)
  /** 授权的操作 */
  protected String actions;

  /** 资源过滤器 */
  protected String filters;

  /** 访问检查器 */
  protected String guards;

  /** 允许访问的方法 */
  protected String parts;

  /** 生效时间 */
  protected Date effectiveAt;

  /** 失效时间 */
  protected Date invalidAt;

  public FuncPermissionBean() {
    super();
  }

  public FuncPermissionBean(Long id) {
    super(id);
  }

  public FuncPermissionBean(Role role, FuncResourceBean resource, String actions) {
    super();
    this.role = role;
    this.resource = resource;
    this.actions = actions;
  }

  public Resource getResource() {
    return resource;
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

  public void merge(Permission other) {
    // TODO Auto-generated method stub
  }
}
