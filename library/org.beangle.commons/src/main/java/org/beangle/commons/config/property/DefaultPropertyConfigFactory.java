/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.config.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;

/**
 * 系统配置加载器
 * 
 * @author chaostone
 */
public class DefaultPropertyConfigFactory implements PropertyConfigFactory,FactoryBean<PropertyConfig> {

	private List<PropertyConfigFactory.Provider> providers = new ArrayList<PropertyConfigFactory.Provider>();

	private PropertyConfig config = new PropertyConfigBean();

	public void addConfigProvider(PropertyConfigFactory.Provider provider) {
		providers.add(provider);
	}

	public PropertyConfig getObject() throws Exception {
		return null;
	}

	public Class<?> getObjectType() {
		return PropertyConfig.class;
	}

	public boolean isSingleton() {
		return true;
	}

	public PropertyConfig getConfig() {
		return config;
	}

	public synchronized void reload() {
		for (PropertyConfigFactory.Provider provider : providers) {
			config.set(provider.getConfig());
		}
		config.multicast();
	}

	public void setProviders(List<PropertyConfigFactory.Provider> providers) {
		this.providers = providers;
	}

}
