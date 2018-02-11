/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;
import org.beangle.security.blueprint.Dimension;
import org.beangle.security.blueprint.Profile;
import org.beangle.security.blueprint.Property;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.UserProfile;

/**
 * 用户配置
 *
 * @author chaostone
 * @version $Id: UserProfileBean.java Oct 21, 2011 8:39:05 AM chaostone $
 */
@Entity(name = "org.beangle.security.blueprint.UserProfile")
public class UserProfileBean extends LongIdObject implements UserProfile {

  private static final long serialVersionUID = -9047586316477373803L;

  /** 用户 */
  @ManyToOne(fetch = FetchType.LAZY)
  private User user;
  /**
   * 用户自定义属性
   */
  @OneToMany(mappedBy = "profile", cascade = CascadeType.ALL, orphanRemoval = true, targetEntity = UserPropertyBean.class)
  protected List<Property> properties = CollectUtils.newArrayList();

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public List<Property> getProperties() {
    return properties;
  }

  public void setProperties(List<Property> properties) {
    this.properties = properties;
  }

  public Property getProperty(Dimension meta) {
    if (null == properties || properties.isEmpty()) {
      return null;
    } else {
      for (Property p : properties) {
        if (p.getDimension().equals(meta)) return p;
      }
    }
    return null;
  }

  public Property getProperty(String name) {
    if (null == properties || properties.isEmpty()) {
      return null;
    } else {
      for (Property p : properties) {
        if (p.getDimension().getName().equals(name)) return p;
      }
    }
    return null;
  }

  @Override
  public boolean matches(Profile other) {
    boolean matched = true;
    if (!other.getProperties().isEmpty()) {
      for (Property property : other.getProperties()) {
        String target = property.getValue();
        Property op = this.getProperty(property.getDimension());
        String source = "";
        if (null != op) source = op.getValue();
        matched = source.equals(Property.AllValue);
        if (!matched) {
          if (!target.equals(Property.AllValue)) {
            matched = CollectUtils.newHashSet(Strings.split(source, ",")).containsAll(
                CollectUtils.newHashSet(Strings.split(target, ",")));
          }
        }
        if (!matched) break;
      }
    }
    return matched;
  }

  public void setProperty(Dimension meta, String text) {
    Property property = getProperty(meta);
    if (Strings.isNotBlank(text)) {
      if (null == property) {
        property = new UserPropertyBean(this, meta, text);
        properties.add(property);
      } else property.setValue(text);
    } else {
      if (null != property) properties.remove(property);
    }
  }

  @Override
  public String toString() {
    return Objects.toStringBuilder(this).add("user", user).add("properties", properties).toString();
  }

}
