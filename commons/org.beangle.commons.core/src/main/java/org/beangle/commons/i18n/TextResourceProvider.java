/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n;

import java.util.Locale;

/**
 * TextResource provider
 * 
 * @author chaostone
 * @since 3.0.2
 */
public interface TextResourceProvider {

  /**
   * Return text resource;
   * 
   * @param locale default could be null.
   */
  TextResource getTextResource(Locale locale);

}
