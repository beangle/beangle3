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
package org.beangle.inject.spring;

import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.inject.Container;
import org.beangle.commons.lang.Option;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * Spring based Ioc Container
 *
 * @author chaostone
 * @since 3.1.0
 */
public class SpringContainer implements Container {

  final ApplicationContext context;

  public SpringContainer(ApplicationContext context) {
    super();
    this.context = context;
  }

  @Override
  public Option<Class<?>> getType(Object key) {
    try {
      Class<?> type = context.getType(key.toString());
      if (null == type) return Option.none();
      return Option.<Class<?>> some(type);
    } catch (NoSuchBeanDefinitionException e) {
      return Option.none();
    }
  }

  @Override
  public boolean contains(Object key) {
    return context.containsBean(key.toString());
  }

  @Override
  @SuppressWarnings("unchecked")
  public <T> Option<T> getBean(Object key) {
    try {
      return Option.some((T) context.getBean(key.toString()));
    } catch (NoSuchBeanDefinitionException e) {
      return Option.none();
    }
  }

  @Override
  public <T> Option<T> getBean(Class<T> type) {
    try {
      return Option.some((T) context.getBean(type));
    } catch (NoSuchBeanDefinitionException e) {
      return Option.none();
    }
  }

  @Override
  public <T> Option<T> getBean(Object key, Class<T> type) {
    try {
      return Option.some((T) context.getBean(key.toString(), type));
    } catch (BeansException e) {
      return Option.none();
    }
  }

  @Override
  public <T> Map<?, T> getBeans(Class<T> type) {
    return context.getBeansOfType(type);
  }

  @Override
  public Set<?> keys() {
    return CollectUtils.newHashSet(context.getBeanDefinitionNames());
  }

  public ApplicationContext getContext() {
    return context;
  }

}
