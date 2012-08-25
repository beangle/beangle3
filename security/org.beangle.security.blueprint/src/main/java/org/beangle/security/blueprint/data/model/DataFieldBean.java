/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.security.blueprint.data.DataField;
import org.beangle.security.blueprint.data.DataResource;
import org.beangle.security.blueprint.data.DataType;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * @author chaostone
 * @since 3.0.0
 */
@Entity(name = "org.beangle.security.blueprint.data.DataField")
@Cacheable
@Cache(region = "beangle", usage = CacheConcurrencyStrategy.READ_WRITE)
public class DataFieldBean extends LongIdObject implements DataField {

  private static final long serialVersionUID = -8782866706523521386L;

  /** 名称 */
  @NotNull
  @Size(max = 50)
  @Column(unique = true)
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
