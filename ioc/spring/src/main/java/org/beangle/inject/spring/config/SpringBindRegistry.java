/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.inject.spring.config;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.inject.bind.BindRegistry;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.time.Stopwatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.ClassUtils;

/**
 * <p>
 * SpringBindRegistry class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SpringBindRegistry implements BindRegistry {

  private static final Logger logger = LoggerFactory.getLogger(SpringBindRegistry.class);

  protected Map<String, Class<?>> nameTypes = CollectUtils.newHashMap();

  protected Map<Class<?>, List<String>> typeNames = CollectUtils.newHashMap();

  protected final BeanDefinitionRegistry definitionRegistry;

  /**
   * <p>
   * getBeanNames.
   * </p>
   * 
   * @return a {@link java.util.Set} object.
   */
  public Set<String> getBeanNames() {
    return nameTypes.keySet();
  }

  /**
   * <p>
   * Constructor for SpringBindRegistry.
   * </p>
   * 
   * @param registry a {@link org.springframework.beans.factory.support.BeanDefinitionRegistry}
   *          object.
   */
  public SpringBindRegistry(BeanDefinitionRegistry registry) {
    Stopwatch watch = new Stopwatch(true);
    definitionRegistry = registry;
    for (String name : registry.getBeanDefinitionNames()) {
      BeanDefinition bd = registry.getBeanDefinition(name);
      if (bd.isAbstract()) continue;
      // find classname
      String className = bd.getBeanClassName();
      if (null == className) {
        String parentName = bd.getParentName();
        if (null == parentName) continue;
        else {
          BeanDefinition parentDef = registry.getBeanDefinition(parentName);
          className = parentDef.getBeanClassName();
        }
      }
      if (null == className) continue;

      try {
        Class<?> beanClass = ClassUtils.forName(className, ClassUtils.getDefaultClassLoader());
        if (FactoryBean.class.isAssignableFrom(beanClass)) {
          register(beanClass, "&" + name);
          PropertyValue pv = bd.getPropertyValues().getPropertyValue("target");
          if (null == pv) {
            Class<?> artifactClass = ((FactoryBean<?>) beanClass.newInstance()).getObjectType();
            if (null != artifactClass) register(artifactClass, name);
          } else {
            if (pv.getValue() instanceof BeanDefinitionHolder) {
              String nestedClassName = ((BeanDefinitionHolder) pv.getValue()).getBeanDefinition()
                  .getBeanClassName();
              if (null != nestedClassName) {
                register(ClassUtils.forName(nestedClassName, ClassUtils.getDefaultClassLoader()), name);
              }
            }
          }
        } else {
          register(beanClass, name);
        }
      } catch (Exception e) {
        logger.error("class not found", e);
        continue;
      }
    }
    logger.info("Init registry ({} beans) in {}", nameTypes.size(), watch);
  }

  /**
   * Get bean name list according given type
   */
  public List<String> getBeanNames(Class<?> type) {
    if (typeNames.containsKey(type)) { return typeNames.get(type); }
    List<String> names = CollectUtils.newArrayList();
    for (Map.Entry<String, Class<?>> entry : nameTypes.entrySet()) {
      if (type.isAssignableFrom(entry.getValue())) {
        names.add(entry.getKey());
      }
    }
    typeNames.put(type, names);
    return names;
  }

  /** {@inheritDoc} */
  public Class<?> getBeanType(String beanName) {
    return nameTypes.get(beanName);
  }

  /** {@inheritDoc} */
  public boolean contains(String beanName) {
    return nameTypes.containsKey(beanName);
  }

  /**
   * <p>
   * register.
   * </p>
   * 
   * @param type a {@link java.lang.Class} object.
   * @param name a {@link java.lang.String} object.
   * @param args a {@link java.lang.Object} object.
   */
  public void register(Class<?> type, String name, Object... args) {
    Assert.notNull(name, "class'name is null");
    if (0 == args.length) {
      nameTypes.put(name, type);
    } else {
      // 注册bean的name和别名
      BeanDefinition bd = (BeanDefinition) args[0];
      definitionRegistry.registerBeanDefinition(name, bd);
      // for list(a.class,b.class) binding usage
      String alias = bd.getBeanClassName();
      if (bd.isSingleton() && !name.equals(alias) && !definitionRegistry.isBeanNameInUse(alias)) {
        definitionRegistry.registerAlias(name, alias);
      }
      if (null == type) {
        if (!bd.isAbstract()) throw new RuntimeException("Concrete bean should has class.");
      } else nameTypes.put(name, type);
    }
  }

  public boolean isPrimary(String name) {
    return definitionRegistry.getBeanDefinition(name).isPrimary();
  }

}
