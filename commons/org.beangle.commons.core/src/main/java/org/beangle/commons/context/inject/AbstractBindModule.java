/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.inject;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.context.inject.BeanConfig.Definition;
import org.beangle.commons.context.inject.BeanConfig.DefinitionBinder;
import org.beangle.commons.context.inject.BeanConfig.ReferenceValue;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.Strings;
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
   * @return a {@link org.beangle.commons.context.inject.BeanConfig} object.
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
   * @return a {@link org.beangle.commons.context.inject.BeanConfig.DefinitionBinder} object.
   */
  protected DefinitionBinder bind(Class<?>... classes) {
    return config.bind(classes);
  }

  /**
   * Create a reference definition based on Name;
   * 
   * @param name
   * @return
   */
  protected ReferenceValue ref(String name) {
    return new ReferenceValue(name);
  }

  protected ReferenceValue ref(Class<?> clazz) {
    return new ReferenceValue(clazz.getName());
  }

  /**
   * Create Map Entry
   * 
   * @param left
   * @param right
   * @return
   */
  protected Pair<?, ?> entry(Object key, Object value) {
    return Pair.of(key, value);
  }

  /**
   * Generate a inner bean definition
   * 
   * @param clazz
   * @return
   */
  protected Definition bean(Class<?> clazz) {
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
   * @return
   */
  protected List<?> list(Object... datas) {
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
   * Generate a list property
   * <p>
   * List singleton bean references with list(A.class,B.class) or list(ref("someBeanId"),C.class).<br>
   * List simple values with list("strValue1","strValue2")
   * 
   * @param datas
   * @return
   */
  protected List<?> listref(Class<?>... datas) {
    List<Object> items = CollectUtils.newArrayList(datas.length);
    for (Class<?> obj : datas) {
      items.add(buildInnerReference((Class<?>) obj));
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
   * @return
   */
  protected Set<?> set(Object... datas) {
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

  protected Map<?, ?> map(Pair<?, ?>... entries) {
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

  protected Properties props(String... keyValuePairs) {
    Properties properties = new Properties();
    for (String pair : keyValuePairs) {
      Assert.isTrue(pair.indexOf('=') > 0, "property entry [" + pair + "] should contain =");
      properties.put(Strings.substringBefore(pair, "="), Strings.substringAfter(pair, "="));
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
   * @return a {@link org.beangle.commons.context.inject.BeanConfig.DefinitionBinder} object.
   */
  protected DefinitionBinder bind(String beanName, Class<?> clazz) {
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
  public Class<?> getObjectType() {
    return BeanConfig.class;
  }

  /**
   * <p>
   * isSingleton.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isSingleton() {
    return true;
  }
}
