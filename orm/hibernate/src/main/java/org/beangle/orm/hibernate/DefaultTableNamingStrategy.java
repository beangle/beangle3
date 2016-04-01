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
package org.beangle.orm.hibernate;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.inject.Resources;
import org.beangle.commons.lang.Strings;
import org.beangle.commons.text.inflector.Pluralizer;
import org.beangle.commons.text.inflector.en.EnNounPluralizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 根据报名动态设置schema,prefix名字
 * 
 * @author chaostone
 */
public class DefaultTableNamingStrategy implements TableNamingStrategy {

  private static final Logger logger = LoggerFactory.getLogger(DefaultTableNamingStrategy.class);

  /** 实体表表名长度限制 */
  private int entityTableMaxLength = 25;

  /** 关联表表名长度限制 */
  private int relationTableMaxLength = 30;

  private Pluralizer pluralizer = new EnNounPluralizer();

  private final List<TableNamePattern> patterns = new ArrayList<TableNamePattern>();

  private final Map<String, TableNamePattern> packagePatterns = CollectUtils.newHashMap();

  public void addConfig(URL url) {
    loadProperties(url);
    Collections.sort(patterns);
  }

  private void loadProperties(URL url) {
    try {
      logger.debug("loading {}", url);
      InputStream is = url.openStream();
      Properties props = new Properties();
      if (null != is) {
        props.load(is);
      }
      for (Iterator<Object> iter = props.keySet().iterator(); iter.hasNext();) {
        String packageName = (String) iter.next();
        String schemaPrefix = props.getProperty(packageName).trim();

        String schema = null;
        String prefix = null;
        String abbreviationStr = null;
        int commaIndex = schemaPrefix.indexOf(',');
        if (commaIndex < 0 || (commaIndex + 1 == schemaPrefix.length())) {
          schema = schemaPrefix;
        } else if (commaIndex == 0) {
          prefix = schemaPrefix.substring(1);
        } else {
          schema = Strings.substringBefore(schemaPrefix, ",");
          prefix = Strings.substringAfter(schemaPrefix, ",");
        }
        if (Strings.contains(prefix, ",")) {
          abbreviationStr = Strings.substringAfter(prefix, ",").toLowerCase();
          prefix = Strings.substringBefore(prefix, ",");
        }
        TableNamePattern pattern = (TableNamePattern) packagePatterns.get(packageName);
        if (null == pattern) {
          pattern = new TableNamePattern(packageName, schema, prefix);
          packagePatterns.put(packageName, pattern);
          patterns.add(pattern);
        } else {
          pattern.setSchema(schema);
          pattern.setPrefix(prefix);
        }
        if (null != abbreviationStr) {
          String[] pairs = Strings.split(abbreviationStr, ";");
          for (String pair : pairs) {
            String longName = Strings.substringBefore(pair, "=");
            String shortName = Strings.substringAfter(pair, "=");
            pattern.abbreviations.put(longName, shortName);
          }
        }
      }
      is.close();
    } catch (IOException e) {
      logger.error("property load error", e);
    }
  }

  public String getSchema(String packageName) {
    String schemaName = null;
    for (TableNamePattern pattern : patterns) {
      if (packageName.indexOf(pattern.getPackageName()) == 0) schemaName = pattern.getSchema();
    }
    return schemaName;
  }

  public TableNamePattern getPattern(String packageName) {
    TableNamePattern last = null;
    for (TableNamePattern pattern : patterns) {
      if (packageName.indexOf(pattern.getPackageName()) == 0) last = pattern;
    }
    return last;
  }

  public String getPrefix(String packageName) {
    String prefix = null;
    for (TableNamePattern pattern : patterns) {
      if (packageName.indexOf(pattern.getPackageName()) == 0) prefix = pattern.getPrefix();
    }
    return prefix;
  }

  /**
   * is Multiple schema for entity
   */
  public boolean isMultiSchema() {
    Set<String> schemas = CollectUtils.newHashSet();
    for (TableNamePattern pattern : patterns) {
      schemas.add((null == pattern.getSchema()) ? "" : pattern.getSchema());
    }
    return schemas.size() > 1;
  }

  public List<TableNamePattern> getPatterns() {
    return patterns;
  }

  public void setResources(Resources resources) {
    if (null != resources) {
      for (final URL url : resources.getAllPaths())
        addConfig(url);
      logger.info("Table name pattern: -> \n{}", this);
    }
  }

  public String toString() {
    int maxlength = 0;
    for (TableNamePattern pattern : patterns) {
      if (pattern.getPackageName().length() > maxlength) {
        maxlength = pattern.getPackageName().length();
      }
    }
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < patterns.size(); i++) {
      TableNamePattern pattern = patterns.get(i);
      sb.append(Strings.rightPad(pattern.getPackageName(), maxlength, ' ')).append(" : [")
          .append(pattern.getSchema());
      sb.append(" , ").append(pattern.getPrefix());
      if (!pattern.abbreviations.isEmpty()) {
        sb.append(" , ").append(pattern.abbreviations);
      }
      sb.append(']');
      if (i < patterns.size() - 1) sb.append('\n');
    }
    return sb.toString();
  }

  public String classToTableName(String className) {
    if (className.endsWith("Bean")) className = Strings.substringBeforeLast(className, "Bean");

    String tableName = addUnderscores(unqualify(className));
    if (null != pluralizer) tableName = pluralizer.pluralize(tableName);

    TableNamePattern pattern = getPattern(className);
    if (null != pattern) tableName = pattern.prefix + tableName;

    if (tableName.length() > entityTableMaxLength && null != pattern) {
      for (Map.Entry<String, String> pairEntry : pattern.abbreviations.entrySet()) {
        tableName = Strings.replace(tableName, pairEntry.getKey(), pairEntry.getValue());
      }
    }
    return tableName;
  }

  public String collectionToTableName(String className, String tableName, String collectionName) {
    TableNamePattern pattern = getPattern(className);
    String collectionTableName = tableName + "_" + addUnderscores(unqualify(collectionName));
    if ((collectionTableName.length() > relationTableMaxLength) && null != pattern) {
      for (Map.Entry<String, String> pairEntry : pattern.abbreviations.entrySet()) {
        collectionTableName = Strings.replace(collectionTableName, pairEntry.getKey(), pairEntry.getValue());
      }
    }
    return collectionTableName;
  }

  protected static String unqualify(String qualifiedName) {
    int loc = qualifiedName.lastIndexOf('.');
    return (loc < 0) ? qualifiedName : qualifiedName.substring(loc + 1);
  }

  protected static String addUnderscores(String name) {
    return Strings.unCamel(name.replace('.', '_'), '_');
  }

  public void setPluralizer(Pluralizer pluralizer) {
    this.pluralizer = pluralizer;
  }

  public void setEntityTableMaxLength(int entityTableMaxLength) {
    this.entityTableMaxLength = entityTableMaxLength;
  }

  public void setRelationTableMaxLength(int relationTableMaxLength) {
    this.relationTableMaxLength = relationTableMaxLength;
  }
}

/**
 * 表命名模式
 * 
 * @author chaostone
 */
class TableNamePattern implements Comparable<TableNamePattern> {
  // 包名
  String packageName;
  // 模式名
  String schema;
  // 前缀名
  String prefix;

  Map<String, String> abbreviations = CollectUtils.newHashMap();

  public TableNamePattern(String packageName, String schemaName, String prefix) {
    this.packageName = packageName;
    this.schema = schemaName;
    if (null == prefix) this.prefix = "";
    else this.prefix = prefix;
  }

  public int compareTo(TableNamePattern other) {
    return this.packageName.compareTo(other.packageName);
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getSchema() {
    return schema;
  }

  public void setSchema(String schemaName) {
    this.schema = schemaName;
  }

  public String getPrefix() {
    return prefix;
  }

  public void setPrefix(String prefix) {
    this.prefix = prefix;
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[package:").append(packageName).append(", schema:").append(schema);
    sb.append(", prefix:").append(prefix).append(']');
    sb.append(", abbreviations:").append(abbreviations).append(']');
    return sb.toString();
  }
}
