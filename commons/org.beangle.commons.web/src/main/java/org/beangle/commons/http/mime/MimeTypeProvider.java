/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.http.mime;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.beangle.commons.context.inject.Resources;
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


  // META-INF/mimetypes.properties
  private void loadMimeType(URL url) {
    try {
      InputStream im = url.openStream();
      contentTypes.load(im);
      logger.info("Content types loaded ");
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
