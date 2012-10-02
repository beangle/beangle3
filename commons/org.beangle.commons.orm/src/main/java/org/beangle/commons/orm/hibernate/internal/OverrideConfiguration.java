/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm.hibernate.internal;

import java.util.Map;
import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.orm.TableNamingStrategy;
import org.beangle.commons.orm.hibernate.RailsNamingStrategy;
import org.beangle.commons.orm.hibernate.TableSeqGenerator;
import org.hibernate.DuplicateMappingException;
import org.hibernate.MappingException;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Mappings;
import org.hibernate.cfg.SettingsFactory;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.IdGenerator;
import org.hibernate.mapping.PersistentClass;
import org.hibernate.mapping.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide schema reconfig and overriderable class mapping in sessionFactory
 * 
 * @author chaostone
 * @since 2.1
 */
@SuppressWarnings("serial")
public class OverrideConfiguration extends Configuration {

  private static Logger logger = LoggerFactory.getLogger(OverrideConfiguration.class);

  public OverrideConfiguration() {
    super();
  }

  public OverrideConfiguration(SettingsFactory settingsFactory) {
    super(settingsFactory);
  }

  @Override
  public Mappings createMappings() {
    return new OverrideMappings();
  }

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
        String schema = namingStrategy.getSchema(clazz.getEntityName());
        if (null != schema) clazz.getTable().setSchema(schema);
      }

      for (Collection collection : collections.values()) {
        final Table table = collection.getCollectionTable();
        if (null == table) continue;
        String schema = namingStrategy.getSchema(collection.getRole());
        if (null != schema) table.setSchema(schema);
      }
    }
  }

  /**
   * Update persistentclass and collection's schema.
   * 
   * @see addClass
   */
  @Override
  protected void secondPassCompile() throws MappingException {
    super.secondPassCompile();
    configSchema();
    // remove duplicated persistentClass register in classes map.
    // see addClass
    Set<String> hackedEntityNames = CollectUtils.newHashSet();
    for (Map.Entry<String, PersistentClass> entry : classes.entrySet()) {
      if (!entry.getKey().equals(entry.getValue().getEntityName())) hackedEntityNames.add(entry.getKey());
    }
    for (String entityName : hackedEntityNames) {
      classes.remove(entityName);
    }
  }

  protected class OverrideMappings extends MappingsImpl {
    // 注册缺省的sequence生成器
    public OverrideMappings() {
      super();
      IdGenerator idGen = new IdGenerator();
      idGen.setName("table_sequence");
      idGen.setIdentifierGeneratorStrategy(TableSeqGenerator.class.getName());
      this.addDefaultGenerator(idGen);
    }

    /**
     * 1.First change jpaName to entityName
     * 2.duplicate register persistent class
     */
    @SuppressWarnings("unchecked")
    @Override
    public void addClass(PersistentClass pClass) throws DuplicateMappingException {
      String jpaEntityName = pClass.getJpaEntityName();
      String entityName = pClass.getEntityName();
      String entityClassName = null;
      if (null != jpaEntityName && jpaEntityName.contains(".")) {
        entityClassName = entityName;
        // Set real entityname is jpa entityname
        entityName = jpaEntityName;
        pClass.setEntityName(entityName);
      }

      PersistentClass old = (PersistentClass) classes.get(entityName);
      if (old == null) {
        classes.put(entityName, pClass);
      } else if (old.getMappedClass().isAssignableFrom(pClass.getMappedClass())) {
        classes.put(entityName, pClass);
        logger.info("{} override {} for entity configuration", pClass.getClassName(), old.getClassName());
      }
      // 为了欺骗hibernate中的ToOneFkSecondPass的部分代码,例如isInPrimaryKey。这些代码会根据className查找persistentClass，而不是根据entityName
      if (null != entityClassName) classes.put(entityClassName, pClass);
    }

    /**
     * Provide override collections with same rolename.
     */
    @Override
    public void addCollection(Collection collection) throws DuplicateMappingException {
      collections.put(collection.getRole(), collection);
    }
  }
}
