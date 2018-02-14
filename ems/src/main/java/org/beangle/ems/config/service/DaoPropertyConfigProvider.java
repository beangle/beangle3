/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.config.service;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.config.property.PropertyConfig;
import org.beangle.commons.conversion.impl.DefaultConversion;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.entity.Entity;
import org.beangle.ems.config.model.PropertyConfigItemBean;

public class DaoPropertyConfigProvider implements PropertyConfig.Provider {

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
        Class<?> idType = PropertyUtils.getPropertyType(itemClass, "id");
        value = entityDao.get(itemClass.getName(), (Serializable) DefaultConversion.Instance.convert(value, idType));
      }
      props.put(prop.getName(), value);
    }
    return props;
  }
}
