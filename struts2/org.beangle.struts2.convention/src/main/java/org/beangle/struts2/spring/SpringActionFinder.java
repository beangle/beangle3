/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
