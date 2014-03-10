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
package org.beangle.security.blueprint.nav.model;

import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.nav.Menu;
import org.beangle.security.blueprint.nav.MenuProfile;

/**
 * 菜单配置
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.security.blueprint.nav.MenuProfile")
@Cacheable
public class MenuProfileBean extends IntegerIdObject implements MenuProfile {

  private static final long serialVersionUID = 9147563981118270960L;

  /** 菜单配置名称 */
  @NotNull
  @Size(max = 50)
  @Column(unique = true)
  private String name;

  /** 菜单列表 */
  @OneToMany(mappedBy = "profile")
  private List<Menu> menus = CollectUtils.newArrayList();

  /** 角色 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Role role;

  /** 是否启用 */
  @NotNull
  private boolean enabled;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<Menu> getMenus() {
    return menus;
  }

  public void setMenus(List<Menu> menus) {
    this.menus = menus;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

}
