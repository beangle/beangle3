/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.bind;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.time.StopWatch;
import org.beangle.commons.collection.CollectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;

public class AutoConfigProcessor implements BeanDefinitionRegistryPostProcessor {
	private static final Logger logger = LoggerFactory.getLogger(AutoConfigProcessor.class);

	protected Map<String, BeanDefinition> newBeanDefinitions = CollectUtils.newHashMap();

	protected BindRegistry bindRegistry;

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		StopWatch watch = new StopWatch();
		watch.start();
		bindRegistry = new DefinitionBindRegistry(registry);
		register(registry);
		autowire();
		logger.debug("Auto register and wire bean [{}]", newBeanDefinitions.keySet());
		logger.info("Auto register and wire {} beans using {} mills", newBeanDefinitions.size(),
				watch.getTime());
		newBeanDefinitions.clear();
		bindRegistry = null;
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
	}

	protected void register(BeanDefinitionRegistry registry) {
		List<String> modules = bindRegistry.getBeanNames(BindModule.class);
		for (String name : modules) {
			Class<?> beanClass = bindRegistry.getBeanType(name);
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
				if (bindRegistry.contains(beanName)) {
					logger.warn("Ingore exists bean definition {}", beanName);
				} else {
					BeanDefinition def = register(beanName, definition.clazz, definition.scope, registry);
					newBeanDefinitions.put(beanName, def);
				}
			}
		}
	}

	/**
	 * @param beanName
	 * @param clazz
	 * @param registry
	 * @param definition
	 */
	protected BeanDefinition register(String beanName, Class<?> clazz, String scope,
			BeanDefinitionRegistry registry) {
		GenericBeanDefinition def = new GenericBeanDefinition();
		def.setBeanClass(clazz);
		def.setScope(scope);
		registry.registerBeanDefinition(beanName, def);
		bindRegistry.register(clazz, beanName);
		logger.debug("Register definition {} for {}", beanName, clazz);
		return def;
	}

	protected void autowire() {
		for (Map.Entry<String, BeanDefinition> entry : newBeanDefinitions.entrySet()) {
			String beanName = entry.getKey();
			BeanDefinition mbd = entry.getValue();
			autowire(beanName, mbd);
		}
	}

	protected void autowire(String beanName, BeanDefinition mbd) {
		Map<String, PropertyDescriptor> properties = unsatisfiedNonSimpleProperties(mbd);
		for (Map.Entry<String, PropertyDescriptor> entry : properties.entrySet()) {
			String propertyName = entry.getKey();
			PropertyDescriptor pd = entry.getValue();
			if (Object.class.equals(pd.getPropertyType())) continue;
			MethodParameter methodParam = BeanUtils.getWriteMethodParameter(pd);
			List<String> beanNames = bindRegistry.getBeanNames(methodParam.getParameterType());
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

	public static boolean isExcludedFromDependencyCheck(PropertyDescriptor pd) {
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

	protected Map<String, PropertyDescriptor> unsatisfiedNonSimpleProperties(BeanDefinition mbd) {
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

}
