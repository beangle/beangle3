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
package org.beangle.security.blueprint.data.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.IntegerIdObject;
import org.beangle.security.blueprint.data.DataType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author chaostone
 * @since 3.0.0
 */
@Entity(name = "org.beangle.security.blueprint.data.DataType")
@Cacheable
@Cache(region = "beangle", usage = CacheConcurrencyStrategy.READ_WRITE)
public class DataTypeBean extends IntegerIdObject implements DataType {

  private static final long serialVersionUID = 1440238057448114481L;

  /**名称 */
  @Size(max = 50)
  protected String name;

  /** 关键字名称 */
  @Size(max = 20)
  protected String keyName;

  /** 其它属性名(逗号隔开) */
  @Size(max = 100)
  protected String properties;

  /** 类型 */
  @NotNull
  @Size(max = 100)
  protected String typeName;

  public DataTypeBean() {
    super();
  }

  public DataTypeBean(Integer id) {
    super(id);
  }

  public DataTypeBean(String name, String typeName) {
    super();
    this.name = name;
    this.typeName = typeName;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getKeyName() {
    return keyName;
  }

  public void setKeyName(String keyName) {
    this.keyName = keyName;
  }

  public String getProperties() {
    return properties;
  }

  public void setProperties(String properties) {
    this.properties = properties;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
  }

}
