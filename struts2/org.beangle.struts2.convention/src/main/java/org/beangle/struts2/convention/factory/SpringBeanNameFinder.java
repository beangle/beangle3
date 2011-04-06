/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.convention.factory;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class SpringBeanNameFinder extends DefaultBeanNameFinder implements ApplicationContextAware,
		BeanNameFinder {

	ApplicationContext appContext;

	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		appContext = applicationContext;
	}

	public ApplicationContext getApplicationContext() {
		return appContext;
	}

	public String[] getBeanNames(Class<?> type) {
		String[] names = appContext.getBeanNamesForType(type);
		if (null == names || names.length == 0) {
			return new String[] { type.getName() };
		} else {
			List<String> lefts = CollectUtils.newArrayList();
			if (names.length > 1) {
				for (String name : names) {
					Class<?> beanType = appContext.getType(name);
					if (beanType.equals(type)) {
						lefts.add(name);
					}
				}
				names = lefts.toArray(new String[lefts.size()]);
			}
			return names;
		}
	}
}
