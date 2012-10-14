/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm;

/**
 * Entity table and Collection Table Naming Strategy.
 * 
 * @author chaostone
 */
public interface TableNamingStrategy {

  /**
   * Convert class to table name
   * 
   * @param className
   */
  String classToTableName(String className);

  /**
   * Convert collection to table name
   * 
   * @param className
   * @param tableName
   * @param collectionName
   */
  String collectionToTableName(String className, String tableName, String collectionName);

  /**
   * Return schema for package
   * 
   * @param packageName
   */
  String getSchema(String packageName);

  /**
   * Mapped in multischema?
   * 
   */
  boolean isMultiSchema();

}
