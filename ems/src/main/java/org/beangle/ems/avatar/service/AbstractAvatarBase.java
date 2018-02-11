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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.avatar.Avatar;
import org.beangle.ems.util.ZipUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractAvatarBase implements AvatarBase {

  private final Logger logger = LoggerFactory.getLogger(AbstractAvatarBase.class);
  // 支持的照片类型
  protected Set<String> types = CollectUtils.newHashSet();

  protected List<String> typeList = new ArrayList<String>();

  protected boolean readOnly = false;

  protected static final String DEFAULT_AVATAR = "default.jpg";

  {
    for (String name : new String[] { "jpg", "JPG", "jpeg", "png", "gif", "GIF" }) {
      types.add(name);
      typeList.add(name);
    }
  }

  public boolean containType(String type) {
    return types.contains(type);
  }

  public Set<String> getTypes() {
    return types;
  }

  public void setTypes(Set<String> types) {
    this.types = types;
  }

  public Avatar getDefaultAvatar() {
    return getAvatar(DEFAULT_AVATAR);
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  public int updateAvatarBatch(File zipFile) {
    String tmpPath = System.getProperty("java.io.tmpdir") + "/avatar/";
    logger.debug("unzip avatar to {}", tmpPath);
    ZipUtils.unzip(zipFile, tmpPath);
    File tmpAvatar = new File(tmpPath);
    int count = updateFile(tmpAvatar);
    logger.debug("removing avatar tmp path {}", tmpPath);
    tmpAvatar.delete();
    return count;
  }

  private int updateFile(File path) {
    int count = 0;
    if (path.isDirectory()) {
      String[] fileNames = path.list();
      for (String fileName : fileNames) {
        File file = new File(path.getAbsolutePath() + "/" + fileName);
        if (file.isDirectory()) {
          count += updateFile(file);
          file.delete();
        } else {
          String type = Strings.substringAfter(fileName, ".");
          boolean passed = containType(type);
          if (passed) {
            logger.debug("updating avatar by {}", file.getName());
            updateAvatar(Strings.substringBefore(fileName, "."), file, type);
            count++;
          }
          file.delete();
        }
      }
    }
    return count;
  }

  public abstract boolean updateAvatar(String name, File file, String type);

}
