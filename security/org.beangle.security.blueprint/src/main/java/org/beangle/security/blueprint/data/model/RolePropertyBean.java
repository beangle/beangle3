/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.security.blueprint.data.DataField;
import org.beangle.security.blueprint.data.RoleProfile;
import org.beangle.security.blueprint.data.RoleProperty;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * 角色属性
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.security.blueprint.data.RoleProperty")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.READ_WRITE)
public class RolePropertyBean extends LongIdObject implements RoleProperty {
  private static final long serialVersionUID = 1L;

  /** 值 */
  @Size(max = 1000)
  private String value;

  /** 属性元 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private DataField field;

  /** 角色属性配置 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private RoleProfile profile;

  public RolePropertyBean() {
    super();
  }

  public RolePropertyBean(RoleProfileBean profile, DataField field, String value) {
    super();
    this.profile = profile;
    this.field = field;
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public DataField getField() {
    return field;
  }

  public void setField(DataField field) {
    this.field = field;
  }

  public RoleProfile getProfile() {
    return profile;
  }

  public void setProfile(RoleProfile profile) {
    this.profile = profile;
  }

}
