/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.web.io;

import static org.beangle.commons.web.util.RequestUtils.encodeAttachName;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.http.mime.MimeTypeProvider;
import org.beangle.commons.io.IOs;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Default Stream Downloader
 * 
 * @author chaostone
 * @since 2.4
 */
public class DefaultStreamDownloader implements Initializing, StreamDownloader {

  protected Logger logger = LoggerFactory.getLogger(getClass());

  protected MimeTypeProvider mimeTypeProvider;

  public void init() throws Exception {
    Assert.notNull(mimeTypeProvider, "mimeTypeProvider must be set");
  }

  public DefaultStreamDownloader() {
    super();
  }

  public DefaultStreamDownloader(MimeTypeProvider mimeTypeProvider) {
    super();
    this.mimeTypeProvider = mimeTypeProvider;
  }

  public void download(HttpServletRequest request, HttpServletResponse response, File file) {
    download(request, response, file, file.getName());
  }

  public void download(HttpServletRequest request, HttpServletResponse response, URL url, String display) {
    try {
      download(request, response, url.openStream(), url.getFile(), display);
    } catch (Exception e) {
      logger.warn("download file error=" + display, e);
    }
  }

  public void download(HttpServletRequest request, HttpServletResponse response, File file, String display) {
    Assert.notNull(file, "file shouldn't be null");
    Assert.isTrue(file.exists(), "file should exists");
    try {
      download(request, response, new FileInputStream(file), file.getAbsolutePath(), display);
    } catch (Exception e) {
      logger.warn("download file error=" + display, e);
    }
  }

  protected void addContent(HttpServletRequest request, HttpServletResponse response, String attach) {
    String contentType = response.getContentType();
    if (null == contentType) {
      contentType = mimeTypeProvider.getMimeType(Strings.substringAfterLast(attach, "."),
          "application/x-msdownload");
      response.setContentType(contentType);
      logger.debug("set content type {} for {}", contentType, attach);
    }
    String encodeName = encodeAttachName(request, attach);
    response.setHeader("Content-Disposition", "attachment; filename=" + encodeName);
    response.setHeader("Location", encodeName);
  }

  public void download(HttpServletRequest request, HttpServletResponse response, InputStream inStream,
      String name, String display) {
    String attach_name = getAttachName(name, display);
    try {
      response.reset();
      addContent(request, response, attach_name);
      IOs.copy(inStream, response.getOutputStream());
    } catch (Exception e) {
      logger.warn("download file error " + attach_name, e);
    } finally {
      IOs.close(inStream);
    }
  }

  public static String getAttachName(String name, String display) {
    String attch_name = "";
    String ext = Strings.substringAfterLast(name, ".");
    if (Strings.isBlank(display)) {
      attch_name = getFileName(name);
    } else {
      attch_name = display;
      if (!attch_name.endsWith("." + ext)) {
        attch_name += "." + ext;
      }
    }
    return attch_name;
  }

  /**
   * Returns the file name by path.
   * 
   * @param file_name
   */
  protected static String getFileName(String file_name) {
    if (file_name == null) return "";
    file_name = file_name.trim();
    int iPos = 0;
    iPos = file_name.lastIndexOf("\\");
    if (iPos > -1) file_name = file_name.substring(iPos + 1);

    iPos = file_name.lastIndexOf("/");
    if (iPos > -1) file_name = file_name.substring(iPos + 1);

    iPos = file_name.lastIndexOf(File.separator);
    if (iPos > -1) file_name = file_name.substring(iPos + 1);

    return file_name;
  }

  public void setMimeTypeProvider(MimeTypeProvider mimeTypeProvider) {
    this.mimeTypeProvider = mimeTypeProvider;
  }
}
