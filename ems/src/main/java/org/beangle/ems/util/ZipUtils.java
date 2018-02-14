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
package org.beangle.ems.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.List;

import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.io.IOs;
import org.beangle.commons.lang.Strings;

/**
 * <p>
 * ZipUtils class.
 * </p>
 *
 * @author chaostone
 * @version $Id: $
 */
public final class ZipUtils {
  /**
   * Don't let anyone instantiate this class.
   */
  private ZipUtils() {
  }

  /**
   * <p>
   * zip.
   * </p>
   *
   * @param fileNames a {@link java.util.List} object.
   * @param zipPath a {@link java.lang.String} object.
   * @return a {@link java.io.File} object.
   */
  public static File zip(List<String> fileNames, String zipPath) {
    return zip(fileNames, zipPath, null);
  }

  // 文件压缩
  /**
   * <p>
   * zip.
   * </p>
   *
   * @param fileNames a {@link java.util.List} object.
   * @param zipPath a {@link java.lang.String} object.
   * @param encoding a {@link java.lang.String} object.
   * @return a {@link java.io.File} object.
   */
  public static File zip(List<String> fileNames, String zipPath, String encoding) {
    try {
      FileOutputStream f = new FileOutputStream(zipPath);
      ZipArchiveOutputStream zos = (ZipArchiveOutputStream) new ArchiveStreamFactory()
          .createArchiveOutputStream(ArchiveStreamFactory.ZIP, f);
      if (null != encoding) {
        zos.setEncoding(encoding);
      }
      for (int i = 0; i < fileNames.size(); i++) {
        String fileName = fileNames.get(i);
        String entryName = Strings.substringAfterLast(fileName, File.separator);
        ZipArchiveEntry entry = new ZipArchiveEntry(entryName);
        zos.putArchiveEntry(entry);
        FileInputStream fis = new FileInputStream(fileName);
        IOs.copy(fis, zos);
        fis.close();
        zos.closeArchiveEntry();
      }
      zos.close();
      return new File(zipPath);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * <p>
   * unzip.
   * </p>
   *
   * @param zipFile a {@link java.io.File} object.
   * @param destination a {@link java.lang.String} object.
   * @return a {@link java.util.List} object.
   */
  public static List<String> unzip(final File zipFile, final String destination) {
    return unzip(zipFile, destination, null);
  }

  /**
   * <p>
   * unzip.
   * </p>
   *
   * @param zipFile a {@link java.io.File} object.
   * @param destination a {@link java.lang.String} object.
   * @param encoding a {@link java.lang.String} object.
   * @return a {@link java.util.List} object.
   */
  public static List<String> unzip(final File zipFile, final String destination, String encoding) {
    List<String> fileNames = CollectUtils.newArrayList();
    String dest = destination;
    if (!destination.endsWith(File.separator)) {
      dest = destination + File.separator;
    }
    ZipFile file;
    try {
      file = null;
      if (null == encoding) file = new ZipFile(zipFile);
      else file = new ZipFile(zipFile, encoding);
      Enumeration<ZipArchiveEntry> en = file.getEntries();
      ZipArchiveEntry ze = null;
      while (en.hasMoreElements()) {
        ze = en.nextElement();
        File f = new File(dest, ze.getName());
        if (ze.isDirectory()) {
          f.mkdirs();
          continue;
        } else {
          f.getParentFile().mkdirs();
          InputStream is = file.getInputStream(ze);
          OutputStream os = new FileOutputStream(f);
          IOs.copy(is, os);
          is.close();
          os.close();
          fileNames.add(f.getAbsolutePath());
        }
      }
      file.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
    return fileNames;
  }

  /**
   * <p>
   * isZipFile.
   * </p>
   *
   * @param zipFile a {@link java.io.File} object.
   * @return a boolean.
   */
  public static boolean isZipFile(File zipFile) {
    try {
      ZipFile zf = new ZipFile(zipFile);
      boolean isZip = zf.getEntries().hasMoreElements();
      zf.close();
      return isZip;
    } catch (IOException e) {
      return false;
    }
  }
}
