/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.config.property;

import java.util.ArrayList;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;

/**
 * 系统配置加载器
 * 
 * @author chaostone
 */
public class DefaultConfigFactory implements ConfigFactory {

	private List<ConfigProvider> providers = new ArrayList<ConfigProvider>();

	private List<ConfigListener> listeners = CollectUtils.newArrayList();

	private Config config;

	public void addConfigListener(ConfigListener listener) {
		listeners.add(listener);
	}

	public void addConfigProvider(ConfigProvider provider) {
		providers.add(provider);
	}

	public void multicast() {
		ConfigEvent e = new ConfigEvent(getConfig());
		for (ConfigListener listener : listeners) {
			listener.onConfigEvent(e);
		}
	}

	public void removeConfigListener(ConfigListener listener) {
		listeners.remove(listener);
	}

	public Config getConfig() {
		if (null == config) {
			reload();
		}
		return config;
	}

	public synchronized void reload() {
		config = new ConfigBean();
		for (ConfigProvider provider : providers) {
			config.set(provider.getConfig());
		}
		// multicastEvent(new ConfigEvent(this));
	}

	public void setProviders(List<ConfigProvider> providers) {
		this.providers = providers;
	}

}
