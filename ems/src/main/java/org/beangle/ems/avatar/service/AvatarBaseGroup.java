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
package org.beangle.ems.avatar.service;

import java.io.File;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.collection.page.Pages;
import org.beangle.ems.avatar.Avatar;

/**
 * 组合头像库
 *
 * @author chaostone
 */
public class AvatarBaseGroup implements AvatarBase {

  private List<AvatarBase> bases = CollectUtils.newArrayList();

  public boolean containType(String type) {
    boolean contains = false;
    for (AvatarBase base : bases) {
      if (base.containType(type)) {
        contains = true;
        break;
      }
    }
    return contains;
  }

  public Avatar getAvatar(String name) {
    Avatar avatar = null;
    for (AvatarBase base : bases) {
      avatar = base.getAvatar(name);
      if (null != avatar) {
        break;
      }
    }
    return avatar;
  }

  public Page<String> getAvatarNames(PageLimit pageLimit) {
    return Pages.emptyPage();
  }

  public Avatar getDefaultAvatar() {
    return null;
  }

  public String getDescription() {
    return "CompositeAvatarBase";
  }

  public Set<String> getTypes() {
    return null;
  }

  public boolean updateAvatar(String name, File file, String type) {
    return false;
  }

  public int updateAvatarBatch(File zipFile) {
    return 0;
  }

  public boolean isReadOnly() {
    return true;
  }

}
