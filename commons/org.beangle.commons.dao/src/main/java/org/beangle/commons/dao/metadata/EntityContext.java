/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.metadata;

/**
 * <p>
 * EntityContext interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface EntityContext {
  /**
   * 根据实体名查找实体类型
   * 
   * @param name a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.dao.metadata.Type} object.
   */
  public Type getType(String name);

  /**
   * 根据实体名查找实体类型
   * 
   * @param entityName a {@link java.lang.String} object.
   * @return null, if cannot find entityType
   */
  public EntityType getEntityType(String entityName);

  /**
   * 根据类型,查找实体类型<br>
   * 找到实体名或者实体类名和指定类类名相同的entityType
   * 
   * @param entityClass a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.dao.metadata.EntityType} object.
   */
  public EntityType getEntityType(Class<?> entityClass);

  /**
   * 一个具体类所对应的实体名数组.
   * 
   * @param clazz a {@link java.lang.Class} object.
   * @return an array of {@link java.lang.String} objects.
   */
  public String[] getEntityNames(Class<?> clazz);

  /**
   * 根据对象返回实体名
   * 
   * @param obj a {@link java.lang.Object} object.
   * @return a {@link java.lang.String} object.
   */
  public String getEntityName(Object obj);

}
