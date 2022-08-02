/*
 * OpenURP, Agile University Resource Planning Solution.
 *
 * Copyright © 2014, The OpenURP Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.app.security;

import org.beangle.commons.entity.pojo.IntegerIdObject;

/**
 * 属性元信息
 *
 * @author chaostone
 * @since 3.0.0
 */
public class Dimension extends IntegerIdObject {
  private static final long serialVersionUID = 1L;

  /** 名称 */
  protected String name;

  /** 标题 */
  protected String title;

  /** 关键字名称 */
  protected String keyName;

  /** 其它属性名(逗号隔开) */
  protected String properties;

  /** 类型 */
  protected String typeName;

  /** 数据提供描述 */
  protected String source;

  /** 能够提供多值 */
  protected boolean multiple;

  /** 是否必填项 */
  protected boolean required;

  public Dimension() {
    super();
  }

  public Dimension(Integer id) {
    super(id);
  }

  public Dimension(Integer id, String name, String typeName, String source) {
    super(id);
    this.name = name;
    this.typeName = typeName;
    this.source = source;
    this.multiple = true;
  }

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

  public String getSource() {
    return source;
  }

  public void setSource(String source) {
    this.source = source;
  }

  public boolean isMultiple() {
    return multiple;
  }

  public void setMultiple(boolean multiple) {
    this.multiple = multiple;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

  public String getTypeName() {
    return typeName;
  }

  public void setTypeName(String typeName) {
    this.typeName = typeName;
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

}
