/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.struts2.view.template;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 * @version $Id: AbstractTemplateEngine.java May 2, 2011 7:38:27 PM chaostone $
 */
public abstract class AbstractTemplateEngine implements TemplateEngine {
  private static final Logger logger = LoggerFactory.getLogger(AbstractTemplateEngine.class);
  protected final Map<String, Properties> themeProps = new HashMap<String, Properties>();
  public static final String DEFAULT_THEME_PROPERTIES_FILE_NAME = "theme.properties";

  public Properties getThemeProps(String theme) {
    Properties props = themeProps.get(theme);
    if (props == null) {
      props = loadProperties(Strings.concat("template/", theme, "/", DEFAULT_THEME_PROPERTIES_FILE_NAME));
      themeProps.put(theme, props);
    }
    return props;
  }

  private Properties loadProperties(String propName) {
    InputStream is = ClassLoaders.getResourceAsStream(propName, getClass());
    Properties props = new Properties();
    if (is != null) {
      try {
        props.load(is);
      } catch (IOException e) {
        logger.error("Could not load " + propName, e);
      } finally {
        try {
          is.close();
        } catch (IOException io) {
          logger.warn("Unable to close input stream", io);
        }
      }
    }
    return props;
  }

  public String getParentTemplate(String template) {
    int start = template.indexOf('/', 1) + 1;
    int end = template.lastIndexOf('/');

    String parentTheme = (String) getThemeProps(template.substring(start, end)).get("parent");
    if (null == parentTheme) return null;
    return Strings.concat(template.substring(0, start), parentTheme, template.substring(end));
  }

}
