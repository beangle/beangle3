/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.commons.transfer.importer;

import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;

/**
 * <p>
 * DefaultEntityImporter class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class DefaultEntityImporter extends MultiEntityImporter {

  private static final String alias = "_entity";

  /**
   * <p>
   * Constructor for DefaultEntityImporter.
   * </p>
   */
  public DefaultEntityImporter() {
    super();
  }

  /**
   * <p>
   * Constructor for DefaultEntityImporter.
   * </p>
   * 
   * @param entityClass a {@link java.lang.Class} object.
   */
  public DefaultEntityImporter(Class<?> entityClass) {
    if (null != entityClass) {
      EntityType type = null;
      if (entityClass.isInterface()) {
        type = Model.getType(entityClass.getName());
      } else {
        type = Model.getType(entityClass);
      }
      entityTypes.put(alias, type);
    }
  }

  protected EntityType getEntityType(String attr) {
    return (EntityType) entityTypes.get(alias);
  }

  /**
   * <p>
   * getEntityClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getEntityClass() {
    return ((EntityType) entityTypes.get(alias)).getEntityClass();
  }

  /**
   * <p>
   * getEntityName.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getEntityName() {
    return ((EntityType) entityTypes.get(alias)).getEntityName();
  }

  /**
   * <p>
   * setEntityClass.
   * </p>
   * 
   * @param entityClass a {@link java.lang.Class} object.
   */
  public void setEntityClass(Class<?> entityClass) {
    ((EntityType) entityTypes.get(alias)).setEntityClass(entityClass);
  }

  public Object getCurrent(String attr) {
    return getCurrent();
  }

  /**
   * <p>
   * getCurrent.
   * </p>
   * 
   * @return a {@link java.lang.Object} object.
   */
  public Object getCurrent() {
    return super.getCurrent(alias);
  }

  protected String getEntityName(String attr) {
    return getEntityName();
  }

  public String processAttr(String attr) {
    return attr;
  }

  public void setCurrent(Object object) {
    current.put(alias, object);
  }

}
