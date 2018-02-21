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

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.pojo.LongIdObject;
import org.beangle.commons.lang.Objects;

/**
 * 用户配置
 *
 * @author chaostone
 */
public class UserProfile extends LongIdObject implements Profile {

  private static final long serialVersionUID = -9047586316477373803L;

  /** 用户 */
  private User user;
  /**
   * 用户自定义属性
   */
  protected Map<Dimension, String> properties = CollectUtils.newHashMap();

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Map<Dimension, String> getProperties() {
    return properties;
  }

  public void setProperties(Map<Dimension, String> properties) {
    this.properties = properties;
  }

  public String getProperty(Dimension meta) {
    return properties.get(meta);
  }

  public String getProperty(String name) {
    for (Map.Entry<Dimension, String> p : properties.entrySet())
      if (p.getKey().getName().equals(name)) return p.getValue();
    return null;
  }

  @Override
  public String toString() {
    return Objects.toStringBuilder(this).add("user", user).add("properties", properties).toString();
  }

}
