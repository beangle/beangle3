/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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

import java.io.Serializable;

import org.beangle.commons.lang.Strings;
import org.hibernate.AssertionFailure;
import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 类似Rails的数据库表名、列名命名策略
 * 
 * @see DefaultNamingStrategy the default strategy
 * @author chaostone
 */
public class RailsNamingStrategy implements NamingStrategy, Serializable {
  private static final long serialVersionUID = -2656604564223895758L;

  private static final Logger logger = LoggerFactory.getLogger(RailsNamingStrategy.class);
  // 表名、字段名、序列长度
  private static final int MaxLength = 30;

  private TableNamingStrategy tableNamingStrategy;

  /**
   * 根据实体名(entityName)命名表
   * 
   * @param className
   */
  public String classToTableName(String className) {
    String tableName = tableNamingStrategy.classToTableName(className);
    if (tableName.length() > MaxLength) logger.warn("{}'s length greate than 30!", tableName);
    logger.debug("Mapping entity[{}] to {}", className, tableName);
    return tableName;
  }

  /**
   * 对自动起名和使体内集合配置的表名，添加前缀
   * 
   * <pre>
   * 配置好的实体表名和关联表的名字都会经过此方法。
   * </re>
   */
  public String tableName(String tableName) {
    return tableName;
  }

  /** 对配置文件起好的列名,不进行处理 */
  public String columnName(String columnName) {
    if (columnName.length() > MaxLength) logger.warn("{}'s length greate than 30!", columnName);
    return columnName;
  }

  /**
   * 数据列的逻辑名
   * 
   * <pre>
   * 如果有列名，不做处理，否则按照属性自动起名.
   * 该策略保证columnName=logicalColumnName
   * </pre>
   */
  public String logicalColumnName(String columnName, String propertyName) {
    return Strings.isNotEmpty(columnName) ? columnName : propertyToColumnName(propertyName);
  }

  /**
   * 根据属性名自动起名
   * 
   * <pre>
   * 将混合大小写，带有.分割的属性描述，转换成下划线分割的名称。
   * 属性名字包括：简单属性、集合属性、组合属性(component.name)
   * </pre>
   * 
   * @param propertyName
   */
  public String propertyToColumnName(String propertyName) {
    return addUnderscores(unqualify(propertyName));
  }

  /** Return the argument */
  public String joinKeyColumnName(String joinedColumn, String joinedTable) {
    return columnName(joinedColumn);
  }

  /** Return the property name or propertyTableName */
  public String foreignKeyColumnName(String propertyName, String propertyEntityName,
      String propertyTableName, String referencedColumnName) {
    String header = null == propertyName ? propertyTableName : unqualify(propertyName);
    if (header == null) { throw new AssertionFailure("NamingStrategy not properly filled"); }
    if (isManyToOne()) {
      header = addUnderscores(header);
    } else {
      header = addUnderscores(propertyTableName);
    }
    return header + "_" + referencedColumnName;

  }

  /** Collection Table */
  public String collectionTableName(String ownerEntity, String ownerEntityTable, String associatedEntity,
      String associatedEntityTable, String propertyName) {
    String ownerTable = null;
    // Just for annotation configuration,it's ownerEntity is classname(not entityName), and
    // ownerEntityTable is class shortname
    if (Character.isUpperCase(ownerEntityTable.charAt(0))) {
      ownerTable = tableNamingStrategy.classToTableName(ownerEntity);
    } else {
      ownerTable = tableName(ownerEntityTable);
    }
    String tblName = tableNamingStrategy.collectionToTableName(ownerEntity, ownerTable, propertyName);
    if (tblName.length() > MaxLength) logger.warn("{}'s length greate than 30!", tblName);
    return tblName;
  }

  /**
   * Returns either the table name if explicit or if there is an associated
   * table, the concatenation of owner entity table and associated table
   * otherwise the concatenation of owner entity table and the unqualified
   * property name
   */
  public String logicalCollectionTableName(String tableName, String ownerEntityTable,
      String associatedEntityTable, String propertyName) {
    if (tableName == null) {
      // use of a stringbuilder to workaround a JDK bug
      return new StringBuilder(ownerEntityTable).append("_")
          .append(associatedEntityTable == null ? unqualify(propertyName) : associatedEntityTable).toString();
    } else {
      return tableName;
    }
  }

  /**
   * Return the column name if explicit or the concatenation of the property
   * name and the referenced column
   */
  public String logicalCollectionColumnName(String columnName, String propertyName, String referencedColumn) {
    return Strings.isNotEmpty(columnName) ? columnName : unqualify(propertyName) + "_" + referencedColumn;
  }

  public void setTableNamingStrategy(TableNamingStrategy tableNamingStrategy) {
    this.tableNamingStrategy = tableNamingStrategy;
  }

  public TableNamingStrategy getTableNamingStrategy() {
    return tableNamingStrategy;
  }

  protected static String addUnderscores(String name) {
    return Strings.unCamel(name.replace('.', '_'), '_');
  }

  protected static String unqualify(String qualifiedName) {
    int loc = qualifiedName.lastIndexOf('.');
    return (loc < 0) ? qualifiedName : qualifiedName.substring(loc + 1);
  }

  /**
   * 检查是否为ManyToOne调用
   */
  private boolean isManyToOne() {
    StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    if (trace.length >= 9) {
      for (int i = 6; i <= 8; i++) {
        if (trace[i].getClassName().equals("org.hibernate.cfg.ToOneFkSecondPass")) return true;
      }
    }
    return false;
  }
}
