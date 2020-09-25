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

import freemarker.cache.FileTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateException;
import org.apache.struts2.views.freemarker.FreemarkerManager;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import static org.beangle.commons.lang.Strings.split;
import static org.beangle.commons.lang.Strings.substringAfter;

/**
 * BeangleFreemarkerManager provide:
 * <p>
 * <ul>
 * <li>Better template loader sequence like class://,file://,webapp://</li>
 * <li>Multi freemark properties
 * loading(META-INF/freemarker.properties,freemarker.properties,System.properties)</li>
 * <li>Friendly Collection/Map/Object objectwrapper</li>
 * <li>Disable freemarker logger instead of slf4j</li>
 * </ul>
 *
 * @author chaostone
 * @since 2.1
 */
public class BeangleFreemarkerManager extends FreemarkerManager {

  private final Logger logger = LoggerFactory.getLogger(BeangleFreemarkerManager.class);

  public BeangleFreemarkerManager() {
    super();
    disableFreemarkerLogger();
  }

  protected void disableFreemarkerLogger() {
    try {
      freemarker.log.Logger.selectLoggerLibrary(freemarker.log.Logger.LIBRARY_NONE);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Override
  protected ObjectWrapper createObjectWrapper(ServletContext servletContext) {
    BeansWrapper wrapper = new BeangleObjectWrapper(altMapWrapper);
    // cacheBeanWrapper should be false in most case.
    wrapper.setUseCache(cacheBeanWrapper);
    return wrapper;
  }

  /**
   * disable configuration using theme temploader
   * because only tag based on theme ,common pages don't based on theme
   */
  protected void configureTemplateLoader(TemplateLoader templateLoader) {
    config.setTemplateLoader(templateLoader);
    config.setSharedVariable("include_if_exists", new IncludeIfExistsModel());
  }

  /**
   * The default template loader is a MultiTemplateLoader which includes
   * BeangleClassTemplateLoader(classpath:) and a WebappTemplateLoader
   * (webapp:) and FileTemplateLoader(file:) . All template path described
   * in init parameter templatePath or TemplatePlath
   * <p/>
   * The ClassTemplateLoader will resolve fully qualified template includes that begin with a slash.
   * for example /com/company/template/common.ftl
   * <p/>
   * The WebappTemplateLoader attempts to resolve templates relative to the web root folder
   */
  @Override
  protected TemplateLoader createTemplateLoader(ServletContext servletContext, String templatePath) {
    // construct a FileTemplateLoader for the init-param 'TemplatePath'
    String[] paths = split(templatePath, ",");
    List<TemplateLoader> loaders = CollectUtils.newArrayList();
    for (String path : paths) {
      if (path.startsWith("class://")) {
        loaders.add(new BeangleClassTemplateLoader(substringAfter(path, "class://")));
      } else if (path.startsWith("file://")) {
        try {
          loaders.add(new FileTemplateLoader(new File(substringAfter(path, "file://"))));
        } catch (IOException e) {
          throw new RuntimeException("templatePath: " + path + " cannot be accessed", e);
        }
      } else if (path.startsWith("webapp://")) {
        loaders.add(new WebappTemplateLoader(servletContext, substringAfter(path, "webapp://")));
      } else {
        throw new RuntimeException("templatePath: " + path
                + " is not well-formed. Use [class://|file://|webapp://] seperated with ,");
      }

    }
    return new MultiTemplateLoader(loaders.toArray(new TemplateLoader[loaders.size()]));
  }

  /**
   * Load the multi settings from the /META-INF/freemarker.properties and
   * /freemarker.properties file on the classpath
   *
   * @see freemarker.template.Configuration#setSettings for the definition of
   * valid settings
   */
  @SuppressWarnings("unchecked")
  @Override
  protected void loadSettings(ServletContext servletContext) {
    try {
      Properties properties = new Properties();
      // 1. first META-INF/freemarker.properties
      List<URL> urls = ClassLoaders.getResources("META-INF/freemarker.properties", getClass());
      for (URL url : urls)
        properties.putAll(getProperties(url));

      // 2. second global freemarker.properties
      urls = ClassLoaders.getResources("freemarker.properties", getClass());
      for (URL url : urls)
        properties.putAll(getProperties(url));

      // 3. system properties
      Properties sysProps = System.getProperties();
      Enumeration<?> sysKeys = sysProps.propertyNames();
      while (sysKeys.hasMoreElements()) {
        String key = (String) sysKeys.nextElement();
        String value = sysProps.getProperty(key);
        if (key.startsWith("freemarker.")) {
          properties.put(Strings.substringAfter(key, "freemarker."), value);
        }
      }

      // 4 add setting and log info
      StringBuilder sb = new StringBuilder();
      @SuppressWarnings("rawtypes")
      List keys = CollectUtils.newArrayList(properties.keySet());
      Collections.sort(keys);
      for (Iterator<String> iter = keys.iterator(); iter.hasNext(); ) {
        String key = iter.next();
        String value = (String) properties.get(key);
        if (null != key && null != value) {
          addSetting(key, value);
          sb.append(key).append("->").append(value);
          if (iter.hasNext()) sb.append(',');
        }
      }
      // 5 support beangle profiles
      String profiles = System.getProperty("beangle.cdi.profiles");
      if (Strings.contains(profiles, "dev")) {
        config.setTemplateUpdateDelayMilliseconds(0);
      }
      logger.info("Freemarker properties:{} ", sb);
    } catch (TemplateException e) {
      logger.error("Error while setting freemarker.properties", e);
    }
  }

  public void addSetting(String name, String value) throws TemplateException {
    if (name.equals("content_type") || name.equals(INITPARAM_CONTENT_TYPE)) {
      contentType = value;
      config.setCustomAttribute("content_type", value);
    } else {
      super.addSetting(name, value);
    }
  }

  private Properties getProperties(URL url) {
    logger.info("loading {}", url);
    InputStream in = null;
    try {
      in = url.openStream();
      if (in != null) {
        Properties p = new Properties();
        p.load(in);
        return p;
      }
    } catch (IOException e) {
      logger.error("Error while loading " + url, e);
    } finally {
      if (in != null) {
        try {
          in.close();
        } catch (IOException io) {
          logger.warn("Unable to close input stream", io);
        }
      }
    }
    return null;
  }
}
