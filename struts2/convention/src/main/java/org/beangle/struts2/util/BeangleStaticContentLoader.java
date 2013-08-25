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
package org.beangle.struts2.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.dispatcher.StaticContentLoader;
import org.apache.struts2.dispatcher.ng.HostConfig;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BeangleStaticContentLoader provide serval features
 * <ul>
 * <li>1 Avoid load resource when get not expired content using ETag</li>
 * <li>2 Multi resource in one request</li>
 * <li>3 Realy detect resource modify datetime.</li>
 * </ul>
 * <p>
 * This loader can find content of new version using url's lastmodifed .So different resource path
 * (rename beagle-3.0.js to beangle-3.1.js,or using beangle-3.0.js?version=m1 etc.) was not
 * necessary;
 * </p>
 * 
 * @author chaostone
 * @version $Id: BeangleStaticContentLoader.java Dec 25, 2011 9:19:06 PM chaostone $
 */
public class BeangleStaticContentLoader implements StaticContentLoader {
  /**
   * Provide a logging instance.
   */
  private Logger logger = LoggerFactory.getLogger(BeangleStaticContentLoader.class);

  /**
   * Store set of path prefixes to use with static resources.
   * Each prefix not ended with /
   */
  protected String[] pathPrefixes;

  /**
   * Static resource expire 7 days by default
   */
  protected int expireDays = 7;

  /**
   * Servered content types
   */
  private Map<String, String> contentTypes = CollectUtils.newHashMap();

  public BeangleStaticContentLoader() {
    super();
    // NOT using the code provided activation.jar to avoid adding yet another dependency
    // this is generally OK, since these are the main files we server up
    contentTypes.put("js", "text/javascript");
    contentTypes.put("css", "text/css");
    contentTypes.put("html", "text/html");
    contentTypes.put("htm", "text/html");
    contentTypes.put("txt", "text/plain");
    contentTypes.put("gif", "image/gif");
    contentTypes.put("jpg", "image/jpeg");
    contentTypes.put("jpeg", "image/jpeg");
    contentTypes.put("png", "image/png");
    contentTypes.put("json", "application/json");
    contentTypes.put("htc", "text/x-component");
  }

  /**
   * Locate a static resource and copy directly to the response, setting the
   * appropriate caching headers.
   */
  public void findStaticResource(String path, HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    List<URL> urls = findResources(path);
    if (urls.isEmpty()) response.sendError(HttpServletResponse.SC_NOT_FOUND);

    // Get max last modified time stamp.
    long maxLastModified = -1;
    for (URL url : urls) {
      long lastModified = lastModified(url);
      if (lastModified > maxLastModified) maxLastModified = lastModified;
    }

    String requestETag = request.getHeader("If-None-Match");
    String newETag = String.valueOf(maxLastModified);
    response.setHeader("ETag", newETag);
    // not modified, content is not sent - only basic headers and status SC_NOT_MODIFIED
    if (newETag.equals(requestETag)) {
      response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
    } else {
      String contentType = getContentType(path);
      if (contentType != null) response.setContentType(contentType);

      // set heading information for caching static content
      Calendar cal = Calendar.getInstance();
      cal.add(Calendar.DAY_OF_MONTH, expireDays);
      final long expires = cal.getTimeInMillis();
      response.setDateHeader("Date", System.currentTimeMillis());
      response.setDateHeader("Expires", expires);
      response.setDateHeader("Retry-After", expires);
      response.setHeader("Cache-Control", "public");
      if (maxLastModified > 0) response.setDateHeader("Last-Modified", maxLastModified);
      OutputStream out = response.getOutputStream();
      for (URL url : urls) {
        InputStream is = url.openConnection().getInputStream();
        try {
          copy(is, out);
          if (null != contentType && contentType.startsWith("text")) out.write('\n');
        } finally {
          is.close();
        }
      }
    }
  }

  /**
   * Return url list for given resources.
   * 
   * @param path resource path description /static/a.js,b.js etc.
   */
  private List<URL> findResources(String path) {
    String lastPostfix = "." + Strings.substringAfterLast(path, ".");
    String namestr = cleanupPath(path);
    List<URL> urls = CollectUtils.newArrayList();
    String[] names = Strings.split(namestr, ",");
    String pathDir = null;
    for (String name : names) {
      if (name.startsWith("/")) pathDir = Strings.substringBeforeLast(name, "/");
      else name = pathDir + "/" + name;
      if (!name.endsWith(lastPostfix)) name += lastPostfix;
      // name will starts with /
      URL url = null;
      for (String pathPrefix : pathPrefixes) {
        url = findResource(pathPrefix + name);
        if (url != null) break;
      }
      if (null == url) logger.warn("cannot find resource {}", name);
      else urls.add(url);
    }
    return urls;
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

  /**
   * Look for a static resource in the classpath.
   * 
   * @param path The resource path
   * @return The inputstream of the resource
   * @throws IOException If there is a problem locating the resource
   */
  protected URL findResource(String path) {
    return ClassLoaders.getResource(path, getClass());
  }

  /**
   * Determine the content type for the resource name.
   * 
   * @param name The resource name
   * @return The mime type
   */
  protected String getContentType(String name) {
    return contentTypes.get(Strings.substringAfterLast(name, "."));
  }

  /**
   * Copy bytes from the input stream to the output stream.
   * 
   * @param input The input stream
   * @param output The output stream
   * @throws IOException If anything goes wrong
   */
  protected void copy(InputStream input, OutputStream output) throws IOException {
    final byte[] buffer = new byte[4096];
    int n;
    while (-1 != (n = input.read(buffer))) {
      output.write(buffer, 0, n);
    }
    output.flush();
  }

  public boolean canHandle(String resourcePath) {
    return resourcePath.startsWith("/static");
  }

  /**
   * @param path requested path
   * @return path without leading "/static"
   */
  protected String cleanupPath(String path) {
    return path.substring("/static".length());
  }

  /**
   * Catch struts2 init parameters
   */
  public void setHostConfig(HostConfig filterConfig) {
    String param = filterConfig.getInitParameter("packages");
    String packages = getAdditionalPackages();
    if (param != null) packages = param + " " + packages;
    this.pathPrefixes = parse(packages);
  }

  /**
   * Additional static resource search package
   */
  protected String getAdditionalPackages() {
    return "static template";
  }

  /**
   * Create a string array from a comma-delimited list of packages.
   * 
   * @param packages A comma-delimited String listing packages
   * @return A string array of packages
   */
  protected String[] parse(String packages) {
    if (packages == null) { return null; }
    List<String> pathPrefixes = new ArrayList<String>();
    Set<String> parsed = CollectUtils.newHashSet();
    StringTokenizer st = new StringTokenizer(packages, ", \n\t");
    while (st.hasMoreTokens()) {
      String pathPrefix = st.nextToken().replace('.', '/');
      if (pathPrefix.endsWith("/")) pathPrefix = Strings.substringBeforeLast(pathPrefix, "/");
      if (!parsed.contains(pathPrefix)) {
        parsed.add(pathPrefix);
        pathPrefixes.add(pathPrefix);
      }
    }
    return pathPrefixes.toArray(new String[pathPrefixes.size()]);
  }

}
