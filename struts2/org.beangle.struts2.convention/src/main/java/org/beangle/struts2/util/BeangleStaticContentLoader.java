/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
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
 * provide
 * 
 * <pre>
 * 	1 avoid load resource when get not expired content
 *  2 multi resource in one request
 * </pre>
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
  protected final Calendar lastModifiedCal = Calendar.getInstance();

  /**
   * Store state of StrutsConstants.STRUTS_I18N_ENCODING setting.
   */
  protected String encoding;

  protected int expireDays = 10;

  /**
   * Modify state of StrutsConstants.STRUTS_SERVE_STATIC_CONTENT setting.
   * 
   * @param val
   *          New setting
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
   * Modify state of StrutsConstants.STRUTS_I18N_ENCODING setting.
   * 
   * @param val
   *          New setting
   */
  @Inject(StrutsConstants.STRUTS_I18N_ENCODING)
  public void setEncoding(String val) {
    encoding = val;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.apache.struts2.dispatcher.StaticResourceLoader#setHostConfig(javax.servlet.FilterConfig)
   */
  public void setHostConfig(HostConfig filterConfig) {
    String param = filterConfig.getInitParameter("packages");
    String packages = getAdditionalPackages();
    if (param != null) {
      packages = param + " " + packages;
    }
    this.pathPrefixes = parse(packages);
  }

  protected String getAdditionalPackages() {
    return "org.apache.struts2.static template org.apache.struts2.interceptor.debugging static";
  }

  /**
   * Create a string array from a comma-delimited list of packages.
   * 
   * @param packages
   *          A comma-delimited String listing packages
   * @return A string array of packages
   */
  protected String[] parse(String packages) {
    if (packages == null) { return null; }
    List<String> pathPrefixes = new ArrayList<String>();

    StringTokenizer st = new StringTokenizer(packages, ", \n\t");
    while (st.hasMoreTokens()) {
      String pathPrefix = st.nextToken().replace('.', '/');
      if (!pathPrefix.endsWith("/")) {
        pathPrefix += "/";
      }
      pathPrefixes.add(pathPrefix);
    }

    return pathPrefixes.toArray(new String[pathPrefixes.size()]);
  }

  /*
   * (non-Javadoc)
   * @see org.apache.struts2.dispatcher.StaticResourceLoader#findStaticResource(java.lang.String,
   * javax.servlet.http.HttpServletRequest,
   * javax.servlet.http.HttpServletResponse)
   */
  public void findStaticResource(String path, HttpServletRequest request, HttpServletResponse response)
      throws IOException {
    Calendar cal = Calendar.getInstance();
    // check for if-modified-since, prior to any other headers
    long ifModifiedSince = 0;
    try {
      ifModifiedSince = request.getDateHeader("If-Modified-Since");
    } catch (Exception e) {
      logger.warn("Invalid If-Modified-Since header value: '" + request.getHeader("If-Modified-Since")
          + "', ignoring");
    }
    long now = cal.getTimeInMillis();
    cal.add(Calendar.DAY_OF_MONTH, expireDays);
    long expires = cal.getTimeInMillis();

    if (ifModifiedSince > 0 && ifModifiedSince <= now) {
      // not modified, content is not sent - only basic
      // headers and status SC_NOT_MODIFIED
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
      int oldsize = iss.size();
      for (String pathPrefix : pathPrefixes) {
        URL resourceUrl = findResource(buildPath(name, pathPrefix));
        if (resourceUrl != null) {
          InputStream is = null;
          try {
            // check that the resource path is under the pathPrefix path
            String pathEnding = buildPath(name, pathPrefix);
            if (resourceUrl.getFile().endsWith(pathEnding)) is = resourceUrl.openStream();
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
      if (iss.size() == oldsize) {
        logger.info("cannot find resource {}", name);
      }
    }

    if (iss.isEmpty()) response.sendError(HttpServletResponse.SC_NOT_FOUND);
    else {
      // set the content-type header
      String contentType = getContentType(path);
      if (contentType != null) response.setContentType(contentType);

      if (serveStaticBrowserCache) {
        // set heading information for caching static content
        response.setDateHeader("Date", now);
        response.setDateHeader("Expires", expires);
        response.setDateHeader("Retry-After", expires);
        response.setHeader("Cache-Control", "public");
        response.setDateHeader("Last-Modified", now);
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
   * @param path
   *          The resource path
   * @return The inputstream of the resource
   * @throws IOException
   *           If there is a problem locating the resource
   */
  protected URL findResource(String path) throws IOException {
    return ClassLoaderUtil.getResource(path, getClass());
  }

  /**
   * @param name
   *          resource name
   * @param packagePrefix
   *          The package prefix to use to locate the resource
   * @return full path
   * @throws UnsupportedEncodingException
   * @throws IOException
   */
  protected String buildPath(String name, String packagePrefix) throws UnsupportedEncodingException {
    String resourcePath;
    if (packagePrefix.endsWith("/") && name.startsWith("/")) {
      resourcePath = packagePrefix + name.substring(1);
    } else {
      resourcePath = packagePrefix + name;
    }

    return URLDecoder.decode(resourcePath, encoding);
  }

  /**
   * Determine the content type for the resource name.
   * 
   * @param name
   *          The resource name
   * @return The mime type
   */
  protected String getContentType(String name) {
    // NOT using the code provided activation.jar to avoid adding yet another dependency
    // this is generally OK, since these are the main files we server up
    if (name.endsWith(".js")) {
      return "text/javascript";
    } else if (name.endsWith(".css")) {
      return "text/css";
    } else if (name.endsWith(".html")) {
      return "text/html";
    } else if (name.endsWith(".txt")) {
      return "text/plain";
    } else if (name.endsWith(".gif")) {
      return "image/gif";
    } else if (name.endsWith(".jpg") || name.endsWith(".jpeg")) {
      return "image/jpeg";
    } else if (name.endsWith(".png")) {
      return "image/png";
    } else {
      return null;
    }
  }

  /**
   * Copy bytes from the input stream to the output stream.
   * 
   * @param input
   *          The input stream
   * @param output
   *          The output stream
   * @throws IOException
   *           If anything goes wrong
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
    return serveStatic && (resourcePath.startsWith("/struts") || resourcePath.startsWith("/static"));
  }

  /**
   * @param path
   *          requested path
   * @return path without leading "/struts" or "/static"
   */
  protected String cleanupPath(String path) {
    // path will start with "/struts" or "/static", remove them
    return path.substring(7);
  }
}
