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
package org.beangle.commons.transfer.importer;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.entity.metadata.ObjectAndType;
import org.beangle.commons.entity.metadata.Populator;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * MultiEntityImporter class.
 * </p>
 *
 * @author chaostone
 * @version $Id: $
 */
public class MultiEntityImporter extends AbstractItemImporter implements EntityImporter {

  private final Logger logger = LoggerFactory.getLogger(MultiEntityImporter.class);

  protected Map<String, Object> current = CollectUtils.newHashMap();

  protected Set<String> foreignerKeys = CollectUtils.newHashSet();

  protected Populator populator = Model.getPopulator();

  // [alias,entityType]
  protected Map<String, EntityType> entityTypes = new HashMap<String, EntityType>();

  /**
   * <p>
   * Constructor for MultiEntityImporter.
   * </p>
   */
  public MultiEntityImporter() {
    super();
    foreignerKeys.add("code");
  }

  /**
   * <p>
   * transferItem.
   * </p>
   */
  public void transferItem() {
    if (logger.isDebugEnabled()) logger.debug("tranfer index:{} : {}", getTranferIndex(), values);
    // 在给定的值的范围内
    for (Map.Entry<String, Object> entry : values.entrySet()) {
      Object value = entry.getValue();
      // 处理空字符串并对所有的字符串进行trim
      if (value instanceof String) {
        String strValue = (String) value;
        if (Strings.isBlank(strValue)) value = null;
        else value = Strings.trim(strValue);
      }
      // 处理null值
      if (null == value) {
        continue;
      } else {
        if (value.equals(Model.NULL)) value = null;
      }
      String key = entry.getKey();
      Object entity = getCurrent(key);
      String attr = processAttr(key);
      String entityName = getEntityName(key);
      EntityType type = Model.getType(entityName);
      populateValue(entity, type, attr, value);
    }
  }

  /**
   * Populate single attribute
   *
   * @param entity
   * @param type
   * @param attr
   * @param value
   */
  protected void populateValue(Object entity, EntityType type, String attr, Object value) {
    // 当有深层次属性
    if (Strings.contains(attr, '.')) {
      if (null != foreignerKeys) {
        boolean isForeigner = isForeigner(attr);
        // 如果是个外键,先根据parentPath生成新的外键实体。
        // 因此导入的是外键,只能有一个属性导入.
        if (isForeigner) {
          String parentPath = Strings.substringBeforeLast(attr, ".");
          ObjectAndType propertyType = populator.initProperty(entity, type, parentPath);
          Object property = propertyType.getObj();
          if (property instanceof Entity<?>) {
            if (((Entity<?>) property).isPersisted()) {
              populator.populateValue(entity, type, parentPath, null);
              populator.initProperty(entity, type, parentPath);
            }
          }
        }
      }
    }
    if (!populator.populateValue(entity, type, attr, value)) {
      transferResult.addFailure(descriptions.get(attr) + " data format error.", value);
    }
  }

  public String processAttr(String attr) {
    return Strings.substringAfter(attr, ".");
  }

  /**
   * <p>
   * getEntityClass.
   * </p>
   *
   * @param attr a {@link java.lang.String} object.
   * @return a {@link java.lang.Class} object.
   */
  protected Class<?> getEntityClass(String attr) {
    return getEntityType(attr).getEntityClass();
  }

  /**
   * <p>
   * getEntityType.
   * </p>
   *
   * @param attr a {@link java.lang.String} object.
   * @return a {@link org.beangle.commons.entity.metadata.EntityType} object.
   */
  protected EntityType getEntityType(String attr) {
    String alias = Strings.substringBefore(attr, ".");
    EntityType entityType = (EntityType) entityTypes.get(alias);
    if (null == entityType) entityType = (EntityType) entityTypes.get(attr);
    return entityType;
  }

  /**
   * <p>
   * addEntity.
   * </p>
   *
   * @param alias a {@link java.lang.String} object.
   * @param entityClass a {@link java.lang.Class} object.
   */
  public void addEntity(String alias, Class<?> entityClass) {
    EntityType entityType = Model.getType(entityClass);
    if (null == entityType) { throw new RuntimeException("cannot find entity type for " + entityClass); }
    entityTypes.put(alias, entityType);
  }

  /**
   * <p>
   * addEntity.
   * </p>
   *
   * @param alias a {@link java.lang.String} object.
   * @param entityName a {@link java.lang.String} object.
   */
  public void addEntity(String alias, String entityName) {
    EntityType entityType = Model.getType(entityName);
    if (null == entityType) { throw new RuntimeException("cannot find entity type for " + entityName); }
    entityTypes.put(alias, entityType);
  }

  /**
   * <p>
   * getEntityName.
   * </p>
   *
   * @param attr a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  protected String getEntityName(String attr) {
    return getEntityType(attr).getEntityName();
  }

  /**
   * <p>
   * Getter for the field <code>current</code>.
   * </p>
   *
   * @param attr a {@link java.lang.String} object.
   * @return a {@link java.lang.Object} object.
   */
  public Object getCurrent(String attr) {
    String alias = Strings.substringBefore(attr, ".");
    Object entity = current.get(alias);
    if (null == entity) {
      EntityType entityType = (EntityType) entityTypes.get(alias);
      if (null == entityType) {
        logger.error("Not register entity type for {}", alias);
        throw new IllegalImportFormatException("Not register entity type for " + alias);
      } else {
        entity = entityType.newInstance();
        current.put(alias, entity);
        return entity;
      }
    }
    return entity;
  }

  /**
   * <p>
   * Getter for the field <code>current</code>.
   * </p>
   *
   * @return a {@link java.lang.Object} object.
   */
  public Object getCurrent() {
    return current;
  }

  @SuppressWarnings("unchecked")
  public void setCurrent(Object object) {
    this.current = (Map<String, Object>) object;
  }

  /**
   * <p>
   * getDataName.
   * </p>
   *
   * @return a {@link java.lang.String} object.
   */
  public String getDataName() {
    return "multi entity";
  }

  /**
   * <p>
   * beforeImportItem.
   * </p>
   */
  public void beforeImportItem() {
    this.current = CollectUtils.newHashMap();
  }

  private boolean isForeigner(String attr) {
    String property = Strings.substringAfterLast(attr, ".");
    return foreignerKeys.contains(property);
  }

  /**
   * <p>
   * Getter for the field <code>foreignerKeys</code>.
   * </p>
   *
   * @return a {@link java.util.Set} object.
   */
  public Set<String> getForeignerKeys() {
    return foreignerKeys;
  }

  /**
   * <p>
   * Setter for the field <code>foreignerKeys</code>.
   * </p>
   *
   * @param foreignerKeys a {@link java.util.Set} object.
   */
  public void setForeignerKeys(Set<String> foreignerKeys) {
    this.foreignerKeys = foreignerKeys;
  }

  public void addForeignedKeys(String foreignerKey) {
    foreignerKeys.add(foreignerKey);
  }

  public void setPopulator(Populator populator) {
    this.populator = populator;
  }

  /**
   * <p>
   * Getter for the field <code>entityTypes</code>.
   * </p>
   *
   * @return a {@link java.util.Map} object.
   */
  public Map<String, EntityType> getEntityTypes() {
    return entityTypes;
  }

  /**
   * <p>
   * Setter for the field <code>entityTypes</code>.
   * </p>
   *
   * @param entityTypes a {@link java.util.Map} object.
   */
  public void setEntityTypes(Map<String, EntityType> entityTypes) {
    this.entityTypes = entityTypes;
  }

}
