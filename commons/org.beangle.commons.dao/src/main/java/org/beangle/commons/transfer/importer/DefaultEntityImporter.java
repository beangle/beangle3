/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
    EntityType type = null;
    if (entityClass.isInterface()) {
      type = Model.getEntityType(entityClass.getName());
    } else {
      type = Model.getEntityType(entityClass);
    }
    entityTypes.put(alias, type);
  }

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
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

  /** {@inheritDoc} */
  protected String getEntityName(String attr) {
    return getEntityName();
  }

  /** {@inheritDoc} */
  public String processAttr(String attr) {
    return attr;
  }

  /** {@inheritDoc} */
  public void setCurrent(Object object) {
    current.put(alias, object);
  }

}
