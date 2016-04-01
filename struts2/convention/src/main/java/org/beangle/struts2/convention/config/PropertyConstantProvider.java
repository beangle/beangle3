/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.struts2.convention.config;

import java.util.Enumeration;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.config.ConfigurationException;
import com.opensymphony.xwork2.config.ConfigurationProvider;
import com.opensymphony.xwork2.inject.ContainerBuilder;
import com.opensymphony.xwork2.util.location.LocatableProperties;

/**
 * Convert System properties to struts2 constants,for those start with struts2
 * Using -Dany.struts.constant.name=your value.
 * 
 * @author chaostone
 */
public class PropertyConstantProvider implements ConfigurationProvider {

  private Logger logger = LoggerFactory.getLogger(getClass());

  @Override
  public void destroy() {

  }

  @Override
  public void init(Configuration configuration) throws ConfigurationException {
  }

  @Override
  public boolean needsReload() {
    return false;
  }

  @Override
  public void register(ContainerBuilder builder, LocatableProperties props) throws ConfigurationException {
    Properties properties = System.getProperties();
    Enumeration<?> keys = properties.propertyNames();
    while (keys.hasMoreElements()) {
      String key = (String) keys.nextElement();
      if (null != props.getProperty(key)) {
        String value = properties.getProperty(key);
        props.setProperty(key, value, null);
        logger.info("Override struts property {}={}", key, value);
      }
    }
  }

  @Override
  public void loadPackages() throws ConfigurationException {
  }

}
