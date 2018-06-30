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
package org.beangle.orm.hibernate.internal;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.Strings;
import org.beangle.orm.hibernate.RailsNamingStrategy;
import org.beangle.orm.hibernate.TableNamingStrategy;
import org.beangle.orm.hibernate.id.AutoIncrementGenerator;
import org.beangle.orm.hibernate.id.TableSeqGenerator;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.SAXReader;
import org.hibernate.DuplicateMappingException;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Mappings;
import org.hibernate.cfg.SettingsFactory;
import org.hibernate.internal.util.xml.ErrorLogger;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.IdGenerator;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;

/**
 * Provide schema reconfig and overriderable class mapping in sessionFactory
 *
 * @author chaostone
 * @since 2.1
 */
@SuppressWarnings("serial")
public class OverrideConfiguration extends Configuration {

  private static Logger logger = LoggerFactory.getLogger(OverrideConfiguration.class);

  private int dynaupdateMinColumn = 7;

  public OverrideConfiguration() {
    super();
  }

  public OverrideConfiguration(SettingsFactory settingsFactory) {
    super(settingsFactory);
  }

  /**
   * Just disable xml file validation.<br>
   * Speed up boostrap
   */
  @Override
  protected Configuration doConfigure(InputStream stream, String resourceName) throws HibernateException {
    try {
      ErrorLogger errorLogger = new ErrorLogger(resourceName);
      SAXReader reader = xmlHelper.createSAXReader(errorLogger, getEntityResolver());
      reader.setValidation(false);
      Document document = reader.read(new InputSource(stream));
      if (errorLogger.hasErrors()) { throw new MappingException("invalid configuration",
          errorLogger.getErrors().get(0)); }
      doConfigure(document);
    } catch (DocumentException e) {
      throw new HibernateException("Could not parse configuration: " + resourceName, e);
    } finally {
      try {
        stream.close();
      } catch (IOException ioe) {
        logger.error("Could not close input stream for {}", resourceName);
      }
    }
    return this;
  }

  @Override
  public Mappings createMappings() {
    return new OverrideMappings();
  }

  /**
   * Config table's schema by TableNamingStrategy.<br>
   *
   * @see org.beangle.orm.hibernate.RailsNamingStrategy
   */
  private void configSchema() {
    TableNamingStrategy namingStrategy = null;
    if (getNamingStrategy() instanceof RailsNamingStrategy) {
      namingStrategy = ((RailsNamingStrategy) getNamingStrategy()).getTableNamingStrategy();
    }

    if (null == namingStrategy) return;
    else {
      // Update SeqGenerator's static variable.
      // Because generator is init by hibernate,cannot inject namingStrategy.
      TableSeqGenerator.namingStrategy = namingStrategy;
    }

    if (namingStrategy.isMultiSchema()) {
      for (PersistentClass clazz : classes.values()) {
        if (Strings.isEmpty(clazz.getTable().getSchema())) {
          String schema = namingStrategy.getSchema(clazz.getEntityName());
          if (null != schema) clazz.getTable().setSchema(schema);
        }
      }

      for (Collection collection : collections.values()) {
        final Table table = collection.getCollectionTable();
        if (null == table) continue;
        if (Strings.isBlank(table.getSchema())) {
          String schema = namingStrategy.getSchema(collection.getOwnerEntityName());
          if (null != schema) table.setSchema(schema);
        }
      }
    }
  }

  /**
   * Update persistentclass and collection's schema.<br>
   * Remove duplicated persistentClass register in classes map.
   *
   * @see #addClass(Class)
   */
  @Override
  protected void secondPassCompile() throws MappingException {
    super.secondPassCompile();
    configSchema();
    Set<String> hackedEntityNames = CollectUtils.newHashSet();
    for (Map.Entry<String, PersistentClass> entry : classes.entrySet()) {
      if (!entry.getKey().equals(entry.getValue().getEntityName())) hackedEntityNames.add(entry.getKey());
    }
    for (String entityName : hackedEntityNames)
      classes.remove(entityName);
  }

  protected class OverrideMappings extends MappingsImpl {
    private final Map<String, List<Collection>> tmpColls = CollectUtils.newHashMap();

    /**
     * 注册缺省的sequence生成器
     */
    public OverrideMappings() {
      super();
      IdGenerator tableSeqGen = new IdGenerator();
      tableSeqGen.setName("table_sequence");
      tableSeqGen.setIdentifierGeneratorStrategy(TableSeqGenerator.class.getName());
      this.addDefaultGenerator(tableSeqGen);

      IdGenerator idGen = new IdGenerator();
      idGen.setName("auto_increment");
      idGen.setIdentifierGeneratorStrategy(AutoIncrementGenerator.class.getName());
      this.addDefaultGenerator(idGen);
    }

    /**
     * <ul>
     * <li>First change jpaName to entityName</li>
     * <li>Duplicate register persistent class,hack hibernate(ToOneFkSecondPass.isInPrimaryKey)</li>
     * </ul>
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addClass(PersistentClass pClass) throws DuplicateMappingException {
      // trigger dynamic update
      if (!pClass.useDynamicUpdate() && pClass.getTable().getColumnSpan() >= dynaupdateMinColumn)
        pClass.setDynamicUpdate(true);
      final String className = pClass.getClassName();
      String entityName = pClass.getEntityName();

      boolean entityNameChanged = false;
      final String jpaEntityName = pClass.getJpaEntityName();
      // Set real entityname using jpaEntityname
      if (null != jpaEntityName && jpaEntityName.contains(".")) {
        entityName = jpaEntityName;
        pClass.setEntityName(entityName);
        entityNameChanged = true;
      }
      // register class
      PersistentClass old = (PersistentClass) classes.get(entityName);
      if (old == null) {
        classes.put(entityName, pClass);
      } else if (old.getMappedClass().isAssignableFrom(pClass.getMappedClass())) {
        PersistentClassMerger.merge(pClass, old);
      }
      // 为了欺骗hibernate中的ToOneFkSecondPass的部分代码,例如isInPrimaryKey。这些代码会根据className查找persistentClass，而不是根据entityName
      if (entityNameChanged) classes.put(className, pClass);

      // add entitis collections
      List<Collection> cols = tmpColls.remove(entityName);
      if (null == cols) cols = tmpColls.remove(className);
      if (null != cols) {
        for (Collection col : cols) {
          String colName = null;
          if (col.getRole().startsWith(className)) colName = col.getRole().substring(className.length() + 1);
          else colName = col.getRole().substring(entityName.length() + 1);
          col.setRole(entityName + "." + colName);
          collections.put(col.getRole(), col);
        }
      }
    }

    /**
     * Duplicated entity name in sup/subclass situation will rise a
     * <code>DuplicateMappingException</code>
     */
    @SuppressWarnings("deprecation")
    @Override
    public void addImport(String entityName, String rename) throws DuplicateMappingException {
      String existing = imports.get(rename);
      if (null == existing) {
        imports.put(rename, entityName);
      } else {
        if (ClassLoaders.loadClass(existing).isAssignableFrom(ClassLoaders.loadClass(entityName)))
          imports.put(rename, entityName);
        else throw new DuplicateMappingException("duplicate import: " + rename + " refers to both "
            + entityName + " and " + existing + " (try using auto-import=\"false\")", "import", rename);
      }
    }

    /**
     * Delay register collection,let class descide which owner will be winner.
     * <ul>
     * <li>Provide override collections with same rolename.
     * <li>Delay register collection,register by addClass method
     * </ul>
     */
    @Override
    public void addCollection(Collection collection) throws DuplicateMappingException {
      String entityName = collection.getOwnerEntityName();
      List<Collection> cols = tmpColls.get(entityName);
      if (null == cols) {
        cols = CollectUtils.newArrayList();
        tmpColls.put(entityName, cols);
      }
      cols.add(collection);
    }
  }

  public int getDynaupdateMinColumn() {
    return dynaupdateMinColumn;
  }

  public void setDynaupdateMinColumn(int dynaupdateMinColumn) {
    this.dynaupdateMinColumn = dynaupdateMinColumn;
  }

}
