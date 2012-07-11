/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.inject;

import org.beangle.commons.context.inject.BeanConfig.Definition;
import org.beangle.commons.context.inject.BeanConfig.DefinitionBinder;

/**
 * <p>
 * Abstract AbstractBindModule class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class AbstractBindModule implements BindModule {

  protected BeanConfig config;

  /**
   * <p>
   * Getter for the field <code>config</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.context.inject.BeanConfig} object.
   */
  public final BeanConfig getConfig() {
    if (null == config) {
      config = new BeanConfig();
      doBinding();
    }
    return config;
  }

  /**
   * <p>
   * bind.
   * </p>
   * 
   * @param classes a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.context.inject.BeanConfig.DefinitionBinder} object.
   */
  protected DefinitionBinder bind(Class<?>... classes) {
    return config.bind(classes);
  }

  protected Definition bean(Class<?> clazz){
    Definition  def= new Definition(clazz.getName(),clazz,Scope.SINGLETON.toString());
    def.beanName=clazz.getName()+"#"+Math.abs(System.identityHashCode(def));
    return def;
  }
  /**
   * <p>
   * bind.
   * </p>
   * 
   * @param beanName a {@link java.lang.String} object.
   * @param clazz a {@link java.lang.Class} object.
   * @return a {@link org.beangle.commons.context.inject.BeanConfig.DefinitionBinder} object.
   */
  protected DefinitionBinder bind(String beanName, Class<?> clazz) {
    return config.bind(beanName, clazz);
  }

  /**
   * <p>
   * doBinding.
   * </p>
   */
  abstract protected void doBinding();
  /**
   * <p>
   * getObjectType.
   * </p>
   * 
   * @return a {@link java.lang.Class} object.
   */
  public Class<?> getObjectType() {
    return BeanConfig.class;
  }

  /**
   * <p>
   * isSingleton.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isSingleton() {
    return true;
  }
}
