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
package org.beangle.ems.config.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.IntegerIdObject;

/**
 * 系统配置项
 *
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.config.model.PropertyConfigItemBean")
public class PropertyConfigItemBean extends IntegerIdObject {

  private static final long serialVersionUID = 1L;

  /** 名称 */
  @NotNull
  @Size(max = 100)
  private String name;

  /** 值 */
  @NotNull
  @Size(max = 1000)
  private String value;

  /** 描述 */
  @NotNull
  @Size(max = 1000)
  private String description;

  /** 类型 */
  @NotNull
  @Size(max = 200)
  private String type;

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

}
