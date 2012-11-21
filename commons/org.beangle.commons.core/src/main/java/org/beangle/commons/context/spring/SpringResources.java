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
package org.beangle.commons.context.spring;

import java.io.IOException;

import org.beangle.commons.context.inject.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * <p>
 * SpringResources class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SpringResources extends Resources {

  private static Logger logger = LoggerFactory.getLogger(SpringResources.class);

  /**
   * 提供0到多个的global配置方法
   * 
   * @param resources an array of {@link org.springframework.core.io.Resource} objects.
   */
  public void setGlobals(Resource[] resources) {
    if (null == resources || resources.length == 0) {
      global = null;
      return;
    }
    if (resources.length == 1) {
      try {
        global = resources[0].getURL();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      logger.warn("there is {} resource,need only one", resources.length);
      int i = 0;
      for (Resource resource : resources) {
        logger.warn("resource {} is {}", i++, resource);
      }
    }
  }

  /**
   * <p>
   * setUsers.
   * </p>
   * 
   * @param resources an array of {@link org.springframework.core.io.Resource} objects.
   */
  public void setUsers(Resource[] resources) {
    if (null == resources || resources.length == 0) {
      user = null;
      return;
    }
    if (resources.length == 1) {
      try {
        user = resources[0].getURL();
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      logger.warn("there is {} resource,need only one", resources.length);
      int i = 0;
      for (Resource resource : resources) {
        logger.warn("resource {} is {}", i++, resource);
      }
    }
  }

  /**
   * <p>
   * setLocals.
   * </p>
   * 
   * @param resources an array of {@link org.springframework.core.io.Resource} objects.
   */
  public void setLocations(Resource[] resources) {
    if (null != resources) {
      for (Resource r : resources) {
        try {
          locals.add(r.getURL());
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    } else {
      locals.clear();
    }
  }

}
