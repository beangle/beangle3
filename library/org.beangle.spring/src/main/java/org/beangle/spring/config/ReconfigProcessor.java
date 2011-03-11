/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.config;

import java.net.URL;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.core.io.UrlResource;

public class ReconfigProcessor implements BeanDefinitionRegistryPostProcessor {
	private static final Logger logger = LoggerFactory.getLogger(ReconfigProcessor.class);

	private ConfigResource resource;

	private List<String> beanNames = CollectUtils.newArrayList();

	public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
		if (null == resource || resource.isEmpty()) return;
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
						logger.warn("reconfig error,no such bean {}.defined in {}.", holder.getBeanName(),
								url);
						continue;
					}
					mergeDefinition(definition, holder);
				}
			}
		}
		if (!beanNames.isEmpty()) {
			logger.info("Reconfig bean : {}", beanNames);
		}
	}

	public void mergeDefinition(BeanDefinition target, ReconfigBeanDefinitionHolder source) {
		if (null == target.getBeanClassName()) {
			logger.warn("ingore bean definition {} for without class", source.getBeanName());
			return;
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
		beanNames.add(source.getBeanName());
		logger.debug("Reconfig bean {} ", source.getBeanName());
	}

	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

	}

	public ConfigResource getResource() {
		return resource;
	}

	public void setResource(ConfigResource location) {
		this.resource = location;
	}
}
