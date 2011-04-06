/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text;

import java.util.Locale;

public class NullTextResource extends AbstractTextResource {

	public Locale getLocale() {
		return null;
	}

	public String getText(String key, Object[] args) {
		return key;
	}

	public String getText(String key) {
		return key;
	}

	public void setLocale(Locale locale) {
	}

}
