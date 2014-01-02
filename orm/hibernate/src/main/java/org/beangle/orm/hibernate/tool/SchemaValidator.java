/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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
package org.beangle.orm.hibernate.tool;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.orm.hibernate.SessionFactoryBean;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.dialect.Dialect;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.id.IdentifierGenerator;
import org.hibernate.id.IdentifierGeneratorAggregator;
import org.hibernate.id.PersistentIdentifierGenerator;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.Column;
import org.hibernate.mapping.IdentifierCollection;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.RootClass;
import org.hibernate.mapping.Table;
import org.hibernate.tool.hbm2ddl.ColumnMetadata;
import org.hibernate.tool.hbm2ddl.DatabaseMetadata;
import org.hibernate.tool.hbm2ddl.TableMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 * @version $Id: SchemaValidator.java Aug 9, 2011 7:45:32 PM chaostone $
 */
public class SchemaValidator {

  private static final Logger logger = LoggerFactory.getLogger(SchemaValidator.class);
  private SessionFactoryBean sessionFactoryBean;

  private StringBuilder reporter = new StringBuilder();

  public String validate() {
    Connection connection = null;
    try {
      connection = sessionFactoryBean.getDataSource().getConnection();
      DatabaseMetadata meta;
      logger.info("fetching database metadata");
      Configuration config = sessionFactoryBean.getConfiguration();
      Dialect dialect = Dialect.getDialect(sessionFactoryBean.getHibernateProperties());
      meta = new DatabaseMetadata(connection, dialect, config, false);
      return validateSchema(config, dialect, meta);
    } catch (SQLException sqle) {
      logger.error("could not get database metadata", sqle);
      throw new RuntimeException(sqle);
    } finally {
      if (null != connection) try {
        connection.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  public String validateSchema(Configuration config, Dialect dialect, DatabaseMetadata databaseMetadata) {
    String defaultCatalog = sessionFactoryBean.getHibernateProperties().getProperty(
        Environment.DEFAULT_CATALOG);
    String defaultSchema = sessionFactoryBean.getHibernateProperties()
        .getProperty(Environment.DEFAULT_SCHEMA);

    Mapping mapping = config.buildMapping();

    Iterator<?> iter = config.getTableMappings();
    while (iter.hasNext()) {
      Table table = (Table) iter.next();
      if (table.isPhysicalTable()) {
        TableMetadata tableInfo = databaseMetadata.getTableMetadata(table.getName(),
            (table.getSchema() == null) ? defaultSchema : table.getSchema(),
            (table.getCatalog() == null) ? defaultCatalog : table.getCatalog(), table.isQuoted());
        if (tableInfo == null) {
          reporter.append("Missing table: " + table.getName() + "\n");
        } else {
          validateColumns(table, dialect, mapping, tableInfo);
        }
      }
    }

    iter = iterateGenerators(config, dialect);
    while (iter.hasNext()) {
      PersistentIdentifierGenerator generator = (PersistentIdentifierGenerator) iter.next();
      Object key = generator.generatorKey();
      if (!databaseMetadata.isSequence(key) && !databaseMetadata.isTable(key)) { throw new HibernateException(
          "Missing sequence or table: " + key); }
    }

    return null;
  }

  private void validateColumns(Table table, Dialect dialect, Mapping mapping, TableMetadata tableInfo) {
    Iterator<?> iter = table.getColumnIterator();
    Set<ColumnMetadata> processed = CollectUtils.newHashSet();
    String tableName = Table.qualify(tableInfo.getCatalog(), tableInfo.getSchema(), tableInfo.getName());
    while (iter.hasNext()) {
      Column col = (Column) iter.next();
      ColumnMetadata columnInfo = tableInfo.getColumnMetadata(col.getName());
      if (columnInfo == null) {
        reporter.append("Missing column: " + col.getName() + " in " + tableName);
      } else {
        final boolean typesMatch = col.getSqlType(dialect, mapping).toLowerCase()
            .startsWith(columnInfo.getTypeName().toLowerCase())
            || columnInfo.getTypeCode() == col.getSqlTypeCode(mapping);
        if (!typesMatch) {
          reporter.append("Wrong column type in " + tableName + " for column " + col.getName() + ". Found: "
              + columnInfo.getTypeName().toLowerCase() + ", expected: " + col.getSqlType(dialect, mapping));
        }
        processed.add(columnInfo);
      }
    }
    // 检查多出的列
    try {
      Field field = tableInfo.getClass().getField("columns");
      @SuppressWarnings("unchecked")
      Set<ColumnMetadata> allColumns = CollectUtils.newHashSet(((Map<String, ColumnMetadata>) field
          .get(tableInfo)).values());
      allColumns.removeAll(processed);
      if (!allColumns.isEmpty()) {
        for (ColumnMetadata col : allColumns) {
          reporter.append("Extra column " + col.getName() + " in " + tableName);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SuppressWarnings({ "unchecked", "rawtypes" })
  // borrow from Configuration code
  private Iterator<IdentifierGenerator> iterateGenerators(Configuration config, Dialect dialect)
      throws MappingException {

    TreeMap generators = new TreeMap();
    String defaultCatalog = sessionFactoryBean.getHibernateProperties().getProperty(
        Environment.DEFAULT_CATALOG);
    String defaultSchema = sessionFactoryBean.getHibernateProperties()
        .getProperty(Environment.DEFAULT_SCHEMA);

    for (Iterator<PersistentClass> pcIter = config.getClassMappings(); pcIter.hasNext();) {
      PersistentClass pc = pcIter.next();
      if (!pc.isInherited()) {
        IdentifierGenerator ig = pc.getIdentifier().createIdentifierGenerator(
            config.getIdentifierGeneratorFactory(), dialect, defaultCatalog, defaultSchema, (RootClass) pc);

        if (ig instanceof PersistentIdentifierGenerator) {
          generators.put(((PersistentIdentifierGenerator) ig).generatorKey(), ig);
        } else if (ig instanceof IdentifierGeneratorAggregator) {
          ((IdentifierGeneratorAggregator) ig).registerPersistentGenerators(generators);
        }
      }
    }

    for (Iterator<Collection> collIter = config.getCollectionMappings(); collIter.hasNext();) {
      Collection collection = collIter.next();
      if (collection.isIdentified()) {
        IdentifierGenerator ig = ((IdentifierCollection) collection).getIdentifier()
            .createIdentifierGenerator(config.getIdentifierGeneratorFactory(), dialect, defaultCatalog,
                defaultSchema, null);

        if (ig instanceof PersistentIdentifierGenerator) {
          generators.put(((PersistentIdentifierGenerator) ig).generatorKey(), ig);
        }
      }
    }

    return generators.values().iterator();
  }

  public void setSessionFactoryBean(SessionFactoryBean sessionFactoryBean) {
    this.sessionFactoryBean = sessionFactoryBean;
  }

}
