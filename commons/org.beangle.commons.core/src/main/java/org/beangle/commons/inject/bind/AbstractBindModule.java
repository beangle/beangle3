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
package org.beangle.commons.inject.bind;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.inject.Scope;
import org.beangle.commons.inject.bind.BeanConfig.Definition;
import org.beangle.commons.inject.bind.BeanConfig.DefinitionBinder;
import org.beangle.commons.inject.bind.BeanConfig.ReferenceValue;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.tuple.Pair;

/**
 * <p>
 * Abstract AbstractBindModule class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class AbstractBindModule implements BindModule {

  protected BeanConfig config;

  /**
   * <p>
   * Getter for the field <code>config</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.inject.bind.BeanConfig} object.
   */
  public final BeanConfig getConfig() {
    if (null == config) {
      config = new BeanConfig(getClass().getName());
      doBinding();
    }
    return config;
  }

  /**
   * <p>
   * bind.
   * </p>
   * 
   * @param classes a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.inject.bind.BeanConfig.DefinitionBinder} object.
   */
  protected final DefinitionBinder bind(Class<?>... classes) {
    return config.bind(classes);
  }

  /**
   * Returns a reference definition based on Name;
   * 
   * @param name
   */
  protected final ReferenceValue ref(String name) {
    return new ReferenceValue(name);
  }

  protected final ReferenceValue ref(Class<?> clazz) {
    return new ReferenceValue(clazz.getName());
  }

  /**
   * Return new map entry
   * 
   * @param key
   * @param value
   */
  protected final Pair<?, ?> entry(Object key, Object value) {
    return Pair.of(key, value);
  }

  /**
   * Generate a inner bean definition
   * 
   * @param clazz
   */
  protected final Definition bean(Class<?> clazz) {
    Definition def = new Definition(clazz.getName(), clazz, Scope.SINGLETON.toString());
    def.beanName = clazz.getName() + "#" + Math.abs(System.identityHashCode(def));
    return def;
  }

  /**
   * Generate a list property
   * <p>
   * List singleton bean references with list(A.class,B.class) or list(ref("someBeanId"),C.class).<br>
   * List simple values with list("strValue1","strValue2")
   * 
   * @param datas
   */
  protected final List<?> list(Object... datas) {
    List<Object> items = CollectUtils.newArrayList(datas.length);
    for (Object obj : datas) {
      if (obj instanceof Class<?>) {
        items.add(buildInnerReference((Class<?>) obj));
      } else {
        items.add(obj);
      }
    }
    return items;
  }

  /**
   * Generate a list reference property
   * <p>
   * 
   * @param classes
   */
  protected final List<?> listref(Class<?>... classes) {
    List<Object> items = CollectUtils.newArrayList(classes.length);
    for (Class<?> clazz : classes) {
      items.add(new ReferenceValue(clazz.getName()));
    }
    return items;
  }

  /**
   * Generate a set property
   * <p>
   * List singleton bean references with set(A.class,B.class) or set(ref("someBeanId"),C.class).<br>
   * List simple values with set("strValue1","strValue2")
   * 
   * @param datas
   */
  protected final Set<?> set(Object... datas) {
    Set<Object> items = CollectUtils.newHashSet();
    for (Object obj : datas) {
      if (obj instanceof Class<?>) {
        items.add(buildInnerReference((Class<?>) obj));
      } else {
        items.add(obj);
      }
    }
    return items;
  }

  private ReferenceValue buildInnerReference(Class<?> clazz) {
    String targetBean = config.innerBeanName(clazz);
    config.add(new Definition(targetBean, clazz, Scope.SINGLETON.toString()));
    return new ReferenceValue(targetBean);
  }

  protected final Map<?, ?> map(Pair<?, ?>... entries) {
    Map<Object, Object> items = CollectUtils.newHashMap();
    for (Map.Entry<?, ?> entry : entries) {
      if (entry.getValue() instanceof Class<?>) {
        items.put(entry.getKey(), buildInnerReference((Class<?>) entry.getValue()));
      } else {
        items.put(entry.getKey(), entry.getValue());
      }
    }
    return items;
  }

  protected final Properties props(String... keyValuePairs) {
    Properties properties = new Properties();
    for (String pair : keyValuePairs) {
      int index = pair.indexOf('=');
      Assert.isTrue(index > 0, "property entry %1$s should contain =", pair);
      properties.put(pair.substring(0, index), pair.substring(index + 1));
    }
    return properties;
  }

  /**
   * <p>
   * bind.
   * </p>
   * 
   * @param beanName a {@link java.lang.String} object.
   * @param clazz a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.inject.bind.BeanConfig.DefinitionBinder} object.
   */
  protected final DefinitionBinder bind(String beanName, Class<?> clazz) {
    return config.bind(beanName, clazz);
  }

  /**
   * <p>
   * doBinding.
   * </p>
   */
  abstract protected void doBinding();

  /**
   * <p>
   * getObjectType.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public final Class<?> getObjectType() {
    return BeanConfig.class;
  }

  /**
   * <p>
   * isSingleton.
   * </p>
   * 
   * @return a boolean.
   */
  public final boolean isSingleton() {
    return true;
  }
}
