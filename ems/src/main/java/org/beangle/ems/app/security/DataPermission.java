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
package org.beangle.ems.app.security;

import java.io.Serializable;

import org.beangle.security.data.Permission;

/**
 * 数据授权实体
 *
 * @author chaostone
 * @since 3.0.0
 */
public class DataPermission implements Permission, Serializable {

  private static final long serialVersionUID = -8956079356245507990L;

  /** 授权的操作 */
  protected String actions;

  /** 资源过滤器 */
  protected String filters;

  /** 访问满足的检查(入口\人员等) */
  protected String restrictions;

  /** 能够访问哪些属性 */
  protected String attrs;

  public DataPermission() {
    super();
  }

  public String getActions() {
    return actions;
  }

  public void setActions(String actions) {
    this.actions = actions;
  }

  public String getFilters() {
    return filters;
  }

  public void setFilters(String filters) {
    this.filters = filters;
  }

  public String getRestrictions() {
    return restrictions;
  }

  public void setRestrictions(String restrictions) {
    this.restrictions = restrictions;
  }

  public String getAttrs() {
    return attrs;
  }

  public void setAttrs(String attrs) {
    this.attrs = attrs;
  }

}
