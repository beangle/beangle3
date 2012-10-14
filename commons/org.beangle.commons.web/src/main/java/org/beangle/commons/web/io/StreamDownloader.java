/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.io;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Stream Downloader
 * 
 * @author chaostone
 * @since 2.1
 */
public interface StreamDownloader {

  void download(HttpServletRequest req, HttpServletResponse res, File file);

  void download(HttpServletRequest req, HttpServletResponse res, URL url, String display);

  void download(HttpServletRequest req, HttpServletResponse res, File file, String display);

  void download(HttpServletRequest req, HttpServletResponse res, InputStream inStream, String name,
      String display);

}
