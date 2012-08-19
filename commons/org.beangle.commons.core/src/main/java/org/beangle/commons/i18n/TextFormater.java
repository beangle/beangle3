/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.i18n;

import java.util.Locale;

/**
 * Text formater
 * @author chaostone
 * @since 3.0.0
 */
public interface TextFormater {

  String format(String text, Locale locale, Object... args);
}
