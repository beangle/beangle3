/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.data.model;

import java.security.Principal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.Strings;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.data.ProfileField;
import org.beangle.security.blueprint.data.UserProfile;
import org.beangle.security.blueprint.data.UserProperty;

/**
 * 用户配置
 * 
 * @author chaostone
 * @version $Id: UserProfileBean.java Oct 21, 2011 8:39:05 AM chaostone $
 */
@Entity(name = "org.beangle.security.blueprint.data.UserProfile")
public class UserProfileBean extends LongIdObject implements UserProfile {

  private static final long serialVersionUID = -9047586316477373803L;

  /** 用户 */
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
  /**
   * 用户自定义属性
   */
  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true)
  protected List<UserProperty> properties = CollectUtils.newArrayList();

  public Principal getPrincipal() {
    return user;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<UserProperty> getProperties() {
    return properties;
  }

  public void setProperties(List<UserProperty> properties) {
    this.properties = properties;
  }

  public UserProperty getProperty(ProfileField meta) {
    if (null == properties || properties.isEmpty()) {
      return null;
    } else {
      for (UserProperty p : properties) {
        if (p.getField().equals(meta)) return p;
      }
    }
    return null;
  }

  public UserProperty getProperty(String name) {
    if (null == properties || properties.isEmpty()) {
      return null;
    } else {
      for (UserProperty p : properties) {
        if (p.getField().getName().equals(name)) return p;
      }
    }
    return null;
  }

  public void setProperty(ProfileField meta, String text) {
    UserProperty property = getProperty(meta);
    if (null == property) {
      property = new UserPropertyBean(this, meta, text);
      properties.add(property);
    } else {
      if (Strings.isEmpty(text)) properties.remove(property);
      else property.setValue(text);
    }
  }
}