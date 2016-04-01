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
package org.beangle.inject.spring;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.event.DefaultEventMulticaster;
import org.beangle.commons.event.EventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author chaostone
 * @since 3.0.0
 */
public class SpringEventMulticaster extends DefaultEventMulticaster implements ApplicationContextAware {

  List<String> listenerBeans = CollectUtils.newArrayList();

  ApplicationContext appContext;

  public synchronized void initListeners() {
    if (listeners.isEmpty()) {
      if (listeners.isEmpty()) {
        for (String beanName : listenerBeans) {
          if (appContext.containsBean(beanName))
            addListener(appContext.getBean(beanName, EventListener.class));
        }
      }
    }
  }

  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.appContext = applicationContext;

  }

  public List<String> getListenerBeans() {
    return listenerBeans;
  }

  public void setListenerBeans(List<String> listenerBeans) {
    this.listenerBeans = listenerBeans;
  }
}
