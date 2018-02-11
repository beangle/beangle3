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
package org.beangle.commons.entity.metadata;

import java.io.Serializable;
import java.util.Map;

import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.lang.Assert;

/**
 * <p>
 * EntityType class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class EntityType extends AbstractType {

  private String entityName;

  private Class<?> entityClass;

  private Map<String, Type> propertyTypes = CollectUtils.newHashMap();

  private String idName;

  /**
   * <p>
   * Constructor for EntityType.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   * @param entityClass a {@link java.lang.Class} object.
   */
  public EntityType(String entityName, Class<?> entityClass, String idName) {
    super();
    Assert.notNull(idName);
    Assert.notNull(entityName);
    Assert.notNull(entityClass);

    this.entityName = entityName;
    this.entityClass = entityClass;
    this.idName = idName;
    Class<?> clazz = PropertyUtils.getPropertyType(entityClass, idName);
    if (null != clazz) propertyTypes.put(idName, new IdentifierType(clazz));
  }

  public EntityType(String entityName, Class<?> entityClass, String idName, Type idType) {
    super();
    Assert.notNull(idName);
    Assert.notNull(entityName);
    Assert.notNull(entityClass);

    this.entityName = entityName;
    this.entityClass = entityClass;
    this.idName = idName;
    this.propertyTypes.put(idName, idType);
  }

  /**
   * <p>
   * Constructor for EntityType.
   * </p>
   * 
   * @param entityClass a {@link java.lang.Class} object.
   */
  public EntityType(Class<?> entityClass) {
    this(entityClass.getName(), entityClass, "id");
  }

  /**
   * <p>
   * isEntityType.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isEntityType() {
    return true;
  }

  /**
   * <p>
   * Getter for the field <code>entityClass</code>.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getEntityClass() {
    return entityClass;
  }

  /**
   * <p>
   * Setter for the field <code>entityClass</code>.
   * </p>
   * 
   * @param entityClass a {@link java.lang.Class} object.
   */
  public void setEntityClass(Class<?> entityClass) {
    this.entityClass = entityClass;
  }

  /**
   * <p>
   * Getter for the field <code>propertyTypes</code>.
   * </p>
   * 
   * @return a {@link java.util.Map} object.
   */
  public Map<String, Type> getPropertyTypes() {
    return propertyTypes;
  }

  /**
   * <p>
   * Setter for the field <code>propertyTypes</code>.
   * </p>
   * 
   * @param propertyTypes a {@link java.util.Map} object.
   */
  public void setPropertyTypes(Map<String, Type> propertyTypes) {
    this.propertyTypes = propertyTypes;
  }

  /**
   * <p>
   * Setter for the field <code>entityName</code>.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   */
  public void setEntityName(String entityName) {
    this.entityName = entityName;
  }

  /**
   * Get the type of a particular (named) property
   */
  public Type getPropertyType(String property) {
    Type type = (Type) propertyTypes.get(property);
    if (null == type) {
      Class<?> propertyType = PropertyUtils.getPropertyType(entityClass, property);
      if (null != propertyType) {
        if (Entity.class.isAssignableFrom(propertyType)) type = new EntityType(propertyType);
        if (propertyType.isInterface()) type = Model.getType(propertyType.getName());
        if (null == type) type = new IdentifierType(propertyType);
      }
    }
    if (null == type) logger.error("{} doesn't contains property {}", entityName, property);
    return type;
  }

  /**
   * The name of the entity
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getEntityName() {
    return entityName;
  }

  /**
   * <p>
   * getName.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getName() {
    return entityName;
  }

  /**
   * <p>
   * getReturnedClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getReturnedClass() {
    return entityClass;
  }

  public String getIdName() {
    return idName;
  }

  public void setIdName(String idName) {
    this.idName = idName;
  }

  /**
   * <p>
   * getIdClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  @SuppressWarnings("unchecked")
  public Class<? extends Serializable> getIdType() {
    Type type = propertyTypes.get(idName);
    return (Class<? extends Serializable>) (null != type ? type.getReturnedClass() : null);
  }
}
