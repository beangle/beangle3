/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.config.service;

import java.util.List;
import java.util.Properties;

import org.beangle.commons.property.PropertyConfigFactory;
import org.beangle.ems.config.model.PropertyConfigItemBean;
import org.beangle.model.Entity;
import org.beangle.model.persist.EntityDao;

public class DaoPropertyConfigProvider implements PropertyConfigFactory.Provider {

	private EntityDao entityDao;

	public void setEntityDao(EntityDao entityDao) throws ClassNotFoundException {
		this.entityDao = entityDao;
	}

	public Properties getConfig() {
		Properties props = new Properties();
		List<PropertyConfigItemBean> rs = entityDao.getAll(PropertyConfigItemBean.class);
		for (final PropertyConfigItemBean prop : rs) {
			Class<?> itemClass = null;
			try {
				itemClass = Class.forName(prop.getType());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			Object value = prop.getValue();
			if (null != itemClass && Entity.class.isAssignableFrom(itemClass)) {
				value = entityDao.get(itemClass, Long.valueOf(value.toString()));
			}
			props.put(prop.getName(), value);
		}
		return props;
	}
}
