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
package org.beangle.commons.entity.metadata.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Type;
import org.beangle.commons.lang.ClassLoaders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * SimpleEntityContext class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SimpleEntityContext extends AbstractEntityContext implements Initializing {

  private static final Logger logger = LoggerFactory.getLogger(SimpleEntityContext.class);

  public void init() throws Exception {
    Properties props = new Properties();
    try {
      InputStream is = ClassLoaders.getResourceAsStream("model.properties", getClass());
      if (null != is) {
        props.load(is);
      }
    } catch (IOException e) {
      logger.error("read error model.properties");
    }

    if (!props.isEmpty()) {
      logger.info("Using model.properties initialize Entity Context.");
      for (Map.Entry<Object, Object> entry : props.entrySet()) {
        String key = (String) entry.getKey();
        String value = (String) entry.getValue();
        EntityType entityType = null;
        try {
          entityType = new EntityType(key, Class.forName(value), "id");
        } catch (ClassNotFoundException e) {
          logger.error(value + " was not correct class name", e);
        }
        entityType.setPropertyTypes(new HashMap<String, Type>());
        entityTypes.put(key, entityType);
      }
    }

  }
}
