/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.property;

import java.util.Properties;

/**
 * 配置工厂
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface PropertyConfigFactory {

  /**
   * <p>
   * getConfig.
   * </p>
   * 
   * @return a {@link org.beangle.commons.context.property.PropertyConfig} object.
   */
  public PropertyConfig getConfig();

  /**
   * <p>
   * reload.
   * </p>
   */
  public void reload();

  /**
   * <p>
   * addConfigProvider.
   * </p>
   * 
   * @param provider a {@link org.beangle.commons.context.property.PropertyConfigFactory.Provider}
   *          object.
   */
  public void addConfigProvider(Provider provider);

  public interface Provider {
    public Properties getConfig();
  }
}
