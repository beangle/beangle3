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
package org.beangle.security.blueprint.function.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;
import org.beangle.security.blueprint.function.FuncResource;

/**
 * 系统功能资源
 * 代表一组系统功能点的集合.<br>
 * <p>
 * 系统模块之间存在基于代码表示上的父子级联关系.<br>
 * 上下级关系是通过模块代码的包含关系体现的。<br>
 * 系统模块作为权限分配的基本单位.
 * <p>
 * 
 * @author dell,chaostone 2005-9-26
 * @since 3.0.0
 */
@Entity(name = "org.beangle.security.blueprint.function.FuncResource")
@Cacheable
public class FuncResourceBean extends IntegerIdObject implements FuncResource {
  private static final long serialVersionUID = -8285208615351119572L;

  /** 模块名字 */
  @Size(max = 100)
  @NotNull
  @Column(unique = true)
  private String name;

  /** 模块标题 */
  @Size(max = 100)
  @NotNull
  private String title;

  /** 允许的操作 */
  @Size(max = 100)
  private String actions;

  /** 简单描述 */
  @Size(max = 100)
  private String remark;

  /** 资源访问范围 */
  @NotNull
  @Enumerated(value = EnumType.ORDINAL)
  private Scope scope = Scope.Private;

  /** 模块是否可用 */
  @NotNull
  private boolean enabled = true;

  /** 是否为入口 */
  @NotNull
  private boolean entry = true;

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isEnabled() {
    return enabled;
  }

  public void setEnabled(boolean enabled) {
    this.enabled = enabled;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDescription() {
    return Strings.concat(name, "[", title, "]");
  }

  public Scope getScope() {
    return scope;
  }

  public void setScope(Scope scope) {
    this.scope = scope;
  }

  public boolean isEntry() {
    return entry;
  }

  public void setEntry(boolean entry) {
    this.entry = entry;
  }

  public String getActions() {
    return actions;
  }

  public void setActions(String actions) {
    this.actions = actions;
  }

  public String toString() {
    return Objects.toStringBuilder(this).add("name", this.name).add("id", this.id).add("remark", this.remark)
        .toString();
  }
}
