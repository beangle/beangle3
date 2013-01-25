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
package org.beangle.context.spring.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.StringUtils;

/**
 * <p>
 * ReconfigBeanDefinitionHolder class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class ReconfigBeanDefinitionHolder extends BeanDefinitionHolder {

  private ReconfigType configType = ReconfigType.UPDATE;

  /**
   * <p>
   * Constructor for ReconfigBeanDefinitionHolder.
   * </p>
   * 
   * @param beanDefinition a {@link org.springframework.beans.factory.config.BeanDefinition} object.
   * @param beanName a {@link java.lang.String} object.
   * @param aliases an array of {@link java.lang.String} objects.
   */
  public ReconfigBeanDefinitionHolder(BeanDefinition beanDefinition, String beanName, String[] aliases) {
    super(beanDefinition, beanName, aliases);
  }

  /**
   * <p>
   * Constructor for ReconfigBeanDefinitionHolder.
   * </p>
   * 
   * @param beanDefinition a {@link org.springframework.beans.factory.config.BeanDefinition} object.
   * @param beanName a {@link java.lang.String} object.
   */
  public ReconfigBeanDefinitionHolder(BeanDefinition beanDefinition, String beanName) {
    super(beanDefinition, beanName);
  }

  /**
   * <p>
   * Constructor for ReconfigBeanDefinitionHolder.
   * </p>
   * 
   * @param beanDefinitionHolder a
   *          {@link org.springframework.beans.factory.config.BeanDefinitionHolder} object.
   */
  public ReconfigBeanDefinitionHolder(BeanDefinitionHolder beanDefinitionHolder) {
    super(beanDefinitionHolder);
  }

  /**
   * <p>
   * Getter for the field <code>configType</code>.
   * </p>
   * 
   * @return a {@link org.beangle.context.spring.config.context.spring.ReconfigType} object.
   */
  public ReconfigType getConfigType() {
    return configType;
  }

  /**
   * <p>
   * Setter for the field <code>configType</code>.
   * </p>
   * 
   * @param configType a {@link org.beangle.context.spring.config.context.spring.ReconfigType} object.
   */
  public void setConfigType(ReconfigType configType) {
    this.configType = configType;
  }

  /** {@inheritDoc} */
  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    if (configType.equals(ReconfigType.UPDATE)) {
      sb.append("UPDATE:");
    } else {
      sb.append("REMOVE:");
    }
    sb.append(this.getBeanName()).append("'");
    if (null != getAliases() && getAliases().length > 0) {
      sb.append(" aliases[").append(StringUtils.arrayToCommaDelimitedString(getAliases())).append("]");
    }
    BeanDefinition bd = getBeanDefinition();
    if (null != bd.getBeanClassName()) {
      sb.append(" [").append(bd.getBeanClassName()).append("]");
    }
    if (null != bd.getScope() && !bd.getScope().equals("")) {
      sb.append("; scope=").append(bd.getScope());
    }
    if (bd.isAbstract()) {
      sb.append("; abstract=true");
    }
    if (bd.isLazyInit()) {
      sb.append("; lazyInit=true");
    }
    if (bd instanceof GenericBeanDefinition) {
      GenericBeanDefinition gbd = (GenericBeanDefinition) bd;
      if (gbd.getAutowireMode() > 0) {
        sb.append("; autowireMode=").append(gbd.getAutowireMode());
      }
      if (null != gbd.getFactoryBeanName()) {
        sb.append("; factoryBeanName=").append(gbd.getFactoryBeanName());
      }
      if (null != gbd.getFactoryMethodName()) {
        sb.append("; factoryMethodName=").append(gbd.getFactoryMethodName());
      }
      if (null != gbd.getInitMethodName()) {
        sb.append("; initMethodName=").append(gbd.getInitMethodName());
      }
      if (null != gbd.getDestroyMethodName()) {
        sb.append("; destroyMethodName=").append(gbd.getDestroyMethodName());
      }
    }
    return sb.toString();
  }

}
