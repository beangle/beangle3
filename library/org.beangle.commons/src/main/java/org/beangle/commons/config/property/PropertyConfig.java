/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.config.property;

import java.util.Properties;
import java.util.Set;

/**
 * 系统属性
 * 
 * @author chaostone
 */
public interface PropertyConfig {

	public Object get(String name);

	public void set(String name, Object value);

	public <T> T get(Class<T> clazz, String name);

	public void set(Properties properties);

	public Set<String> getNames();

	public void addConfigListener(PropertyConfigListener listener);

	public void removeConfigListener(PropertyConfigListener listener);

	public void multicast();

}
