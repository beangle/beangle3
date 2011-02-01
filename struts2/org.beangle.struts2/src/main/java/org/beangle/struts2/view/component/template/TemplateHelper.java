/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component.template;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;

public class TemplateHelper {

	public static Map<Class<?>, String> defaultNames = CollectUtils.newHashMap();

	public static String buildFullName(String theme, String name) {
		StringBuilder sb = new StringBuilder(20);
		sb.append("/template/").append(theme).append('/').append(name);
		return sb.toString();
	}

	public static String buildFullName(String theme, Class<?> clazz) {
		StringBuilder sb = new StringBuilder(20);
		sb.append("/template/").append(theme).append('/').append(getTemplateName(clazz));
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
}
