/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.config.spring;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.FactoryBean;

public class BeanConfigFactory implements FactoryBean<BeanConfig> {

	BeanConfig config = new BeanConfig();

	BeanConfig.DefinitionBinder builder = new BeanConfig.DefinitionBinder(config);

	public BeanConfig getObject() throws Exception {
		return config;
	}

	public void setClasses(List<Class<?>> classes) {
		for (Class<?> clazz : classes) {
			builder.bind(clazz);
		}
	}

	public void setClassesMap(Map<String, Class<?>> newClasses) {
		for (Map.Entry<String, Class<?>> entry : newClasses.entrySet()) {
			builder.bind(entry.getKey(), entry.getValue());
		}
	}

	public void setUseShortName(boolean useShortName) {
		builder.shortName(useShortName);
	}

	public void setScope(String scopeStr) {
		Scope[] scopes = Scope.values();
		for (Scope scope : scopes) {
			if (scope.name.equals(scopeStr)) {
				builder.in(scope);
				return;
			}
		}
		throw new IllegalArgumentException(scopeStr);
	}

	public Class<?> getObjectType() {
		return BeanConfig.class;
	}

	public boolean isSingleton() {
		return true;
	}
}
