/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
import java.util.List;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.NumberIdHierarchyObject;
import org.beangle.commons.lang.Strings;
import org.beangle.security.blueprint.Field;
import org.beangle.security.blueprint.Member;
import org.beangle.security.blueprint.Profile;
import org.beangle.security.blueprint.Property;
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

  /** 角色属性 */
  @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = RolePropertyBean.class)
  protected List<Property> properties = CollectUtils.newArrayList();

  /** 备注 */
  @Size(max = 100)
  protected String remark;

  /** 是否启用 */
  @NotNull
  protected boolean enabled = true;

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

  public List<Property> getProperties() {
    return properties;
  }

  public void setProperties(List<Property> properties) {
    this.properties = properties;
  }

  public Property getProperty(Field meta) {
    if (null == properties || properties.isEmpty()) {
      return null;
    } else {
      for (Property p : properties)
        if (p.getField().equals(meta)) return p;
    }
    return null;
  }

  public Property getProperty(String name) {
    if (null == properties || properties.isEmpty()) {
      return null;
    } else {
      for (Property p : properties)
        if (p.getField().getName().equals(name)) return p;
    }
    return null;
  }

  public void setProperty(Field meta, String text) {
    Property property = getProperty(meta);
    if (Strings.isNotBlank(text)) {
      if (null == property) {
        property = new RolePropertyBean(this, meta, text);
        properties.add(property);
      } else property.setValue(text);
    } else {
      if (null != property) properties.remove(property);
    }
  }

  @Override
  public boolean matches(Profile other) {
    boolean matched = true;
    if (!other.getProperties().isEmpty()) {
      for (Property property : other.getProperties()) {
        String target = property.getValue();
        Property op = this.getProperty(property.getField());
        String source = "";
        if (null != op) source = op.getValue();
        if (target.equals(Property.AllValue)) {
          matched = source.equals(Property.AllValue);
        } else {
          matched = CollectUtils.newHashSet(Strings.split(source, ",")).contains(
              CollectUtils.newHashSet(Strings.split(target, ",")));
        }
        if (!matched) break;
      }
    }
    return matched;
  }

  public int compareTo(Role o) {
    return getIndexno().compareTo(o.getIndexno());
  }

}
