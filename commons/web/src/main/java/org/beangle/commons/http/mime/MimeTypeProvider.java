/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.http.mime;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.beangle.commons.inject.Resources;
import org.beangle.commons.lang.time.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MimeTypeProvider {

  private static Logger logger = LoggerFactory.getLogger(MimeTypeProvider.class);

  private final Properties contentTypes = new Properties();

  private Resources resources;

  public String getMimeType(String ext, String defaultValue) {
    return contentTypes.getProperty(ext, defaultValue);
  }

  public String getMimeType(String ext) {
    return contentTypes.getProperty(ext);
  }

  /**
   * META-INF/mimetypes.properties
   * 
   * @param url
   */
  private void loadMimeType(URL url) {
    try {
      Stopwatch watch = new Stopwatch(true);
      InputStream im = url.openStream();
      contentTypes.load(im);
      logger.info("Load {} content types in {}", contentTypes.size(), watch);
      im.close();
    } catch (IOException e) {
      logger.error("load " + url + " error", e);
    }
  }

  public Resources getResources() {
    return resources;
  }

  public void setResources(Resources resources) {
    this.resources = resources;
    if (null == resources) return;
    if (null != resources.getGlobal()) {
      loadMimeType(resources.getGlobal());
    }
    if (null != resources.getLocals()) {
      for (URL path : resources.getLocals()) {
        loadMimeType(path);
      }
    }
    if (null != resources.getUser()) {
      loadMimeType(resources.getUser());
    }

  }

}
