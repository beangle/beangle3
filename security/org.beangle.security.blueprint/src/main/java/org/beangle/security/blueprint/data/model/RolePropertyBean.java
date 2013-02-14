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
import org.beangle.security.blueprint.data.ProfileField;
import org.beangle.security.blueprint.data.RoleProfile;
import org.beangle.security.blueprint.data.RoleProperty;

/**
 * 角色属性
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.security.blueprint.data.RoleProperty")
@Cacheable
public class RolePropertyBean extends IntegerIdObject implements RoleProperty {
  private static final long serialVersionUID = 1L;

  /** 值 */
  @Size(max = 1000)
  private String value;

  /** 属性元 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ProfileField field;

  /** 角色属性配置 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private RoleProfile profile;

  public RolePropertyBean() {
    super();
  }

  public RolePropertyBean(RoleProfileBean profile, ProfileField field, String value) {
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

  public ProfileField getField() {
    return field;
  }

  public void setField(ProfileField field) {
    this.field = field;
  }

  public RoleProfile getProfile() {
    return profile;
  }

  public void setProfile(RoleProfile profile) {
    this.profile = profile;
  }

}
