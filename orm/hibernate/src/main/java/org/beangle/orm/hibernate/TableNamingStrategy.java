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
  String classToTableName(Class<?> clazz);

  /**
   * Convert collection to table name
   *
   * @param className
   * @param tableName
   * @param collectionName
   */
  String collectionToTableName(Class<?> entityClass, String tableName, String collectionName);

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
