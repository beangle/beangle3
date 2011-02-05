/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.template;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;

public class Theme {

	public static final String THEME=".ui_theme";
	
	public static final String DEFAULT_THEME="beangle";
	
	public Theme() {
		super();
		this.name = DEFAULT_THEME;
	}
	public Theme(String name) {
		super();
		this.name = name;
	}

	private final String name;

	public final static Map<Class<?>, String> defaultNames = CollectUtils.newHashMap();

	public String getTemplatePath(Class<?> clazz, String suffix) {
		StringBuilder sb = new StringBuilder(20);
		sb.append("/template/").append(name).append('/').append(getTemplateName(clazz))
				.append(suffix);
		return sb.toString();
	}

	public static String getTemplateName(Class<?> clazz) {
		String name = defaultNames.get(clazz);
		if (null == name) {
			name = StringUtils.uncapitalize(clazz.getSimpleName());
			defaultNames.put(clazz, name);
		}
		return name;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object obj) {
		return name.equals(obj.toString());
	}

	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
