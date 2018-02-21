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
package org.beangle.security.blueprint.service;

import java.util.List;

import org.beangle.security.blueprint.model.Dimension;
import org.beangle.security.blueprint.model.FuncResource;
import org.beangle.security.blueprint.model.Profile;
import org.beangle.security.blueprint.model.User;

public interface ProfileService {
  /**
   * Get field enumerated values.
   *
   * @param field
   * @param profile
   */
  Object getProperty(Profile profile, Dimension field);

  /**
   * 查找用户在指定资源上对应的数据配置
   *
   * @param user
   */
  List<Profile> getProfiles(User user, FuncResource resource);

  /**
   * Search field values
   *
   * @param field
   * @param keys
   */
  List<?> getDimensionValues(Dimension field, Object... keys);

  /**
   * Search field
   *
   * @param fieldName
   */
  Dimension getDimension(String fieldName);

  /**
   * find profile
   *
   * @param id
   * @return
   */
  Profile get(Long id);

}
