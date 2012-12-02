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
package org.beangle.commons.orm.hibernate;

import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.beangle.commons.bean.Disposable;
import org.beangle.commons.bean.Initializing;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.ClassLoaders;
import org.beangle.commons.lang.reflect.Reflections;
import org.beangle.commons.lang.time.Stopwatch;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.cfg.NamingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * @author chaostone
 * @version $Id: SessionFactoryBean.java Feb 27, 2012 10:52:27 PM chaostone $
 */
public class SessionFactoryBean implements FactoryBean<SessionFactory>, Initializing, Disposable,
    BeanClassLoaderAware {

  private static final ThreadLocal<DataSource> configTimeDataSourceHolder = new ThreadLocal<DataSource>();

  protected Logger logger = LoggerFactory.getLogger(getClass());

  private Class<? extends Configuration> configurationClass = Configuration.class;

  private DataSource dataSource;

  private Resource[] configLocations;

  private String[] mappingResources;

  private List<String> classNames = CollectUtils.newArrayList();

  private NamingStrategy namingStrategy;

  private Properties hibernateProperties;

  private SessionFactory sessionFactory;

  private ClassLoader beanClassLoader = ClassLoaders.getDefaultClassLoader();

  private Configuration configuration;

  /**
   * Set the locations of multiple Hibernate XML config files, for example as
   * classpath resources "classpath:hibernate.cfg.xml,classpath:extension.cfg.xml".
   * <p>
   * Note: Can be omitted when all necessary properties and mapping resources are specified locally
   * via this bean.
   * 
   * @see org.hibernate.cfg.Configuration#configure(java.net.URL)
   */
  public void setConfigLocations(Resource[] configLocations) {
    this.configLocations = configLocations;
    // for (Resource res : configLocations) {
    // try {
    // InputStream is = res.getURL().openStream();
    // List<String> lines = IOUtils.readLines(is);
    // is.close();
    // for (String line : lines) {
    // if (line.contains("<mapping")) {
    // classNames.add(Strings.substringBetween(line, "\"", "\"").trim());
    // }
    // }
    // } catch (IOException e) {
    // e.printStackTrace();
    // }
    // }
  }

  /**
   * Set Hibernate mapping resources to be found in the class path,
   * like "example.hbm.xml" or "mypackage/example.hbm.xml".
   * Analogous to mapping entries in a Hibernate XML config file.
   * Alternative to the more generic setMappingLocations method.
   * <p>
   * Can be used to add to mappings from a Hibernate XML config file, or to specify all mappings
   * locally.
   * 
   * @see org.hibernate.cfg.Configuration#addResource
   */
  public void setMappingResources(String[] mappingResources) {
    this.mappingResources = mappingResources;
  }

  /**
   * Set Hibernate properties, such as "hibernate.dialect".
   * <p>
   * Can be used to override values in a Hibernate XML config file, or to specify all necessary
   * properties locally.
   * <p>
   * Note: Do not specify a transaction provider here when using Spring-driven transactions. It is
   * also advisable to omit connection provider settings and use a Spring-set DataSource instead.
   * 
   * @see #setDataSource
   */
  public void setHibernateProperties(Properties hibernateProperties) {
    this.hibernateProperties = hibernateProperties;
  }

  /**
   * Return the Hibernate properties, if any. Mainly available for
   * configuration through property paths that specify individual keys.
   */
  public Properties getHibernateProperties() {
    if (this.hibernateProperties == null) this.hibernateProperties = new Properties();
    return this.hibernateProperties;
  }

  /**
   * Set a Hibernate NamingStrategy for the SessionFactory, determining the
   * physical column and table names given the info in the mapping document.
   * 
   * @see org.hibernate.cfg.Configuration#setNamingStrategy
   */
  public void setNamingStrategy(NamingStrategy namingStrategy) {
    this.namingStrategy = namingStrategy;
  }

  public void init() throws Exception {
    if (dataSource != null) configTimeDataSourceHolder.set(dataSource);
    configuration = newConfiguration();

    configuration.getProperties().put("hibernate.classLoader.application", beanClassLoader);
    // Set Hibernate 4.0+ CurrentSessionContext implementation,
    // provide the Beangle-managed Session as current Session.
    configuration.setProperty(Environment.CURRENT_SESSION_CONTEXT_CLASS,
        BeangleSessionContext.class.getName());
    if (this.namingStrategy != null) configuration.setNamingStrategy(this.namingStrategy);
    if (dataSource != null) configuration.getProperties().put(Environment.DATASOURCE, dataSource);
    if (this.hibernateProperties != null) configuration.addProperties(this.hibernateProperties);

    try {

      if (this.configLocations != null) {
        for (Resource resource : this.configLocations)
          configuration.configure(resource.getURL());
      }
      if (this.mappingResources != null) {
        for (String mapping : this.mappingResources) {
          Resource resource = new ClassPathResource(mapping.trim(), this.beanClassLoader);
          configuration.addInputStream(resource.getInputStream());
        }
      }

      for (String className : classNames) {
        configuration.addAnnotatedClass(Class.forName(className, true, beanClassLoader));
      }
      Stopwatch watch = new Stopwatch(true);
      this.sessionFactory = configuration.buildSessionFactory();
      logger.info("Building Hibernate SessionFactory in {}", watch);
    }

    finally {
      if (dataSource != null) configTimeDataSourceHolder.remove();
    }
  }

  /**
   * Subclasses can override this method to perform custom initialization
   * of the Configuration instance used for SessionFactory creation.
   * The properties of this LocalSessionFactoryBean will be applied to
   * the Configuration object that gets returned here.
   * <p>
   * The default implementation creates a new Configuration instance. A custom implementation could
   * prepare the instance in a specific way, or use a custom Configuration subclass.
   * 
   * @return the Configuration instance
   * @throws HibernateException in case of Hibernate initialization errors
   * @see org.hibernate.cfg.Configuration#Configuration()
   */
  protected Configuration newConfiguration() throws HibernateException {
    return Reflections.newInstance(this.configurationClass);
  }

  public void destroy() throws HibernateException {
    logger.info("Closing Hibernate SessionFactory");
    this.sessionFactory.close();
  }

  public void setBeanClassLoader(ClassLoader classLoader) {
    this.beanClassLoader = classLoader;
  }

  public SessionFactory getObject() {
    return this.sessionFactory;
  }

  public Class<? extends SessionFactory> getObjectType() {
    return (this.sessionFactory != null ? this.sessionFactory.getClass() : SessionFactory.class);
  }

  public boolean isSingleton() {
    return true;
  }

  public static DataSource getConfigTimeDataSource() {
    return configTimeDataSourceHolder.get();
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public SessionFactory getSessionFactory() {
    return sessionFactory;
  }

  public void setConfigurationClass(Class<? extends Configuration> configurationClass) {
    this.configurationClass = configurationClass;
  }

  public Configuration getConfiguration() {
    return configuration;
  }

}
