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
package org.beangle.orm.hibernate;

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
   */
  boolean isMultiSchema();

}
