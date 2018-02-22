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
package org.beangle.orm.hibernate.dwr;

import org.beangle.commons.inject.Containers;
import org.beangle.inject.spring.SpringContainer;
import org.directwebremoting.extend.AbstractCreator;
import org.directwebremoting.extend.Creator;
import org.springframework.beans.factory.BeanFactory;

/**
 * A creator that relies on a spring bean factory.
 */
public class BeangleCreator extends AbstractCreator implements Creator {

  public BeangleCreator() {
    super();
    factory = getBeanFactory();
  }

  public String getBeanName() {
    return beanName;
  }

  public void setBeanName(String beanName) {
    this.beanName = beanName;
  }

  public Class<?> getType() {
    if (clazz == null) clazz = getInstance().getClass();
    return clazz;
  }

  public Object getInstance() {
    return factory.getBean(beanName);
  }

  private BeanFactory getBeanFactory() {
    SpringContainer context = (SpringContainer) Containers.getRoot();
    return context.getContext();
  }

  private String beanName = null;

  private BeanFactory factory = null;

  private Class<?> clazz = null;
}
