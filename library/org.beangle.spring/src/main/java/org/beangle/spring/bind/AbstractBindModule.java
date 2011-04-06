/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.bind;

import org.beangle.spring.bind.BeanConfig.DefinitionBinder;

public abstract class AbstractBindModule implements BindModule {

	protected BeanConfig config;

	public final BeanConfig getConfig() {
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
