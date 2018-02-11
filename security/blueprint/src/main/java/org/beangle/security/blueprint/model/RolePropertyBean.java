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

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.beangle.commons.lang.Objects;
import org.beangle.security.blueprint.Dimension;
import org.beangle.security.blueprint.Property;
import org.beangle.security.blueprint.Role;

/**
 * 角色属性
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.security.blueprint.model.RolePropertyBean")
@Cacheable
@Table(name = "roles_properties")
public class RolePropertyBean extends IntegerIdObject implements Property {
  private static final long serialVersionUID = 1L;

  /** 值 */
  @Size(max = 1000)
  @NotNull
  private String value;

  /** 属性元 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Dimension dimension;

  /** 角色 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Role role;

  public RolePropertyBean() {
    super();
  }

  public RolePropertyBean(Role role, Dimension field, String value) {
    super();
    this.role = role;
    this.dimension = field;
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public Dimension getDimension() {
    return dimension;
  }

  public void setDimension(Dimension dimension) {
    this.dimension = dimension;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return Objects.toStringBuilder(this).add("field", dimension.getName()).add("value", value).toString();
  }
}
