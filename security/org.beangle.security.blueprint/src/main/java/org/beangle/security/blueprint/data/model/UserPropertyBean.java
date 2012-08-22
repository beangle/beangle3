/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.security.blueprint.data.ProfileField;
import org.beangle.security.blueprint.data.UserProfile;
import org.beangle.security.blueprint.data.UserProperty;

/**
 * 数据限制域
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.security.blueprint.data.UserProperty")
public class UserPropertyBean extends LongIdObject implements UserProperty {
  private static final long serialVersionUID = 1L;

  /** 值 */
  @Size(max = 1000)
  private String value;

  /** 属性元 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private ProfileField field;

  /** 用户属性配置 */
  @NotNull
  @ManyToOne(fetch = FetchType.LAZY)
  private UserProfile profile;

  public UserPropertyBean() {
    super();
  }

  public UserPropertyBean(UserProfileBean profile, ProfileField field, String value) {
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

  public UserProfile getProfile() {
    return profile;
  }

  public void setProfile(UserProfile profile) {
    this.profile = profile;
  }

}
