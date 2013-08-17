/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.security.blueprint.data.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.beangle.security.blueprint.data.DataField;
import org.beangle.security.blueprint.data.DataResource;
import org.beangle.security.blueprint.data.DataType;

/**
 * @author chaostone
 * @since 3.0.0
 */
@Entity(name = "org.beangle.security.blueprint.data.DataField")
@Cacheable
public class DataFieldBean extends IntegerIdObject implements DataField {

  private static final long serialVersionUID = -8782866706523521386L;

  /** 名称 */
  @NotNull
  @Size(max = 50)
  protected String name;

  /** 标题 */
  @NotNull
  @Size(max = 50)
  protected String title;

  /** 数据类型 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected DataType type;

  /** 数据资源 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  protected DataResource resource;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public DataType getType() {
    return type;
  }

  public void setType(DataType type) {
    this.type = type;
  }

  public DataResource getResource() {
    return resource;
  }

  public void setResource(DataResource resource) {
    this.resource = resource;
  }

}
