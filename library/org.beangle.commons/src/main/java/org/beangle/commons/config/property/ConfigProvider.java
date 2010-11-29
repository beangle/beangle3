/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.config.property;

import java.util.Properties;

/**
 * 系统配置加载器
 * 
 * @author chaostone
 */
public interface ConfigProvider {

	public Properties getConfig();

}
