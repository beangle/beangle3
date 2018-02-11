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
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.collection.page.PagedList;
import org.beangle.commons.collection.page.Pages;
import org.beangle.commons.config.property.PropertyConfig;
import org.beangle.commons.config.property.PropertyConfigEvent;
import org.beangle.commons.config.property.PropertyConfigListener;
import org.beangle.commons.io.Files;
import org.beangle.commons.lang.Strings;
import org.beangle.ems.avatar.Avatar;
import org.beangle.ems.avatar.model.FileAvatar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 基于文件系统的照片库
 *
 * @author chaostone
 */
public class FileSystemAvatarBase extends AbstractAvatarBase implements PropertyConfigListener {

  private static final Logger logger = LoggerFactory.getLogger(FileSystemAvatarBase.class);
  // 照片路径
  private String avatarDir;

  public Page<String> getAvatarNames(PageLimit limit) {
    if (null == avatarDir) {
      logger.error("avatar dir property not config properly");
      return Pages.emptyPage();
    }
    File file = new File(avatarDir);
    if (!file.exists()) { return Pages.emptyPage(); }
    String[] names = file.list();
    List<String> fileNames = CollectUtils.newArrayList();
    for (int i = 0; i < names.length; i++) {
      String name = Strings.substringBefore(names[i], ".");
      String ext = Strings.substringAfter(names[i], ".");
      if (Strings.isNotBlank(name) && containType(ext)) {
        fileNames.add(name);
      }
    }
    Collections.sort(fileNames);
    return new PagedList<String>(fileNames, limit);
  }

  /**
   * 根据名称和类型得到文件绝对路径
   *
   * @param name
   * @param type
   */
  public String getAbsoluteAvatarPath(String name, String type) {
    StringBuilder sb = new StringBuilder(avatarDir);
    sb.append(name).append('.').append(type.toLowerCase());
    return sb.toString();
  }

  public Avatar getAvatar(String name) {
    if (null == avatarDir) return null;
    if (Strings.contains(name, '.')) {
      File file = new File(avatarDir + name);
      if (file.exists()) { return new FileAvatar(file); }
    } else {
      for (int i = 0; i < typeList.size(); i++) {
        StringBuilder sb = new StringBuilder(avatarDir);
        sb.append(name).append('.').append(typeList.get(i));
        File file = new File(sb.toString());
        if (file.exists()) { return new FileAvatar(file); }
      }
    }
    return null;
  }

  public String getAvatarDir() {
    return avatarDir;
  }

  public void setAvatarDir(String avatarDir) {
    if (null != avatarDir) {
      if (!avatarDir.endsWith(File.separator)) {
        this.avatarDir = avatarDir + File.separator;
      } else {
        this.avatarDir = avatarDir;
      }
    } else {
      this.avatarDir = null;
    }
  }

  public String getDescription() {
    return "路径:" + avatarDir + " <br><em>如需改变该位置,请配置系统参数:fileSystemAvatarBase.avatarDir.</em>";
  }

  public void setPropertyConfig(PropertyConfig config) {
    if (null != config) {
      config.addListener(this);
      setAvatarDir(config.get(String.class, "fileSystemAvatarBase.avatarDir"));
    }
  }

  public void onConfigEvent(PropertyConfigEvent event) {
    setAvatarDir(event.getSource().get(String.class, "fileSystemAvatarBase.avatarDir"));
  }

  public boolean updateAvatar(String name, File avatar, String type) {
    try {
      Files.copyFile(avatar, new File(getAbsoluteAvatarPath(name, type)));
    } catch (IOException e) {
      logger.error("copy avator error", e);
      return false;
    }
    return true;
  }

}
