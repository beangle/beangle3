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
package org.beangle.ems.avatar.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;

import org.beangle.commons.lang.Strings;
import org.beangle.ems.avatar.AvatarException;

public class FileAvatar extends AbstractAvatar {

  private File file;

  public FileAvatar(File file) {
    super();
    this.file = file;
    this.setUpdatedAt(new Date(file.lastModified()));
    setName(file.getName());
  }

  public FileAvatar() {
    super();
  }

  public long getSize() {
    return file.length();
  }

  public String getType() {
    if (null == super.getType()) {
      setType(Strings.substringAfterLast(file.getAbsolutePath(), "."));
    }
    return super.getType();
  }

  public InputStream getInputStream() throws AvatarException {
    try {
      return new FileInputStream(file);
    } catch (FileNotFoundException e) {
      throw new AvatarException(e);
    }
  }

  public File getFile() {
    return file;
  }

  public void setFile(File file) {
    this.file = file;
  }

}
