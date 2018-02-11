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
package org.beangle.inject.spring.config;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.beangle.commons.bean.Disposable;
import org.beangle.commons.bean.Initializing;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.event.EventListener;
import org.beangle.commons.inject.Resources;
import org.beangle.commons.inject.Scope;
import org.beangle.commons.inject.bind.BeanConfig;
import org.beangle.commons.inject.bind.BeanConfig.Definition;
import org.beangle.commons.inject.bind.BeanConfig.ReferenceValue;
import org.beangle.commons.inject.bind.BindModule;
import org.beangle.commons.inject.bind.BindRegistry;
import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.lang.reflect.Reflections;
import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.inject.spring.SpringEventMulticaster;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.beans.factory.support.ManagedList;
import org.springframework.beans.factory.support.ManagedMap;
import org.springframework.beans.factory.support.ManagedProperties;
import org.springframework.beans.factory.support.ManagedSet;
import org.springframework.core.io.UrlResource;

/**
 * 完成springbean的自动注册和再配置
 *
 * @author chaostone
 * @version $Id: $
 */
public class SpringConfigProcessor implements BeanDefinitionRegistryPostProcessor {

  private static final Logger logger = LoggerFactory.getLogger(SpringConfigProcessor.class);

  private Resources reconfigResources;

  /**
   * Automate register and wire bean<br/>
   * Reconfig beans
   */
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry definitionRegistry)
      throws BeansException {
    // 自动注册
    BindRegistry registry = new SpringBindRegistry(definitionRegistry);
    Map<String, BeanDefinition> newDefinitions = registerModules(registry);
    // should register after all beans
    registerBuildins(registry);
    // 再配置
    reconfig(definitionRegistry, registry);
    lifecycle(registry, definitionRegistry);
    autowire(newDefinitions, registry);
  }

  public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
    factory.registerCustomEditor(Resources.class, ResourcesEditor.class);
  }

  private void reconfig(BeanDefinitionRegistry registry, BindRegistry bindRegistry) {
    Stopwatch watch = new Stopwatch(true);
    if (null == reconfigResources || reconfigResources.isEmpty()) return;
    Set<String> beanNames = CollectUtils.newHashSet();
    BeanDefinitionReader reader = new BeanDefinitionReader();
    for (URL url : reconfigResources.getAllPaths()) {
      List<ReconfigBeanDefinitionHolder> holders = reader.load(new UrlResource(url));
      for (ReconfigBeanDefinitionHolder holder : holders) {
        // choose primary key
        if (holder.getConfigType().equals(ReconfigType.PRIMARY)) {
          Class<?> clazz = ClassLoaders.loadClass(holder.getBeanDefinition().getBeanClassName());
          if (clazz.isInterface()) {
            List<String> names = bindRegistry.getBeanNames(clazz);
            boolean finded = names.contains(holder.getBeanName());
            if (finded) {
              for (String name : names) {
                BeanDefinition bd = registry.getBeanDefinition(name);
                bd.setPrimary(name.equals(holder.getBeanName()));
              }
            }
          }
          // lets do property update and merge.
          holder.setConfigType(ReconfigType.UPDATE);
          holder.getBeanDefinition().setBeanClassName(null);
        }

        if (holder.getConfigType().equals(ReconfigType.UPDATE)) {
          if (registry.containsBeanDefinition(holder.getBeanName())) {
            BeanDefinition definition = registry.getBeanDefinition(holder.getBeanName());
            String successName = mergeDefinition(definition, holder);
            if (null != successName) beanNames.add(successName);
          } else {
            logger.warn("No bean {} to reconfig defined in {}.", holder.getBeanName(), url);
            continue;
          }
        }
      }
    }
    if (!beanNames.isEmpty()) logger.info("Reconfig {} in {}", beanNames, watch);
  }

  /**
   * <p>
   * lifecycle.
   * </p>
   *
   * @param registry a {@link org.beangle.commons.inject.bind.BindRegistry} object.
   * @param definitionRegistry a
   *          {@link org.springframework.beans.factory.support.BeanDefinitionRegistry} object.
   */
  protected void lifecycle(BindRegistry registry, BeanDefinitionRegistry definitionRegistry) {
    for (String name : registry.getBeanNames()) {
      Class<?> clazz = registry.getBeanType(name);

      String springName = name;
      if (springName.startsWith("&")) springName = springName.substring(1);
      if (!definitionRegistry.containsBeanDefinition(springName)) continue;

      AbstractBeanDefinition def = (AbstractBeanDefinition) definitionRegistry.getBeanDefinition(springName);
      if (Initializing.class.isAssignableFrom(clazz) && null == def.getInitMethodName()
          && !def.getPropertyValues().contains("init-method")) {
        def.setInitMethodName("init");
      }
      if (Disposable.class.isAssignableFrom(clazz) && null == def.getDestroyMethodName()
          && !def.getPropertyValues().contains("destroy-method")) {
        def.setDestroyMethodName("destroy");
      }
    }
  }

  /**
   * <p>
   * registerBuildins.
   * </p>
   *
   * @param registry a {@link org.beangle.commons.inject.bind.BindRegistry} object.
   */
  protected void registerBuildins(BindRegistry registry) {
    List<String> listenerBeans = registry.getBeanNames(EventListener.class);
    Definition eventMulticaster = new Definition(SpringEventMulticaster.class.getName(),
        SpringEventMulticaster.class, Scope.SINGLETON.toString());
    eventMulticaster.property("listenerBeans", listenerBeans);

    registerBean(eventMulticaster, registry);
  }

  /**
   * 合并bean定义
   *
   * @param target a {@link org.springframework.beans.factory.config.BeanDefinition} object.
   * @param source a
   *          {@link org.beangle.inject.spring.config.context.spring.ReconfigBeanDefinitionHolder}
   *          object.
   * @return a {@link java.lang.String} object.
   */
  protected String mergeDefinition(BeanDefinition target, ReconfigBeanDefinitionHolder source) {
    if (null == target.getBeanClassName()) {
      logger.warn("ingore bean definition {} for without class", source.getBeanName());
      return null;
    }
    // 当类型变化后,删除原有配置
    if (null != source.getBeanDefinition().getBeanClassName()
        && !source.getBeanDefinition().getBeanClassName().equals(target.getBeanClassName())) {
      target.setBeanClassName(source.getBeanDefinition().getBeanClassName());
      for (PropertyValue pv : target.getPropertyValues().getPropertyValues()) {
        target.getPropertyValues().removePropertyValue(pv);
      }
      target.getConstructorArgumentValues().clear();
    }
    MutablePropertyValues pvs = source.getBeanDefinition().getPropertyValues();
    for (PropertyValue pv : pvs.getPropertyValueList()) {
      String name = pv.getName();
      target.getPropertyValues().addPropertyValue(name, pv.getValue());
      logger.debug("config {}.{} = {}", new Object[] { source.getBeanName(), name, pv.getValue() });
    }
    logger.debug("Reconfig bean {} ", source.getBeanName());
    return source.getBeanName();
  }

  /**
   * <p>
   * findRegistedModules.
   * </p>
   *
   * @param registry a {@link org.beangle.commons.inject.bind.BindRegistry} object.
   * @return a {@link java.util.Map} object.
   */
  protected Map<String, BeanDefinition> registerModules(BindRegistry registry) {
    Stopwatch watch = new Stopwatch(true);
    List<String> modules = registry.getBeanNames(BindModule.class);
    Map<String, BeanDefinition> newBeanDefinitions = CollectUtils.newHashMap();
    for (String name : modules) {
      Class<?> beanClass = registry.getBeanType(name);
      BeanConfig config = null;
      try {
        config = ((BindModule) beanClass.newInstance()).getConfig();
      } catch (Exception e) {
        logger.error("class initialization error of  " + beanClass, e);
        continue;
      }
      List<BeanConfig.Definition> definitions = config.getDefinitions();
      for (BeanConfig.Definition definition : definitions) {
        String beanName = definition.beanName;
        if (registry.contains(beanName)) {
          logger.warn("Ingore exists bean definition {}", beanName);
        } else {
          BeanDefinition def = registerBean(definition, registry);
          newBeanDefinitions.put(beanName, def);
        }
      }
    }
    logger.info("Auto register {} beans in {}", newBeanDefinitions.size(), watch);
    return newBeanDefinitions;
  }

  private BeanDefinition convert(Definition definition) {
    GenericBeanDefinition def = new GenericBeanDefinition();
    def.setBeanClass(definition.clazz);
    def.setScope(definition.scope);
    if (null != definition.initMethod) def.setInitMethodName(definition.initMethod);
    if (null != definition.destroyMethod) def.setDestroyMethodName(definition.destroyMethod);
    MutablePropertyValues mpv = new MutablePropertyValues();
    for (Map.Entry<String, Object> entry : definition.getProperties().entrySet()) {
      Object value = entry.getValue();
      if (value instanceof Collection<?>) {
        if (value instanceof List<?>) {
          List<Object> list = new ManagedList<Object>();
          for (Object item : ((List<?>) value)) {
            if (item instanceof ReferenceValue) list
                .add(new RuntimeBeanReference(((ReferenceValue) item).ref));
            else list.add(item);
          }
          value = list;
        }
        if (value instanceof Set<?>) {
          Set<Object> set = new ManagedSet<Object>();
          for (Object item : (Set<?>) value) {
            if (item instanceof ReferenceValue) set
                .add(new RuntimeBeanReference(((ReferenceValue) item).ref));
            else set.add(item);
          }
          value = set;
        }
      } else if (value instanceof Properties) {
        ManagedProperties props = new ManagedProperties();
        Enumeration<?> propertyNames = ((Properties) value).propertyNames();
        while (propertyNames.hasMoreElements()) {
          String key = propertyNames.nextElement().toString();
          props.put(new TypedStringValue(key), new TypedStringValue(((Properties) value).getProperty(key)));
        }
        value = props;
      } else if (value instanceof Map<?, ?>) {
        Map<Object, Object> maps = new ManagedMap<Object, Object>();
        for (Map.Entry<?, ?> item : ((Map<?, ?>) value).entrySet()) {
          if (item.getValue() instanceof ReferenceValue) maps.put(item.getKey(), new RuntimeBeanReference(
              ((ReferenceValue) item.getValue()).ref));
          else maps.put(item.getKey(), item.getValue());
        }
        value = maps;
      } else if (value instanceof Definition) {
        value = new BeanDefinitionHolder(convert((Definition) value), ((Definition) value).beanName);
      } else if (value instanceof ReferenceValue) {
        value = new RuntimeBeanReference(((ReferenceValue) value).ref);
      }
      mpv.add(entry.getKey(), value);
    }
    def.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_NO);
    def.setLazyInit(definition.lazyInit);
    def.setAbstract(definition.isAbstract());
    def.setParentName(definition.parent);
    def.setPrimary(definition.primary);
    if (null != definition.constructorArgs) {
      ConstructorArgumentValues cav = new ConstructorArgumentValues();
      for (Object arg : definition.constructorArgs)
        cav.addGenericArgumentValue(arg);
      def.setConstructorArgumentValues(cav);
    }
    def.setPropertyValues(mpv);
    return def;
  }

  /**
   * <p>
   * registerBean.
   * </p>
   *
   * @param def bean definition.
   * @param registry a {@link org.beangle.commons.inject.bind.BindRegistry} object.
   * @return a {@link org.springframework.beans.factory.config.BeanDefinition} object.
   */
  protected BeanDefinition registerBean(Definition def, BindRegistry registry) {
    BeanDefinition bd = convert(def);
    if ((def.isAbstract() && def.clazz == null) || FactoryBean.class.isAssignableFrom(def.clazz)) {
      try {
        Class<?> target = def.targetClass;

        if (null == target && def.clazz != null) target = ((FactoryBean<?>) def.clazz.newInstance())
            .getObjectType();
        Assert.isTrue(null != target || def.isAbstract(), "Concrete bean [%1$s] should has class.",
            def.beanName);

        registry.register(target, def.beanName, bd);
        // register concrete factory bean
        if (!def.isAbstract()) registry.register(def.clazz, "&" + def.beanName);
      } catch (Exception e) {
        logger.error("Cannot get Factory's Object Type {}", def.clazz);
      }
    } else {
      registry.register(def.clazz, def.beanName, bd);
    }
    logger.debug("Register definition {} for {}", def.beanName, def.clazz);
    return bd;
  }

  /**
   * <p>
   * autowire.
   * </p>
   *
   * @param newBeanDefinitions a {@link java.util.Map} object.
   * @param registry a {@link org.beangle.commons.inject.bind.BindRegistry} object.
   */
  protected void autowire(Map<String, BeanDefinition> newBeanDefinitions, BindRegistry registry) {
    Stopwatch watch = new Stopwatch(true);
    for (Map.Entry<String, BeanDefinition> entry : newBeanDefinitions.entrySet()) {
      autowireBean(entry.getKey(), entry.getValue(), registry);
    }
    logger.info("Auto wire {} beans in {}", newBeanDefinitions.size(), watch);
  }

  /**
   * <p>
   * autowireBean.
   * </p>
   *
   * @param beanName a {@link java.lang.String} object.
   * @param mbd a {@link org.springframework.beans.factory.config.BeanDefinition} object.
   * @param registry a {@link org.beangle.commons.inject.bind.BindRegistry} object.
   */
  protected void autowireBean(String beanName, BeanDefinition mbd, BindRegistry registry) {
    Map<String, Class<?>> properties = unsatisfiedNonSimpleProperties(mbd);
    for (Map.Entry<String, Class<?>> entry : properties.entrySet()) {
      String propertyName = entry.getKey();
      Class<?> propertyType = entry.getValue();
      List<String> beanNames = registry.getBeanNames(propertyType);
      boolean binded = false;
      if (beanNames.size() == 1) {
        mbd.getPropertyValues().add(propertyName, new RuntimeBeanReference(beanNames.get(0)));
        binded = true;
      } else if (beanNames.size() > 1) {
        // first autowire by primary
        for (String name : beanNames) {
          if (registry.isPrimary(name)) {
            mbd.getPropertyValues().add(propertyName, new RuntimeBeanReference(name));
            binded = true;
            break;
          }
        }
        // second autowire by name
        if (!binded) {
          for (String name : beanNames) {
            if (name.equals(propertyName)) {
              mbd.getPropertyValues().add(propertyName, new RuntimeBeanReference(name));
              binded = true;
              break;
            }
          }
        }
      }
      if (!binded) {
        if (beanNames.isEmpty()) {
          logger.debug("{}'s {} cannot found candidate beans.", beanName, propertyName);
        } else {
          logger.warn("{}'s {} expected single bean but found {} : {}", new Object[] { beanName,
              propertyName, beanNames.size(), beanNames });
        }
      }
    }
  }

  /**
   * <p>
   * Find unsatisfied properties
   * </p>
   * Unsatisfied property is empty value and not primary type and not starts with java.
   *
   * @param mbd
   */
  private Map<String, Class<?>> unsatisfiedNonSimpleProperties(BeanDefinition mbd) {
    Map<String, Class<?>> properties = CollectUtils.newHashMap();
    if (mbd.isAbstract()) return properties;
    Class<?> clazz = ClassLoaders.loadClass(((GenericBeanDefinition) mbd).getBeanClassName());

    PropertyValues pvs = mbd.getPropertyValues();
    for (Method m : Reflections.getBeanSetters(clazz)) {
      String propertyName = Strings.uncapitalize(m.getName().substring(3));
      Class<?> propertyType = m.getParameterTypes()[0];
      if (!pvs.contains(propertyName) && !propertyType.isArray()
          && !propertyType.getName().startsWith("java.")) {
        properties.put(propertyName, propertyType);
      }
    }
    return properties;
  }

  public void setReconfigResources(String reconfigResources) {
    ResourcesEditor re = new ResourcesEditor();
    re.setAsText(reconfigResources);
    this.reconfigResources = (Resources) re.getValue();
  }

}
