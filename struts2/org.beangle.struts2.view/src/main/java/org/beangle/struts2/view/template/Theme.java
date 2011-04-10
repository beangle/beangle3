/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.template;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;

public class Theme {

	public static final String THEME = ".beangle_theme";

	public static final String INNER_THEME = ".inner_beangle_theme";

	public static final String DEFAULT_THEME = "xml";

	private final static Map<Class<?>, String> defaultNames = CollectUtils.newHashMap();

	private final String name;

	private String ui;

	private String uibase;

	public Theme() {
		this.name = DEFAULT_THEME;
	}

	public Theme(String name) {
		super();
		this.name = name;
	}

	public String iconurl(String name) {
		return iconurl(name, "16x16");
	}

	public String iconurl(String name, int size) {
		StringBuilder sb = new StringBuilder();
		sb.append(size).append('x').append(size);
		return iconurl(name, sb.toString());
	}

	public String iconurl(String name, String size) {
		StringBuilder sb = new StringBuilder(80);
		if (uibase.length() < 2) {
			sb.append("/static/themes/");
		} else {
			sb.append(uibase).append("/static/themes/");
		}
		sb.append(getUi()).append("/icons/").append(size);
		if (!name.startsWith("/")) sb.append('/');
		sb.append(name);
		return sb.toString();
	}

	public String cssurl(String name) {
		StringBuilder sb = new StringBuilder(80);
		if (uibase.length() < 2) {
			sb.append("/static/themes/");
		} else {
			sb.append(uibase).append("/static/themes/");
		}
		sb.append(getUi());
		if (!name.startsWith("/")) sb.append('/');
		sb.append(name);
		return sb.toString();
	}

	public String getTemplatePath(Class<?> clazz, String suffix) {
		StringBuilder sb = new StringBuilder(20);
		sb.append("/template/").append(name).append('/').append(getTemplateName(clazz)).append(suffix);
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

	public String getUi() {
		return ui;
	}

	public void setUi(String uitheme) {
		this.ui = uitheme;
	}

	public String getUibase() {
		return uibase;
	}

	public void setUibase(String uibase) {
		this.uibase = uibase;
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
