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
package org.beangle.commons.web.resource.filter;

import java.io.File;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beangle.commons.web.resource.ProcessChain;
import org.beangle.commons.web.resource.ProcessContext;
import org.beangle.commons.web.resource.ResourceFilter;

public class HeaderFilter implements ResourceFilter {

  /**
   * Static resource expire 7 days by default
   */
  protected int expireDays = 7;

  @Override
  public void filter(ProcessContext context, HttpServletRequest request, HttpServletResponse response,
      ProcessChain chain) throws IOException{
    // Get max last modified time stamp.
    long maxLastModified = -1;
    for (ProcessContext.Resource res : context.resources) {
      long lastModified = lastModified(res.url);
      if (lastModified > maxLastModified) maxLastModified = lastModified;
    }
    String requestETag = request.getHeader("If-None-Match");
    String newETag = String.valueOf(maxLastModified);
    response.setHeader("ETag", newETag);
    // not modified, content is not sent - only basic headers and status SC_NOT_MODIFIED
    if (newETag.equals(requestETag)) {
      response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
      return;
    } else {
      chain.process(context, request, response);
      // set heading information for caching static content
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_MONTH, expireDays);
      final long expires = cal.getTimeInMillis();
      response.setDateHeader("Date", System.currentTimeMillis());
      response.setDateHeader("Expires", expires);
      response.setDateHeader("Retry-After", expires);
      response.setHeader("Cache-Control", "public");
      if (maxLastModified > 0) response.setDateHeader("Last-Modified", maxLastModified);
    }
  }

  /**
   * Return url's last modified date time.
   * saves some opening and closing
   */
  private long lastModified(URL url) {
    if (url.getProtocol().equals("file")) {
      return new File(url.getFile()).lastModified();
    } else {
      try {
        URLConnection conn = url.openConnection();
        if (conn instanceof JarURLConnection) {
          URL jarURL = ((JarURLConnection) conn).getJarFileURL();
          if (jarURL.getProtocol().equals("file")) { return new File(jarURL.getFile()).lastModified(); }
        }
      } catch (IOException e1) {
        return -1;
      }
      return -1;
    }
  }

  public void setExpireDays(int expireDays) {
    this.expireDays = expireDays;
  }

}
