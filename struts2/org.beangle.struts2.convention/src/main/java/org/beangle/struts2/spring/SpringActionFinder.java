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

public class SpringActionFinder implements ActionFinder, ApplicationContextAware {

  ApplicationContext appContext;

  final ActionTest test;

  public SpringActionFinder(ActionTest test) {
    super();
    this.test = test;
  }

  public Map<Class<?>, String> getActions() {
    Map<Class<?>, String> actions = CollectUtils.newHashMap();
    for (String name : appContext.getBeanDefinitionNames()) {
      Class<?> type = appContext.getType(name);
      if (null != type && test.evaluate(type.getName())) {
        actions.put(type, name);
      }
    }
    return actions;
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    appContext = applicationContext;
  }

}
