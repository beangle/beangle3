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
package org.beangle.security.blueprint.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.security.blueprint.Field;
import org.beangle.security.blueprint.Property;
import org.beangle.security.blueprint.UserProfile;

/**
 * 数据限制域
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.security.blueprint.model.UserPropertyBean")
public class UserPropertyBean extends LongIdObject implements Property {
  private static final long serialVersionUID = 1L;

  /** 值 */
  @Size(max = 1000)
  private String value;

  /** 属性元 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private Field field;

  /** 用户属性配置 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private UserProfile profile;

  public UserPropertyBean() {
    super();
  }

  public UserPropertyBean(UserProfileBean profile, Field field, String value) {
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

  public Field getField() {
    return field;
  }

  public void setField(Field field) {
    this.field = field;
  }

  public UserProfile getProfile() {
    return profile;
  }

  public void setProfile(UserProfile profile) {
    this.profile = profile;
  }

}
