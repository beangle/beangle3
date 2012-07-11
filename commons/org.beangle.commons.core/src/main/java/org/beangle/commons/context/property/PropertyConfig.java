/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.property;

import java.util.Properties;
import java.util.Set;

/**
 * 系统属性
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface PropertyConfig {

  /**
   * <p>
   * get.
   * </p>
   * 
   * @param name a {@link java.lang.String} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object get(String name);

  /**
   * <p>
   * set.
   * </p>
   * 
   * @param name a {@link java.lang.String} object.
   * @param value a {@link java.lang.Object} object.
   */
  public void set(String name, Object value);

  /**
   * <p>
   * get.
   * </p>
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @param name a {@link java.lang.String} object.
   * @param <T> a T object.
   * @return a T object.
   */
  public <T> T get(Class<T> clazz, String name);

  /**
   * <p>
   * set.
   * </p>
   * 
   * @param properties a {@link java.util.Properties} object.
   */
  public void set(Properties properties);

  /**
   * <p>
   * getNames.
   * </p>
   * 
   * @return a {@link java.util.Set} object.
   */
  public Set<String> getNames();

  /**
   * <p>
   * addConfigListener.
   * </p>
   * 
   * @param listener a {@link org.beangle.commons.context.property.PropertyConfigListener} object.
   */
  public void addConfigListener(PropertyConfigListener listener);

  /**
   * <p>
   * removeConfigListener.
   * </p>
   * 
   * @param listener a {@link org.beangle.commons.context.property.PropertyConfigListener} object.
   */
  public void removeConfigListener(PropertyConfigListener listener);

  /**
   * <p>
   * multicast.
   * </p>
   */
  public void multicast();

}
