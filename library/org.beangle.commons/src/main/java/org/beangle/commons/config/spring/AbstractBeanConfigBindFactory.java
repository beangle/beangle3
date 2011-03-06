/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.config.spring;

import org.beangle.commons.config.spring.BeanConfig.DefinitionBinder;
import org.springframework.beans.factory.FactoryBean;

public abstract class AbstractBeanConfigBindFactory implements FactoryBean<BeanConfig> {

	protected BeanConfig config;

	public BeanConfig getObject() throws Exception {
		if (null == config) {
			config = new BeanConfig();
			doBinding();
		}
		return config;
	}

	protected DefinitionBinder bind(Class<?>... classes) {
		return config.bind(classes);
	}

	protected DefinitionBinder bind(String beanName, Class<?> clazz) {
		return config.bind(beanName, clazz);
	}

	abstract protected void doBinding();

	public Class<?> getObjectType() {
		return BeanConfig.class;
	}

	public boolean isSingleton() {
		return true;
	}
}
