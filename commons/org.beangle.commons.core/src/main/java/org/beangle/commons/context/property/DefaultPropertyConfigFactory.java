/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.property;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.FactoryBean;

/**
 * 系统配置加载器
 * 
 * @author chaostone
 * @version $Id: $
 */
public class DefaultPropertyConfigFactory implements PropertyConfigFactory, FactoryBean<PropertyConfig> {

  private List<PropertyConfigFactory.Provider> providers = new ArrayList<PropertyConfigFactory.Provider>();

  private PropertyConfig config = null;

  /** {@inheritDoc} */
  public void addConfigProvider(PropertyConfigFactory.Provider provider) {
    providers.add(provider);
  }

  /**
   * <p>
   * getObject.
   * </p>
   * 
   * @return a {@link org.beangle.commons.context.property.PropertyConfig} object.
   * @throws java.lang.Exception if any.
   */
  public PropertyConfig getObject() throws Exception {
    if (null == config) {
      config = new PropertyConfigBean();
      reload();
    }
    return config;
  }

  /**
   * <p>
   * getObjectType.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getObjectType() {
    return PropertyConfig.class;
  }

  /**
   * <p>
   * isSingleton.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isSingleton() {
    return true;
  }

  /**
   * <p>
   * Getter for the field <code>config</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.context.property.PropertyConfig} object.
   */
  public PropertyConfig getConfig() {
    return config;
  }

  /**
   * <p>
   * reload.
   * </p>
   */
  public synchronized void reload() {
    for (PropertyConfigFactory.Provider provider : providers) {
      config.set(provider.getConfig());
    }
    config.multicast();
  }

  /**
   * <p>
   * Setter for the field <code>providers</code>.
   * </p>
   * 
   * @param providers a {@link java.util.List} object.
   */
  public void setProviders(List<PropertyConfigFactory.Provider> providers) {
    this.providers = providers;
  }

}
