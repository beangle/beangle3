/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.config.spring;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.core.MethodParameter;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

public class AutoConfigProcessor implements BeanFactoryPostProcessor {
	private static final Logger logger = LoggerFactory.getLogger(AutoConfigProcessor.class);

	public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {
		if (!(factory instanceof BeanDefinitionRegistry)) return;
		BeanDefinitionRegistry registry = (BeanDefinitionRegistry) factory;
		String[] definitionNames = factory.getBeanNamesForType(BeanConfig.class);

		Map<String, BeanDefinition> newBeanDefinitions = CollectUtils.newHashMap();
		for (String name : definitionNames) {
			BeanConfig config = factory.getBean(name, BeanConfig.class);
			List<BeanConfig.Definition> definitions = config.getDefinitions();
			for (BeanConfig.Definition definition : definitions) {
				String beanName = definition.beanName;
				if (newBeanDefinitions.containsKey(beanName) || registry.containsBeanDefinition(beanName)) {
					logger.warn("Ingore exists bean definition {}", beanName);
				} else {
					BeanDefinition def = register(beanName, definition.clazz, definition.scope, registry);
					newBeanDefinitions.put(beanName, def);
				}
			}
		}
		autowire(registry, newBeanDefinitions);
	}

	/**
	 * @param beanName
	 * @param clazz
	 * @param registry
	 * @param definition
	 */
	private BeanDefinition register(String beanName, Class<?> clazz, String scope,
			BeanDefinitionRegistry registry) {
		GenericBeanDefinition def = new GenericBeanDefinition();
		def.setBeanClass(clazz);
		def.setScope(scope);
		registry.registerBeanDefinition(beanName, def);
		logger.debug("Register definition {} for {}", beanName, clazz);
		return def;
	}

	protected void autowire(BeanDefinitionRegistry registry, Map<String, BeanDefinition> definitions) {
		for (Map.Entry<String, BeanDefinition> entry : definitions.entrySet()) {
			String beanName = entry.getKey();
			BeanDefinition mbd = entry.getValue();
			autowire(beanName, mbd, registry, (ConfigurableListableBeanFactory) registry);
		}
	}

	protected void autowire(String beanName, BeanDefinition mbd, BeanDefinitionRegistry registry,
			ConfigurableListableBeanFactory factory) {
		Map<String, PropertyDescriptor> properties = unsatisfiedNonSimpleProperties(mbd);
		for (Map.Entry<String, PropertyDescriptor> entry : properties.entrySet()) {
			String propertyName = entry.getKey();
			PropertyDescriptor pd = entry.getValue();
			if (Object.class.equals(pd.getPropertyType())) continue;
			MethodParameter methodParam = BeanUtils.getWriteMethodParameter(pd);
			String[] beanNames = factory.getBeanNamesForType(methodParam.getParameterType());
			boolean binded = false;
			if (beanNames.length == 1) {
				mbd.getPropertyValues().add(propertyName, new RuntimeBeanReference(beanNames[0]));
				binded = true;
			} else if (beanNames.length > 1) {
				for (String name : beanNames) {
					if (name.equals(propertyName)) {
						binded = true;
						mbd.getPropertyValues().add(propertyName, new RuntimeBeanReference(propertyName));
					}
				}
			}
			if (!binded) logger.debug("expected single bean but found {} : {}", beanNames.length,
					StringUtils.arrayToCommaDelimitedString(beanNames));
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
