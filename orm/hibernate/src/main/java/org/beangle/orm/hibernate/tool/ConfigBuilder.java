package org.beangle.orm.hibernate.tool;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

import org.beangle.commons.entity.orm.AbstractPersistModule;
import org.beangle.commons.entity.orm.EntityPersistConfig;
import org.beangle.commons.io.IOs;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.orm.hibernate.DefaultTableNamingStrategy;
import org.beangle.orm.hibernate.RailsNamingStrategy;
import org.hibernate.cfg.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

public class ConfigBuilder {

  /**
   * build configration
   * 
   * @throws Exception
   */
  @SuppressWarnings("unchecked")
  public static Configuration build(Configuration cfg) throws Exception {
    PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(
        DdlGenerator.class.getClassLoader());

    // config naming strategy
    DefaultTableNamingStrategy tableNamingStrategy = new DefaultTableNamingStrategy();
    for (Resource resource : resolver.getResources("classpath*:META-INF/beangle/table.properties"))
      tableNamingStrategy.addConfig(resource.getURL());
    RailsNamingStrategy namingStrategy = new RailsNamingStrategy();
    namingStrategy.setTableNamingStrategy(tableNamingStrategy);
    cfg.setNamingStrategy(namingStrategy);

    for (Resource resource : resolver.getResources("classpath*:META-INF/hibernate.cfg.xml"))
      cfg.configure(resource.getURL());

    for (Resource resource : resolver.getResources("classpath*:META-INF/beangle/persist*.properties")) {
      InputStream is = resource.getURL().openStream();
      Properties props = new Properties();
      if (null != is) props.load(is);

      Object module = props.remove("module");
      if (null == module) continue;

      Class<? extends AbstractPersistModule> moduleClass = (Class<? extends AbstractPersistModule>) ClassLoaders
          .loadClass(module.toString());
      addPersistInfo(cfg, moduleClass.newInstance().getConfig());
      Enumeration<String> enumer = (Enumeration<String>) props.propertyNames();
      while (enumer.hasMoreElements()) {
        String propertyName = enumer.nextElement();
        cfg.setProperty(propertyName, props.getProperty(propertyName));
      }
      IOs.close(is);
    }
    cfg.buildMappings();
    return cfg;
  }

  private static void addPersistInfo(Configuration cfg, EntityPersistConfig epconfig) {
    for (EntityPersistConfig.EntityDefinition definition : epconfig.getEntites()) {
      cfg.addAnnotatedClass(definition.getClazz());
      if (null != definition.getCacheUsage()) {
        String region = (null == definition.getCacheRegion()) ? definition.getEntityName() : definition
            .getCacheRegion();
        cfg.setCacheConcurrencyStrategy(definition.getEntityName(), definition.getCacheUsage(), region, true);
      }
    }
    for (EntityPersistConfig.CollectionDefinition definition : epconfig.getCollections()) {
      if (null == definition.getCacheUsage()) continue;
      String role = epconfig.getEntity(definition.getClazz()).getEntityName() + "."
          + definition.getProperty();
      String region = (null == definition.getCacheRegion()) ? role : definition.getCacheRegion();
      cfg.setCollectionCacheConcurrencyStrategy(role, definition.getCacheUsage(), region);
    }
  }

}
