/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.security.blueprint.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.NumberIdHierarchyObject;
import org.beangle.security.blueprint.Member;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.User;

/**
 * 角色信息
 * 系统中角色的基本信息和账号信息.
 * 
 * @author dell,chaostone 2005-9-26
 */
@Entity(name = "org.beangle.security.blueprint.Role")
@Cacheable
public class RoleBean extends NumberIdHierarchyObject<Role, Integer> implements Role {

  private static final long serialVersionUID = -3404181949500894284L;

  /** 名称 */
  @Size(max = 100)
  @NotNull
  @Column(unique = true)
  private String name;

  /** 关联的用户 */
  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL)
  private Set<Member> members = CollectUtils.newHashSet();

  /** 创建人 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private User owner;

  /** 备注 */
  @Size(max = 100)
  protected String remark;

  /** 是否启用 */
  @NotNull
  protected boolean enabled = true;

  /** 动态组 */
  protected boolean dynamic = false;

  /** 创建时间 */
  protected Date createdAt;

  /** 最后修改时间 */
  protected Date updatedAt;

  public RoleBean() {
    super();
  }

  public RoleBean(Integer id) {
    setId(id);
  }

  public RoleBean(Integer id, String indexno, String name) {
    setId(id);
    this.indexno = indexno;
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public Set<Member> getMembers() {
    return members;
  }

  public void setMembers(Set<Member> members) {
    this.members = members;
  }

  public User getOwner() {
    return owner;
  }

  public void setOwner(User owner) {
    this.owner = owner;
  }

  public String toString() {
    return name;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public boolean isDynamic() {
    return dynamic;
  }

  public void setDynamic(boolean dynamic) {
    this.dynamic = dynamic;
  }

  public int compareTo(Role o) {
    return getIndexno().compareTo(o.getIndexno());
  }

}
