/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.model;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.NumberIdHierarchyObject;

/**
 * 角色信息
 * 系统中角色的基本信息和账号信息.
 *
 * @author dell,chaostone 2005-9-26
 */
public class Role extends NumberIdHierarchyObject<Role, Integer>
    implements Comparable<Role>, Principal, Profile {
  private static final long serialVersionUID = -3404181949500894284L;

  /** 匿名角色id */
  static final long ANONYMOUS_ID = 1;

  /** 所有用户所在的公共角色id */
  static final long ANYONE_ID = 2;
  /** 名称 */
  private String name;

  /** 关联的用户 */
  private Set<RoleMember> members = CollectUtils.newHashSet();

  /** 创建人 */
  private User creator;

  /** 角色属性 */
  protected Map<Dimension, String> properties = CollectUtils.newHashMap();

  /** 备注 */
  protected String remark;

  /** 是否启用 */
  protected boolean enabled = true;

  public Role() {
    super();
  }

  public Role(Integer id) {
    setId(id);
  }

  public Role(Integer id, String indexno, String name) {
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

  public Set<RoleMember> getMembers() {
    return members;
  }

  public void setMembers(Set<RoleMember> members) {
    this.members = members;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public String toString() {
    return name;
  }

  public Map<Dimension, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<Dimension, String> properties) {
    this.properties = properties;
  }

  public String getProperty(Dimension meta) {
    return properties.get(meta);
  }

  public String getProperty(String name) {
    if (null == properties || properties.isEmpty()) {
      return null;
    } else {
      for (Map.Entry<Dimension, String> p : properties.entrySet())
        if (p.getKey().getName().equals(name)) return p.getValue();
    }
    return null;
  }

  public void setProperty(Dimension meta, String text) {
    properties.put(meta, text);
  }

  public int compareTo(Role o) {
    return getIndexno().compareTo(o.getIndexno());
  }

}
