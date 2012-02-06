/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.context.spring;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.time.StopWatch;
import org.beangle.bean.Disposable;
import org.beangle.bean.Initializing;
import org.beangle.collection.CollectUtils;
import org.beangle.context.event.DefaultEventMulticaster;
import org.beangle.context.inject.BeanConfig;
import org.beangle.context.inject.BindModule;
import org.beangle.context.inject.BindRegistry;
import org.beangle.context.inject.ConfigResource;
import org.beangle.context.inject.Scope;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.MethodParameter;
import org.springframework.core.io.UrlResource;
import org.springframework.util.ClassUtils;

/**
 * 完成springbean的自动注册和再配置
 * 
 * @author chaostone
 */
public class SpringConfigProcessor implements BeanDefinitionRegistryPostProcessor {

	private static final Logger logger = LoggerFactory.getLogger(SpringConfigProcessor.class);

	private ConfigResource resource;

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry definitionRegistry)
			throws BeansException {
		// 自动注册
		autoconfig(definitionRegistry);
		// 再配置
		reconfig(definitionRegistry);
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
	}

	private void reconfig(BeanDefinitionRegistry registry) {
		if (null == resource || resource.isEmpty()) return;
		Set<String> beanNames = CollectUtils.newHashSet();
		BeanDefinitionReader reader = new BeanDefinitionReader();
		for (URL url : resource.getAllPaths()) {
			List<ReconfigBeanDefinitionHolder> holders = reader.load(new UrlResource(url));
			for (ReconfigBeanDefinitionHolder holder : holders) {
				if (holder.getConfigType().equals(ReconfigType.REMOVE)) {
				} else {
					BeanDefinition definition = null;
					try {
						definition = registry.getBeanDefinition(holder.getBeanName());
					} catch (NoSuchBeanDefinitionException e) {
						logger.warn("No bean {} to reconfig defined in {}.", holder.getBeanName(), url);
						continue;
					}
					String successName = mergeDefinition(definition, holder);
					if (null != successName) beanNames.add(successName);
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
		Map<String, BeanDefinition> newDefinitions = registerModules(registry);
		// should register after all beans
		registerBuildins(registry);
		autowire(newDefinitions, registry);
		lifecycle(registry, definitionRegistry);
		logger.debug("Auto register and wire bean [{}]", newDefinitions.keySet());
		logger.info("Auto register and wire {} beans using {} mills", newDefinitions.size(), watch.getTime());
	}

	protected void lifecycle(BindRegistry registry, BeanDefinitionRegistry definitionRegistry) {
		for (String name : registry.getBeanNames()) {
			if (name.startsWith("&")) continue;
			Class<?> clazz = registry.getBeanType(name);
			AbstractBeanDefinition def = (AbstractBeanDefinition) definitionRegistry.getBeanDefinition(name);
			if (Initializing.class.isAssignableFrom(clazz)
					&& !def.getPropertyValues().contains("init-method")) {
				def.setInitMethodName("init");
			}
			if (Disposable.class.isAssignableFrom(clazz)
					&& !def.getPropertyValues().contains("destroy-method")) {
				def.setDestroyMethodName("destroy");
			}
		}
	}

	protected void registerBuildins(BindRegistry registry) {
		// FIXME for listeners inject
		registerBean(DefaultEventMulticaster.class.getName(), DefaultEventMulticaster.class,
				Scope.SINGLETON.toString(), new HashMap<String, Object>(), registry);
	}

	/**
	 * 合并bean定义
	 * 
	 * @param target
	 * @param source
	 * @return
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

	protected Map<String, BeanDefinition> registerModules(BindRegistry registry) {
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
					BeanDefinition def = registerBean(beanName, definition.clazz, definition.scope,
							definition.getProperties(), registry);
					newBeanDefinitions.put(beanName, def);
				}
			}
		}
		return newBeanDefinitions;
	}

	/**
	 * @param beanName
	 * @param clazz
	 * @param registry
	 * @param definition
	 */
	protected BeanDefinition registerBean(String beanName, Class<?> clazz, String scope,
			Map<String, Object> properties, BindRegistry registry) {
		GenericBeanDefinition def = new GenericBeanDefinition();
		def.setBeanClass(clazz);
		def.setScope(scope);
		MutablePropertyValues mpv = new MutablePropertyValues();
		for (Map.Entry<String, Object> entry : properties.entrySet()) {
			mpv.add(entry.getKey(), entry.getValue());
		}
		def.setPropertyValues(mpv);
		registry.register(clazz, beanName, def);
		logger.debug("Register definition {} for {}", beanName, clazz);
		return def;
	}

	protected void autowire(Map<String, BeanDefinition> newBeanDefinitions, BindRegistry registry) {
		for (Map.Entry<String, BeanDefinition> entry : newBeanDefinitions.entrySet()) {
			String beanName = entry.getKey();
			BeanDefinition mbd = entry.getValue();
			autowireBean(beanName, mbd, registry);
		}
	}

	protected void autowireBean(String beanName, BeanDefinition mbd, BindRegistry registry) {
		Map<String, PropertyDescriptor> properties = unsatisfiedNonSimpleProperties(mbd);
		for (Map.Entry<String, PropertyDescriptor> entry : properties.entrySet()) {
			String propertyName = entry.getKey();
			PropertyDescriptor pd = entry.getValue();
			if (Object.class.equals(pd.getPropertyType())) continue;
			MethodParameter methodParam = BeanUtils.getWriteMethodParameter(pd);
			List<String> beanNames = registry.getBeanNames(methodParam.getParameterType());
			boolean binded = false;
			if (beanNames.size() == 1) {
				mbd.getPropertyValues().add(propertyName, new RuntimeBeanReference(beanNames.get(0)));
				binded = true;
			} else if (beanNames.size() > 1) {
				for (String name : beanNames) {
					if (name.equals(propertyName)) {
						mbd.getPropertyValues().add(propertyName, new RuntimeBeanReference(propertyName));
						binded = true;
						break;
					}
				}
			}
			if (!binded) {
				if (beanNames.isEmpty()) {
					logger.debug(beanName + "'s " + propertyName + "  cannot  found candidate bean");
				} else {
					logger.warn(beanName + "'s " + propertyName + " expected single bean but found {} : {}",
							beanNames.size(), beanNames);
				}
			}
		}
	}

	private static boolean isExcludedFromDependencyCheck(PropertyDescriptor pd) {
		Method wm = pd.getWriteMethod();
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

	private Map<String, PropertyDescriptor> unsatisfiedNonSimpleProperties(BeanDefinition mbd) {
		Map<String, PropertyDescriptor> properties = CollectUtils.newHashMap();
		PropertyValues pvs = mbd.getPropertyValues();
		Class<?> clazz = null;
		try {
			clazz = Class.forName(mbd.getBeanClassName());
		} catch (ClassNotFoundException e) {
			logger.error("Class " + mbd.getBeanClassName() + "not found", e);
			return properties;
		}
		PropertyDescriptor[] pds = PropertyUtils.getPropertyDescriptors(clazz);
		for (PropertyDescriptor pd : pds) {
			if (pd.getWriteMethod() != null && !isExcludedFromDependencyCheck(pd)
					&& !pvs.contains(pd.getName()) && !BeanUtils.isSimpleProperty(pd.getPropertyType())) {
				properties.put(pd.getName(), pd);
			}
		}
		return properties;
	}

	public void setResource(ConfigResource resource) {
		this.resource = resource;
	}

}
