/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.spring;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.time.StopWatch;
import org.beangle.commons.bean.Disposable;
import org.beangle.commons.bean.Initializing;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.context.event.DefaultEventMulticaster;
import org.beangle.commons.context.inject.*;
import org.beangle.commons.context.inject.BeanConfig.Definition;
import org.beangle.commons.context.inject.BeanConfig.ListValue;
import org.beangle.commons.context.inject.BeanConfig.MapValue;
import org.beangle.commons.context.inject.BeanConfig.PropertiesValue;
import org.beangle.commons.context.inject.BeanConfig.ReferenceValue;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.reflect.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.*;
import org.springframework.core.io.UrlResource;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * 完成springbean的自动注册和再配置
 * 
 * @author chaostone
 * @version $Id: $
 */
public class SpringConfigProcessor implements BeanDefinitionRegistryPostProcessor {

  private static final Logger logger = LoggerFactory.getLogger(SpringConfigProcessor.class);

  private Resources resources;

  /** {@inheritDoc} */
  public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry definitionRegistry)
      throws BeansException {
    logger.info("Auto register and wire beans starting...");
    // 自动注册
    autoconfig(definitionRegistry);
    // 再配置
    reconfig(definitionRegistry);
  }

  /** {@inheritDoc} */
  public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
  }

  private void reconfig(BeanDefinitionRegistry registry) {
    if (null == resources || resources.isEmpty()) return;
    Set<String> beanNames = CollectUtils.newHashSet();
    BeanDefinitionReader reader = new BeanDefinitionReader();
    for (URL url : resources.getAllPaths()) {
      List<ReconfigBeanDefinitionHolder> holders = reader.load(new UrlResource(url));
      for (ReconfigBeanDefinitionHolder holder : holders) {
        if (holder.getConfigType().equals(ReconfigType.REMOVE)) {
        } else {
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
    if (!beanNames.isEmpty()) {
      logger.info("Reconfig bean : {}", beanNames);
    }
  }

  private void autoconfig(BeanDefinitionRegistry definitionRegistry) {
    StopWatch watch = new StopWatch();
    watch.start();
    BindRegistry registry = new SpringBindRegistry(definitionRegistry);
    Map<String, BeanDefinition> newDefinitions = findRegistedModules(registry);
    // should register after all beans
    registerBuildins(registry);
    logger.info("Auto register {} beans using {} mills", newDefinitions.size(), watch.getTime());
    watch.reset();
    watch.start();
    autowire(newDefinitions, registry);
    lifecycle(registry, definitionRegistry);
    logger.debug("Auto register and wire bean [{}]", newDefinitions.keySet());
    logger.info("Auto wire {} beans using {} mills", newDefinitions.size(), watch.getTime());
  }

  /**
   * <p>
   * lifecycle.
   * </p>
   * 
   * @param registry a {@link org.beangle.commons.context.inject.BindRegistry} object.
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
   * @param registry a {@link org.beangle.commons.context.inject.BindRegistry} object.
   */
  protected void registerBuildins(BindRegistry registry) {
    // FIXME for listeners inject
    registerBean(new Definition(DefaultEventMulticaster.class.getName(), DefaultEventMulticaster.class,
        Scope.SINGLETON.toString()), registry);
  }

  /**
   * 合并bean定义
   * 
   * @param target a {@link org.springframework.beans.factory.config.BeanDefinition} object.
   * @param source a {@link org.beangle.commons.context.spring.ReconfigBeanDefinitionHolder} object.
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
   * @param registry a {@link org.beangle.commons.context.inject.BindRegistry} object.
   * @return a {@link java.util.Map} object.
   */
  protected Map<String, BeanDefinition> findRegistedModules(BindRegistry registry) {
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
    return newBeanDefinitions;
  }

  private BeanDefinition convert(Definition definition) {
    GenericBeanDefinition def = new GenericBeanDefinition();
    def.setBeanClass(definition.clazz);
    def.setScope(definition.scope);
    if (null != definition.initMethod) def.setInitMethodName(definition.initMethod);
    MutablePropertyValues mpv = new MutablePropertyValues();
    for (Map.Entry<String, Object> entry : definition.getProperties().entrySet()) {
      Object value = entry.getValue();
      if (value instanceof ListValue) {
        List<Object> list = new ManagedList<Object>();
        for (Object item : ((ListValue) value).items) {
          if (item instanceof ReferenceValue) list.add(new RuntimeBeanReference(((ReferenceValue) item).ref));
          else list.add(item);
        }
        value = list;
      } else if (value instanceof MapValue) {
        Map<Object, Object> maps = new ManagedMap<Object, Object>();
        for (Map.Entry<Object, Object> item : maps.entrySet()) {
          if (item.getValue() instanceof ReferenceValue) maps.put(item.getKey(), new RuntimeBeanReference(
              ((ReferenceValue) item.getValue()).ref));
          else maps.put(item.getKey(), item.getValue());
        }
        value = maps;
      } else if (value instanceof PropertiesValue) {
        ManagedProperties props = new ManagedProperties();
        for (Map.Entry<String, String> pentry : ((PropertiesValue) value).properties.entrySet())
          props.put(new TypedStringValue(pentry.getKey()), new TypedStringValue(pentry.getValue()));

        value = props;
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
    def.setPropertyValues(mpv);
    return def;
  }

  /**
   * <p>
   * registerBean.
   * </p>
   * 
   * @param beanName a {@link java.lang.String} object.
   * @param registry a {@link org.beangle.commons.context.inject.BindRegistry} object.
   * @return a {@link org.springframework.beans.factory.config.BeanDefinition} object.
   */
  protected BeanDefinition registerBean(Definition def, BindRegistry registry) {
    BeanDefinition bd = convert(def);
    if (FactoryBean.class.isAssignableFrom(def.clazz)) {
      try {
        Class<?> target = def.targetClass;

        if (null == target) target = ((FactoryBean<?>) def.clazz.newInstance()).getObjectType();
        Assert.isTrue(null != target || def.isAbstract(), "Concrete bean [" + def.beanName
            + "]should has class.");

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
   * @param registry a {@link org.beangle.commons.context.inject.BindRegistry} object.
   */
  protected void autowire(Map<String, BeanDefinition> newBeanDefinitions, BindRegistry registry) {
    for (Map.Entry<String, BeanDefinition> entry : newBeanDefinitions.entrySet()) {
      String beanName = entry.getKey();
      BeanDefinition mbd = entry.getValue();
      autowireBean(beanName, mbd, registry);
    }
  }

  /**
   * <p>
   * autowireBean.
   * </p>
   * 
   * @param beanName a {@link java.lang.String} object.
   * @param mbd a {@link org.springframework.beans.factory.config.BeanDefinition} object.
   * @param registry a {@link org.beangle.commons.context.inject.BindRegistry} object.
   */
  protected void autowireBean(String beanName, BeanDefinition mbd, BindRegistry registry) {
    Map<String, Class<?>> properties = unsatisfiedNonSimpleProperties(mbd);
    for (Map.Entry<String, Class<?>> entry : properties.entrySet()) {
      String propertyName = entry.getKey();
      Class<?> propertyType = entry.getValue();
      // if (Object.class.equals(propertyType)) continue;
      // MethodParameter methodParam = BeanUtils.getWriteMethodParameter(pd);
      List<String> beanNames = registry.getBeanNames(propertyType);
      boolean binded = false;
      if (beanNames.size() == 1) {
        mbd.getPropertyValues().add(propertyName, new RuntimeBeanReference(beanNames.get(0)));
        binded = true;
      } else if (beanNames.size() > 1) {
        // first autowire by name
        for (String name : beanNames) {
          if (name.equals(propertyName)) {
            mbd.getPropertyValues().add(propertyName, new RuntimeBeanReference(name));
            binded = true;
            break;
          }
        }
        // second autowire by primary
        if (!binded) {
          for (String name : beanNames) {
            if (registry.isPrimary(name)) {
              mbd.getPropertyValues().add(propertyName, new RuntimeBeanReference(name));
              binded = true;
              break;
            }
          }
        }
      }
      if (!binded) {
        if (beanNames.isEmpty()) {
          logger.debug(beanName + "'s " + propertyName + " cannot found candidate bean");
        } else {
          logger.warn(beanName + "'s " + propertyName + " expected single bean but found {} : {}",
              beanNames.size(), beanNames);
        }
      }
    }
  }

  private static boolean isExcludedFromDependencyCheck(Method wm) {
    if (wm == null) { return false; }
    if (!wm.getDeclaringClass().getName().contains("$$")) {
      // Not a CGLIB method so it's OK.
      return false;
    }
    // It was declared by CGLIB, but we might still want to autowire it
    // if it was actually declared by the superclass.
    Class<?> superclass = wm.getDeclaringClass().getSuperclass();
    return !ClassUtils.hasMethod(superclass, wm.getName(), wm.getParameterTypes());
  }

  /**
   * <p>
   * Find unsatisfied properties
   * </p>
   * Unsatisfied property is empty value and not primary type and not starts with java.
   * 
   * @param mbd
   * @return
   */
  private Map<String, Class<?>> unsatisfiedNonSimpleProperties(BeanDefinition mbd) {
    Map<String, Class<?>> properties = CollectUtils.newHashMap();
    Class<?> clazz = (Class<?>) ((GenericBeanDefinition) mbd).getBeanClass();
    if (mbd.isAbstract()) return properties;
    PropertyValues pvs = mbd.getPropertyValues();
    for (Method m : Reflections.getBeanSetters(clazz)) {
      String propertyName = Strings.uncapitalize(m.getName().substring(3));
      Class<?> propertyType = m.getParameterTypes()[0];
      if (!isExcludedFromDependencyCheck(m) && !pvs.contains(propertyName)
          && !BeanUtils.isSimpleProperty(propertyType) && !propertyType.getName().startsWith("java.")) {
        properties.put(propertyName, propertyType);
      }
    }
    return properties;
  }

  /**
   * <p>
   * Setter for the field <code>resource</code>.
   * </p>
   * 
   * @param resources a {@link org.beangle.commons.context.inject.Resources} object.
   */
  public void setResources(Resources resources) {
    this.resources = resources;
  }

}
