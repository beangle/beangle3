/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.metadata;

import java.io.Serializable;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.dao.Entity;
import org.beangle.commons.lang.Throwables;

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

  private String idPropertyName;

  /**
   * <p>
   * Constructor for EntityType.
   * </p>
   */
  public EntityType() {
    super();
  }

  /**
   * <p>
   * Constructor for EntityType.
   * </p>
   * 
   * @param entityName a {@link java.lang.String} object.
   * @param entityClass a {@link java.lang.Class} object.
   */
  public EntityType(String entityName, Class<?> entityClass) {
    super();
    this.entityName = entityName;
    this.entityClass = entityClass;
  }

  /**
   * <p>
   * Constructor for EntityType.
   * </p>
   * 
   * @param entityClass a {@link java.lang.Class} object.
   */
  public EntityType(Class<?> entityClass) {
    super();
    this.entityClass = entityClass;
    this.entityName = entityClass.getName();
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
   * {@inheritDoc} Get the type of a particular (named) property
   */
  public Type getPropertyType(String property) {
    Type type = (Type) propertyTypes.get(property);
    if (null == type) {
      Class<?> propertyType = ReflectHelper.getProperty(entityClass, property);
      if (null != propertyType) {
        if (Entity.class.isAssignableFrom(propertyType)) {
          type = new EntityType(propertyType);
        }
        if (propertyType.isInterface()) {
          type = Model.getEntityType(propertyType.getName());
        }
        if (null == type) {
          type = new IdentifierType(propertyType);
        }
      }
    }
    if (null == type) {
      logger.error("{} doesn't contains property {}", entityName, property);
    }
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

  /**
   * <p>
   * Getter for the field <code>idPropertyName</code>.
   * </p>
   * 
   * @return a {@link java.lang.String} object.
   */
  public String getIdPropertyName() {
    return idPropertyName;
  }

  /**
   * <p>
   * Setter for the field <code>idPropertyName</code>.
   * </p>
   * 
   * @param idName a {@link java.lang.String} object.
   */
  public void setIdPropertyName(String idName) {
    this.idPropertyName = idName;
  }

  /**
   * <p>
   * getIdClass.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  @SuppressWarnings("unchecked")
  public Class<? extends Serializable> getIdClass() {
    if (null == this.idPropertyName) return null;
    else try {
      return (Class<? extends Serializable>) PropertyUtils.getPropertyType(entityClass, idPropertyName);
    } catch (Exception e) {
      throw Throwables.propagate(e);
    }
  }
}
