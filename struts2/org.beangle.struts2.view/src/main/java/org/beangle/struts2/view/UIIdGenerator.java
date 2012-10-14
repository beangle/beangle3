/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view;

/**
 * @author chaostone
 * @since 3.0.0
 */
public interface UIIdGenerator {

  static final String GENERATOR = "UIIdGenerator";

  String generate(Class<?> clazz);
}
