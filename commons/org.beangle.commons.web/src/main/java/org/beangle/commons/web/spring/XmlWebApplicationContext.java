/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.web.spring;

import java.io.IOException;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.ResourceEntityResolver;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.AbstractRefreshableConfigApplicationContext;

public class XmlWebApplicationContext extends AbstractRefreshableConfigApplicationContext {

  @Override
  protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) throws BeansException,
      IOException {
    // Create a new XmlBeanDefinitionReader for the given BeanFactory.
    XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);

    // Configure the bean definition reader with this context's
    // resource loading environment.

    beanDefinitionReader.setEnvironment(this.getEnvironment());
    beanDefinitionReader.setResourceLoader(this);
    beanDefinitionReader.setEntityResolver(new ResourceEntityResolver(this));

    // Disable xml validating
    beanDefinitionReader.setValidating(false);
    // Allow a subclass to provide custom initialization of the reader,
    // then proceed with actually loading the bean definitions.
    loadBeanDefinitions(beanDefinitionReader);
  }

  protected void loadBeanDefinitions(XmlBeanDefinitionReader reader) throws IOException {
    String[] configLocations = getConfigLocations();
    if (configLocations != null) {
      for (String configLocation : configLocations) {
        reader.loadBeanDefinitions(configLocation);
      }
    }
  }

}
