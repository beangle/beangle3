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
      ProcessChain chain) {
    // Get max last modified time stamp.
    long maxLastModified = -1;
    for (URL url : context.urls) {
      long lastModified = lastModified(url);
      if (lastModified > maxLastModified) maxLastModified = lastModified;
    }
    String requestETag = request.getHeader("If-None-Match");
    String newETag = String.valueOf(maxLastModified);
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
