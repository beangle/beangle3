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
package org.beangle.ems.meta.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.beangle.ems.meta.EntityMeta;
import org.beangle.ems.meta.PropertyMeta;

/**
 * 属性元数据实现
 */
@Entity(name = "org.beangle.ems.meta.PropertyMeta")
public class PropertyMetaBean extends IntegerIdObject implements PropertyMeta {
  private static final long serialVersionUID = 8581246709461219082L;

  /** 所属元数据 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private EntityMeta meta;
  /** 属性名称 */
  @NotNull
  private String name;
  /** 类型 */
  @NotNull
  private String type;
  /** 属性说明 */
  @Size(max = 40)
  private String comments;
  /** 备注 */
  @Size(max = 100)
  private String remark;

  public PropertyMetaBean() {
  }

  public PropertyMetaBean(Integer id) {
    this.id = id;
  }

  public EntityMeta getMeta() {
    return meta;
  }

  public void setMeta(EntityMeta meta) {
    this.meta = meta;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }

  public String getRemark() {
    return remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

}
