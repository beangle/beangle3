/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.profile.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.orm.pojo.LongIdObject;
import org.beangle.security.blueprint.profile.PropertyMeta;

/**
 * 用户属性元信息
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.security.blueprint.profile.PropertyMeta")
public class PropertyMetaBean extends LongIdObject implements PropertyMeta {
  private static final long serialVersionUID = 1L;

  /** 名称 */
  @NotNull
  @Size(max = 50)
  @Column(unique = true)
  protected String name;

  /** 关键字名称 */
  @Size(max = 20)
  protected String keyName;

  /** 其它属性名(逗号隔开) */
  @Size(max = 100)
  protected String propertyNames;

  /** 类型 */
  @NotNull
  @Size(max = 100)
  protected String valueType;

  /** 备注 */
  @NotNull
  protected String remark;

  /** 数据提供描述 */
  @Size(max = 200)
  protected String source;

  /** 能够提供多值 */
  @NotNull
  protected boolean multiple;

  /** 是否必填项 */
  protected boolean required;

  public PropertyMetaBean() {
    super();
  }

  public PropertyMetaBean(Long id) {
    super(id);
  }

  public PropertyMetaBean(Long id, String name, String type, String source) {
    super(id);
    this.name = name;
    this.valueType = type;
    this.source = source;
    this.multiple = true;
  }

  public String getKeyName() {
    return keyName;
  }

  public void setKeyName(String keyName) {
    this.keyName = keyName;
  }

  public String getPropertyNames() {
    return propertyNames;
  }

  public void setPropertyNames(String propertyNames) {
    this.propertyNames = propertyNames;
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

  public String getValueType() {
    return valueType;
  }

  public void setValueType(String valueType) {
    this.valueType = valueType;
  }

  public boolean isRequired() {
    return required;
  }

  public void setRequired(boolean required) {
    this.required = required;
  }

}
