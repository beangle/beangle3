/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.action;

import java.util.Locale;

import org.beangle.commons.text.AbstractTextResource;
import org.beangle.commons.text.TextResource;

import com.opensymphony.xwork2.LocaleProvider;
import com.opensymphony.xwork2.TextProvider;

public class ActionTextResource extends AbstractTextResource implements TextResource {

	TextProvider textProvider;

	LocaleProvider localeProvider;

	public ActionTextResource(TextProvider textProvider, LocaleProvider localeProvider) {
		super();
		this.textProvider = textProvider;
		this.localeProvider = localeProvider;
	}

	public Locale getLocale() {
		return localeProvider.getLocale();
	}

	public String getText(String key, Object[] args) {
		String[] params = new String[args.length];
		for (int i = 0; i < args.length; i++) {
			params[i] = String.valueOf(args[i]);
		}
		return textProvider.getText(key, params);
	}

	public String getText(String key) {
		return textProvider.getText(key);
	}

	public void setLocale(Locale locale) {

	}

}
