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
package org.beangle.commons.config.property;

import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import org.beangle.commons.inject.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * UrlPropertyConfigProvider class.
 * </p>
 *
 * @author chaostone
 * @version $Id: $
 */
public class UrlPropertyConfigProvider implements PropertyConfig.Provider {

  /** Constant <code>logger</code> */
  protected static final Logger logger = LoggerFactory.getLogger(UrlPropertyConfigProvider.class);

  protected Resources resources;

  /**
   * <p>
   * getConfig.
   * </p>
   *
   * @return a {@link java.util.Properties} object.
   */
  public Properties getConfig() {
    try {
      Properties properties = new Properties();
      if (null != resources.getGlobal()) populateConfigItems(properties, resources.getGlobal());

      if (null != resources.getLocals()) {
        for (URL url : resources.getLocals()) {
          populateConfigItems(properties, url);
        }
      }

      if (null != resources.getUser()) populateConfigItems(properties, resources.getUser());
      return properties;
    } catch (Exception e) {
      logger.error("Exception", e);
      throw new RuntimeException(e);
    }
  }

  private void populateConfigItems(Properties properties, URL url) {
    logger.debug("loading {}", url);
    try {
      InputStream is = url.openStream();
      properties.load(is);
      is.close();
    } catch (Exception e) {
      logger.error("populate config error", e);
    }
  }

  /**
   * <p>
   * Getter for the field <code>resource</code>.
   * </p>
   *
   * @return a {@link org.beangle.commons.inject.Resources} object.
   */
  public Resources getResources() {
    return resources;
  }

  /**
   * <p>
   * Setter for the field <code>resource</code>.
   * </p>
   *
   * @param resources a {@link org.beangle.commons.inject.Resources} object.
   */
  public void setResources(Resources resources) {
    this.resources = resources;
  }

}
