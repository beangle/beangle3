/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.config.property;

/**
 * 配置工厂
 * 
 * @author chaostone
 */
public interface ConfigFactory {

	public Config getConfig();

	public void reload();

	public void addConfigProvider(ConfigProvider provider);

	public void addConfigListener(ConfigListener listener);

	public void removeConfigListener(ConfigListener listener);

	public void multicast();

}
