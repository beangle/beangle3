package org.beangle.commons.context.spring;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.context.event.DefaultEventMulticaster;
import org.beangle.commons.context.event.EventListener;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringEventMulticaster extends DefaultEventMulticaster implements ApplicationContextAware {

  List<String> listenerBeans = CollectUtils.newArrayList();

  ApplicationContext appContext;

  public void initListeners() {
    if (listeners.isEmpty()) {
      for (String beanName : listenerBeans) {
        if (appContext.containsBean(beanName)) addListener(appContext.getBean(beanName, EventListener.class));
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
