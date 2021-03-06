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
package org.beangle.commons.web.io;

import java.io.File;
import java.io.InputStream;
import java.net.URL;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
