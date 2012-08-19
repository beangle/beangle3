/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n.impl;

import java.util.Locale;

import org.beangle.commons.i18n.TextFormater;

/**
 * @author chaostone
 * @since 3.0.0
 */
public class DefaultTextFormater implements TextFormater {

  public String format(String text, Locale locale, Object... args) {
    return text;
  }

}
