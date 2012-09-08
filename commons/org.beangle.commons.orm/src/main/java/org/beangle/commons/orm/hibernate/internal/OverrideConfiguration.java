/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.orm.hibernate.internal;

import org.beangle.commons.orm.hibernate.TableSeqGenerator;
import org.hibernate.DuplicateMappingException;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Mappings;
import org.hibernate.cfg.SettingsFactory;
import org.hibernate.mapping.Collection;
import org.hibernate.mapping.IdGenerator;
import org.hibernate.mapping.PersistentClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provide overriderable mapping in sessionFactory
 * 
 * @author chaostone
 */
@SuppressWarnings("serial")
public class OverrideConfiguration extends Configuration {

  private static Logger logger = LoggerFactory.getLogger(OverrideConfiguration.class);

  public OverrideConfiguration() {
    super();
    this.metadataSourceQueue = new StmartMetadataSourceQueue();
  }

  public OverrideConfiguration(SettingsFactory settingsFactory) {
    super(settingsFactory);
    this.metadataSourceQueue = new StmartMetadataSourceQueue();
  }

  @Override
  public Mappings createMappings() {
    return new OverrideMappings();
  }

  protected class OverrideMappings extends MappingsImpl {
    
    public OverrideMappings() {
      super();
      //注册缺省的sequence生成器
      IdGenerator idGen=new IdGenerator();
      idGen.setName("table_sequence");
      idGen.setIdentifierGeneratorStrategy(TableSeqGenerator.class.getName());
      this.addDefaultGenerator(idGen);
    }

    @SuppressWarnings("unchecked")
    @Override
    public void addClass(PersistentClass persistentClass) throws DuplicateMappingException {
      String jpaEntityName = persistentClass.getJpaEntityName();
      String entityName = persistentClass.getEntityName();
      String entityClassName = null;
      if (null != jpaEntityName && jpaEntityName.contains(".")) {
        entityClassName = entityName;
        entityName = jpaEntityName;
        persistentClass.setEntityName(entityName);
      }
      PersistentClass old = (PersistentClass) classes.get(entityName);
      if (old == null) {
        classes.put(entityName, persistentClass);
      } else if (old.getMappedClass().isAssignableFrom(persistentClass.getMappedClass())) {
        classes.put(entityName, persistentClass);
        logger.info("{} override {} for entity configuration", persistentClass.getClassName(),
            old.getClassName());
      }
      if (null != entityClassName) {
        classes.put(entityClassName, persistentClass);
      }
    }

    @Override
    public void addCollection(Collection collection) throws DuplicateMappingException {
      collections.put(collection.getRole(), collection);
    }

  }

  protected class StmartMetadataSourceQueue extends MetadataSourceQueue {
    protected void syncAnnotatedClasses() {
    }
  }
}
