/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class BundleTextResource extends AbstractTextResource {

	private Locale locale;

	private ResourceBundle bundle;

	public Locale getLocale() {
		return locale;
	}

	public String getText(String key) {
		return bundle.getString(key);
	}

	public String getText(String key, Object[] args) {
		String text = bundle.getString(key);
		MessageFormat format = new MessageFormat(text);
		format.setLocale(locale);
		format.applyPattern(text);
		return format.format(args);
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public ResourceBundle getBundle() {
		return bundle;
	}

	public void setBundle(ResourceBundle bundle) {
		this.bundle = bundle;
	}

}
