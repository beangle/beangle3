/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.context.spring;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.Validate;
import org.beangle.collection.CollectUtils;
import org.beangle.context.inject.BindRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.util.ClassUtils;

public class SpringBindRegistry implements BindRegistry {

	private static final Logger logger = LoggerFactory.getLogger(SpringBindRegistry.class);

	protected Map<String, Class<?>> nameTypes = CollectUtils.newHashMap();

	protected Map<Class<?>, List<String>> typeNames = CollectUtils.newHashMap();

	protected final BeanDefinitionRegistry definitionRegistry;

	public Set<String> getBeanNames() {
		return nameTypes.keySet();
	}

	public SpringBindRegistry(BeanDefinitionRegistry registry) {
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
							String nestedClassName = ((BeanDefinitionHolder) pv.getValue())
									.getBeanDefinition().getBeanClassName();
							if (null != nestedClassName) {
								register(
										ClassUtils.forName(nestedClassName,
												ClassUtils.getDefaultClassLoader()), name);
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
	}

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

	public Class<?> getBeanType(String beanName) {
		return nameTypes.get(beanName);
	}

	public boolean contains(String beanName) {
		return nameTypes.containsKey(beanName);
	}

	public void register(Class<?> type, String name, Object... args) {
		Validate.notNull(type, "name's class is null");
		nameTypes.put(name, type);
		if (args.length > 0) {
			definitionRegistry.registerBeanDefinition(name, (BeanDefinition) args[0]);
		}
	}

}
