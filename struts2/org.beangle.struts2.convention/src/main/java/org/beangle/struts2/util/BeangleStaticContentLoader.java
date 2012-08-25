/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.StrutsConstants;
import org.apache.struts2.dispatcher.StaticContentLoader;
import org.apache.struts2.dispatcher.ng.HostConfig;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.inject.Inject;
import com.opensymphony.xwork2.util.ClassLoaderUtil;

/**
 * BeangleStaticContentLoader provide serval features
 * <ul>
 * <li>1 Avoid load resource when get not expired content</li>
 * <li>2 multi resource in one request</li>
 * </ul>
 * <p>
 * BUT This loader cannot find content of new version,because it didn't record content's really last
 * modify date;Some md5 or fingerprint technology may work.So use different resource path when
 * resource modified(prefer rename beagle-3.0.js to beangle-3.1.js,or using
 * beangle-3.0.js?version=m1 etc.);
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
   * Store state of StrutsConstants.STRUTS_SERVE_STATIC_CONTENT setting.
   */
  protected boolean serveStatic;

  /**
   * Store state of StrutsConstants.STRUTS_SERVE_STATIC_BROWSER_CACHE setting.
   */
  protected boolean serveStaticBrowserCache;

  /**
   * Provide a formatted date for setting heading information when caching static content.
   */
  protected final long lastModified;

  /**
   * Static resource expire 10 days by default
   */
  protected int expireDays = 10;

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
    contentTypes.put("txt", "text/plain");
    contentTypes.put("gif", "image/gif");
    contentTypes.put("jpg", "image/jpeg");
    contentTypes.put("jpeg", "image/jpeg");
    contentTypes.put("png", "image/png");

    // Round down to the nearest second since client headers are in seconds
    lastModified = System.currentTimeMillis() / 1000 * 1000;
  }

  /**
   * Locate a static resource and copy directly to the response, setting the
   * appropriate caching headers.
   */
  public void findStaticResource(String path, HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    // check for if-modified-since, prior to any other headers
    long ifModifiedSince = 0;
    try {
      ifModifiedSince = request.getDateHeader("If-Modified-Since");
    } catch (Exception e) {
      logger.warn("Invalid If-Modified-Since header : '{}',ignoring", request.getHeader("If-Modified-Since"));
    }
    Calendar cal = Calendar.getInstance();
    long now = cal.getTimeInMillis();
    cal.add(Calendar.DAY_OF_MONTH, expireDays);
    long expires = cal.getTimeInMillis();

    // not modified, content is not sent - only basic headers and status SC_NOT_MODIFIED
    if (ifModifiedSince > 0 && ifModifiedSince <= lastModified) {
      response.setDateHeader("Expires", expires);
      response.setStatus(HttpServletResponse.SC_NOT_MODIFIED);
      return;
    }
    String namestr = path;
    List<InputStream> iss = CollectUtils.newArrayList();
    String[] names = Strings.split(namestr, ",");
    String pathDir = null;
    for (String name : names) {
      if (null == pathDir) {
        name = cleanupPath(name);
        pathDir = Strings.substringBeforeLast(name, "/");
      } else if (!name.startsWith("/")) {
        name = pathDir + "/" + name;
      }
      // name will starts with /
      int oldsize = iss.size();
      for (String pathPrefix : pathPrefixes) {
        URL resourceUrl = findResource(pathPrefix + name);
        if (resourceUrl != null) {
          InputStream is = null;
          try {
            is = resourceUrl.openStream();
          } catch (IOException ex) {
            // just ignore it
            continue;
          }

          // not inside the try block, as this could throw IOExceptions also
          if (is != null) {
            iss.add(is);
            break;
          }
        }
      }
      if (iss.size() == oldsize) logger.info("cannot find resource {}", name);
    }

    if (iss.isEmpty()) response.sendError(HttpServletResponse.SC_NOT_FOUND);
    else {
      String contentType = getContentType(path);
      if (contentType != null) response.setContentType(contentType);

      if (serveStaticBrowserCache) {
        // set heading information for caching static content
        response.setDateHeader("Date", now);
        response.setDateHeader("Expires", expires);
        response.setDateHeader("Retry-After", expires);
        response.setHeader("Cache-Control", "public");
        response.setDateHeader("Last-Modified", lastModified);
      } else {
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
      }

      for (InputStream is : iss) {
        try {
          copy(is, response.getOutputStream());
        } finally {
          is.close();
        }
      }
    }
  }

  /**
   * Look for a static resource in the classpath.
   * 
   * @param path The resource path
   * @return The inputstream of the resource
   * @throws IOException If there is a problem locating the resource
   */
  protected URL findResource(String path) throws IOException {
    return ClassLoaderUtil.getResource(path, getClass());
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
    return serveStatic && resourcePath.startsWith("/static");
  }

  /**
   * @param path requested path
   * @return path without leading "/static"
   */
  protected String cleanupPath(String path) {
    return path.substring("/static".length());
  }

  /**
   * Modify state of StrutsConstants.STRUTS_SERVE_STATIC_CONTENT setting.
   * 
   * @param val New setting
   */
  @Inject(StrutsConstants.STRUTS_SERVE_STATIC_CONTENT)
  public void setServeStaticContent(String val) {
    serveStatic = "true".equals(val);
  }

  /**
   * Modify state of StrutsConstants.STRUTS_SERVE_STATIC_BROWSER_CACHE
   * setting.
   * 
   * @param val
   *          New setting
   */
  @Inject(StrutsConstants.STRUTS_SERVE_STATIC_BROWSER_CACHE)
  public void setServeStaticBrowserCache(String val) {
    serveStaticBrowserCache = "true".equals(val);
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
   * 
   * @return
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
