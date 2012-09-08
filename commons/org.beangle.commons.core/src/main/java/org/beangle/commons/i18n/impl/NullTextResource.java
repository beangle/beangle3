/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n.impl;

import java.util.Locale;

import org.beangle.commons.i18n.TextResource;

/**
 * <p>
 * NullTextResource class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class NullTextResource implements TextResource {

  /** {@inheritDoc} */
  public String getText(String key) {
    return key;
  }

  public String getText(String key, String defaultValue, Object... obj) {
    return key;
  }

  public Locale getLocale() {
    return null;
  }

}
