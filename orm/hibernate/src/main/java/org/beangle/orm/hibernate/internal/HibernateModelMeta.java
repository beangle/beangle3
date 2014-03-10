/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.orm.hibernate.internal;

import java.util.Map;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.entity.metadata.impl.DefaultModelMeta;
import org.hibernate.SessionFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Building model from Hibernate.
 * 
 * @author chaostone
 * @since 2.0
 */
public class HibernateModelMeta extends DefaultModelMeta implements ApplicationContextAware, Initializing {

  ApplicationContext context;

  public void init() throws Exception {
    HibernateEntityContext entityContext = new HibernateEntityContext();
    Map<String, SessionFactory> factories = context.getBeansOfType(SessionFactory.class);
    for (Map.Entry<String, SessionFactory> entry : factories.entrySet()) {
      entityContext.initFrom(entry.getValue());
    }
    setContext(entityContext);
    Model.setMeta(this);
    context = null;
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    context = applicationContext;
  }

}
