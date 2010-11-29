/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractTextResource implements TextResource {

	protected static final Object[] EMPTY_ARGS = new Object[0];

	public String getText(String key, String defaultValue) {
		String text = getText(key);
		if (null == text) { return defaultValue; }
		return text;
	}

	public String getText(String key, String defaultValue, String obj) {
		List<Object> args = new ArrayList<Object>(1);
		args.add(obj);
		return getText(key, defaultValue, args);
	}

	public String getText(String key, List<Object> args) {
		Object[] params;
		if (null == args) {
			params = EMPTY_ARGS;
		} else {
			params = args.toArray();
		}
		return getText(key, params);
	}

	public String getText(String key, String defaultValue, List<Object> args) {
		Object[] params;
		if (null == args) {
			params = EMPTY_ARGS;
		} else {
			params = args.toArray();
		}
		return getText(key, defaultValue, params);
	}

	public String getText(String key, String defaultValue, Object[] args) {
		String text = getText(key, args);
		String defaultVal = defaultValue;
		if (null == text && null == defaultVal) {
			defaultVal = key;
		}
		if (null == text && defaultVal != null) {
			MessageFormat format = new MessageFormat(defaultVal);
			format.setLocale(getLocale());
			format.applyPattern(defaultVal);
			return format.format(args);
		}
		return text;
	}

}
