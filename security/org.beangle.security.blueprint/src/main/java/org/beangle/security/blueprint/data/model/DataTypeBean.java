/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
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
public class DataTypeBean extends LongIdObject implements DataType {

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

  public DataTypeBean(Long id) {
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
