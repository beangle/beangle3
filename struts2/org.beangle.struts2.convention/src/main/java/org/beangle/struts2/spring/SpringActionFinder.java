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
package org.beangle.struts2.spring;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.struts2.convention.config.ActionFinder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Find actions from application context
 * 
 * @author chaostone
 * @since 3.0.0
 */
public class SpringActionFinder implements ActionFinder, ApplicationContextAware {

  ApplicationContext appContext;

  public Map<Class<?>, String> getActions(ActionTest actionTest) {
    Map<Class<?>, String> actions = CollectUtils.newHashMap();
    if (null != appContext) {
      for (String name : appContext.getBeanDefinitionNames()) {
        Class<?> type = appContext.getType(name);
        if (null != type && actionTest.evaluate(type.getName())) {
          actions.put(type, name);
        }
      }
    }
    return actions;
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    appContext = applicationContext;
  }

}
