/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.context.property;

import java.util.Properties;

/**
 * 配置工厂
 * 
 * @author chaostone
 */
public interface PropertyConfigFactory {

	public PropertyConfig getConfig();

	public void reload();

	public void addConfigProvider(Provider provider);

	public interface Provider {
		public Properties getConfig();
	}
}
