/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.inject;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * BindRegistry interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface BindRegistry {

  /**
   * <p>
   * getBeanNames.
   * </p>
   * 
   * @param type a {@link java.lang.Class} object.
   * @return a {@link java.util.List} object.
   */
  List<String> getBeanNames(Class<?> type);

  /**
   * <p>
   * getBeanType.
   * </p>
   * 
   * @param beanName a {@link java.lang.String} object.
   * @return a {@link java.lang.Class} object.
   */
  Class<?> getBeanType(String beanName);

  /**
   * <p>
   * register.
   * </p>
   * 
   * @param type a {@link java.lang.Class} object.
   * @param name a {@link java.lang.String} object.
   * @param args a {@link java.lang.Object} object.
   */
  void register(Class<?> type, String name, Object... args);

  /**
   * <p>
   * contains.
   * </p>
   * 
   * @param beanName a {@link java.lang.String} object.
   * @return a boolean.
   */
  boolean contains(String beanName);

  /**
   * <p>
   * getBeanNames.
   * </p>
   * 
   * @return a {@link java.util.Set} object.
   */
  Set<String> getBeanNames();

  /**
   * <p>
   * Whether the bean is primary
   * </p>
   * 
   * @param name
   * @return
   */
  boolean isPrimary(String name);

}
