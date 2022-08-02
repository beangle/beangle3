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
package org.beangle.struts2.freemarker;

import org.beangle.commons.web.util.Https;

import javax.net.ssl.HttpsURLConnection;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Wraps a {@link URL}, and implements methods required for a typical template source.
 */
class URLTemplateSource {
  private final URL url;
  private URLConnection conn;
  private InputStream inputStream;
  private Boolean useCaches;

  /**
   * @param useCaches {@code null} if this aspect wasn't set in the parent {@link TemplateLoader}.
   */
  URLTemplateSource(URL url) throws IOException {
    this.url = url;
    this.conn = url.openConnection();
    if (this.conn instanceof HttpsURLConnection) {
      Https.noverify((HttpsURLConnection) conn);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (o instanceof URLTemplateSource) {
      return url.equals(((URLTemplateSource) o).url);
    } else {
      return false;
    }
  }

  @Override
  public int hashCode() {
    return url.hashCode();
  }

  @Override
  public String toString() {
    return url.toString();
  }

  long lastModified() {
    if (conn instanceof JarURLConnection) {
      // There is a bug in sun's jar url connection that causes file handle leaks when calling getLastModified()
      // (see https://bugs.openjdk.java.net/browse/JDK-6956385).
      // Since the time stamps of jar file contents can't vary independent from the jar file timestamp, just use
      // the jar file timestamp
      URL jarURL = ((JarURLConnection) conn).getJarFileURL();
      if (jarURL.getProtocol().equals("file")) {
        // Return the last modified time of the underlying file - saves some opening and closing
        return new File(jarURL.getFile()).lastModified();
      } else {
        // Use the URL mechanism
        URLConnection jarConn = null;
        try {
          jarConn = jarURL.openConnection();
          return jarConn.getLastModified();
        } catch (IOException e) {
          return -1;
        } finally {
          try {
            if (jarConn != null) jarConn.getInputStream().close();
          } catch (IOException e) {
          }
        }
      }
    } else {
      long lastModified = conn.getLastModified();
      if (lastModified == -1L && url.getProtocol().equals("file")) {
        // Hack for obtaining accurate last modified time for
        // URLs that point to the local file system. This is fixed
        // in JDK 1.4, but prior JDKs returns -1 for file:// URLs.
        return new File(url.getFile()).lastModified();
      } else {
        return lastModified;
      }
    }
  }

  InputStream getInputStream() throws IOException {
    if (inputStream != null) {
      // Ensure that the returned InputStream reads from the beginning of the resource when getInputStream()
      // is called for the second time:
      try {
        inputStream.close();
      } catch (IOException e) {
        // Ignore; this is maybe because it was closed for the 2nd time now
      }
      this.conn = url.openConnection();
    }
    inputStream = conn.getInputStream();
    return inputStream;
  }

  void close() throws IOException {
    try {
      if (inputStream != null) {
        inputStream.close();
      } else {
        conn.getInputStream().close();
      }
    } finally {
      inputStream = null;
      conn = null;
    }
  }

  Boolean getUseCaches() {
    return useCaches;
  }

  void setUseCaches(boolean useCaches) {
    if (this.conn != null) {
      conn.setUseCaches(useCaches);
      this.useCaches = Boolean.valueOf(useCaches);
    }
  }

}
