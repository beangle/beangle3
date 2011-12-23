/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.freemarker;

import java.net.URL;

import org.apache.commons.lang.StringUtils;

import com.opensymphony.xwork2.util.ClassLoaderUtil;

import freemarker.cache.URLTemplateLoader;

/**
 * 搜索带有固定前缀下的资源，排除/org/和/com/
 * 
 * @author chaostone
 */
public class BeangleClassTemplateLoader extends URLTemplateLoader {

	String prefix = null;

	public BeangleClassTemplateLoader(String prefix) {
		super();
		setPrefix(prefix);
	}

	protected URL getURL(String name) {
		URL url = ClassLoaderUtil.getResource(name, getClass());
		if (null != prefix) {
			url = ClassLoaderUtil.getResource(prefix + name, getClass());
		}
		return url;
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String pre) {
		if (StringUtils.isBlank(pre)) {
			this.prefix = null;
		} else {
			this.prefix = pre.trim();
		}
		if (null != prefix) {
			if (prefix.equals("/")) {
				prefix = null;
			} else {
				if (!prefix.endsWith("/")) {
					prefix += "/";
				}
				if (prefix.startsWith("/")) {
					prefix = prefix.substring(1);
				}
			}
		}
	}
}
