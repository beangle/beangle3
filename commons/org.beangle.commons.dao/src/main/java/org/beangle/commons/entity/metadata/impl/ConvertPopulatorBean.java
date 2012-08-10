/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.entity.metadata.impl;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.beangle.commons.bean.converters.Converter;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.entity.metadata.ObjectAndType;
import org.beangle.commons.entity.metadata.Populator;
import org.beangle.commons.entity.metadata.Type;
import org.beangle.commons.entity.util.ValidEntityKeyPredicate;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * ConvertPopulatorBean class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class ConvertPopulatorBean implements Populator {
  /** Constant <code>logger</code> */
  protected static final Logger logger = LoggerFactory.getLogger(ConvertPopulatorBean.class);

  /** Constant <code>TRIM_STR=true</code> */
  public static final boolean TRIM_STR = true;

  private BeanUtilsBean beanUtils;

  /**
   * <p>
   * Constructor for ConvertPopulatorBean.
   * </p>
   */
  public ConvertPopulatorBean() {
    beanUtils = new BeanUtilsBean(Converter.getDefault());
  }

  /**
   * <p>
   * Constructor for ConvertPopulatorBean.
   * </p>
   * 
   * @param convertUtils a {@link org.apache.commons.beanutils.ConvertUtilsBean} object.
   */
  public ConvertPopulatorBean(ConvertUtilsBean convertUtils) {
    beanUtils = new BeanUtilsBean(convertUtils);
  }

  /**
   * {@inheritDoc} 初始化对象指定路径的属性。<br>
   * 例如给定属性a.b.c,方法会依次检查a a.b a.b.c是否已经初始化
   */
  public ObjectAndType initProperty(final Object target, String entityName, final String attr) {
    Object propObj = target;
    Object property = null;

    int index = 0;
    String[] attrs = Strings.split(attr, ".");
    Type type = Model.getType(entityName);
    while (index < attrs.length) {
      try {
        property = PropertyUtils.getProperty(propObj, attrs[index]);
        Type propertyType = type.getPropertyType(attrs[index]);
        // 初始化
        if (null == propertyType) {
          logger.error("Cannot find property type [{}] of {}", attrs[index], propObj.getClass());
          throw new RuntimeException("Cannot find property type " + attrs[index] + " of "
              + propObj.getClass().getName());
        }
        if (null == property) {
          property = propertyType.newInstance();
          PropertyUtils.setProperty(propObj, attrs[index], property);
        }
        index++;
        propObj = property;
        type = propertyType;
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return new ObjectAndType(property, type);
  }

  /**
   * {@inheritDoc} 安静的拷贝属性，如果属性非法或其他错误则记录日志
   */
  public void populateValue(final Object target, String entityName, final String attr, final Object value) {
    try {
      if (attr.indexOf('.') > -1) {
        initProperty(target, entityName, Strings.substringBeforeLast(attr, "."));
      }
      beanUtils.copyProperty(target, attr, value);
    } catch (Exception e) {
      logger.error("copy property failure:[class:" + entityName + " attr:" + attr + " value:" + value + "]:",
          e);
    }
  }

  /** {@inheritDoc} */
  public void populateValue(Object target, String attr, Object value) {
    populateValue(target, Model.getEntityType(target.getClass()).getEntityName(), attr, value);
  }

  /** {@inheritDoc} */
  public Object populate(Object target, Map<String, Object> params) {
    return populate(target, Model.getEntityName(target), params);
  }

  /** {@inheritDoc} */
  public Object populate(String entityName, Map<String, Object> params) {
    Type type = Model.getType(entityName);
    if (null == type) {
      throw new RuntimeException(entityName + " was not configured!");
    } else {
      return populate(type.newInstance(), type.getName(), params);
    }
  }

  /** {@inheritDoc} */
  public Object populate(Class<?> entityClass, Map<String, Object> params) {
    EntityType entityType = Model.getEntityType(entityClass);
    return populate(entityType.newInstance(), entityType.getEntityName(), params);
  }

  /**
   * {@inheritDoc} 将params中的属性([attr(string)->value(object)]，放入到实体类中。<br>
   * 如果引用到了别的实体，那么<br>
   * 如果params中的id为null，则将该实体的置为null.<br>
   * 否则新生成一个实体，将其id设为params中指定的值。 空字符串按照null处理
   */
  public Object populate(Object entity, String entityName, Map<String, Object> params) {
    Type type = Model.getType(entityName);
    for (final Map.Entry<String, Object> paramEntry : params.entrySet()) {
      String attr = paramEntry.getKey();
      Object value = paramEntry.getValue();
      if (value instanceof String) {
        if (Strings.isEmpty((String) value)) {
          value = null;
        } else if (TRIM_STR) {
          value = ((String) value).trim();
        }
      }
      // 主键
      if (null != type && type.isEntityType() && attr.equals(((EntityType) type).getIdPropertyName())) {
        if (ValidEntityKeyPredicate.INSTANCE.evaluate(value)) {
          setValue(attr, value, entity);
        } else {
          try {
            PropertyUtils.setProperty(entity, attr, null);
          } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
          }
        }
        continue;
      }
      // 普通属性
      if (-1 == attr.indexOf('.')) {
        setValue(attr, value, entity);
      } else {
        String parentAttr = Strings.substring(attr, 0, attr.lastIndexOf('.'));
        try {
          ObjectAndType ot = initProperty(entity, entityName, parentAttr);
          if (null == ot) {
            logger.error("error attr:[" + attr + "] value:[" + value + "]");
            continue;
          }
          // 属性也是实体类对象
          if (ot.getType().isEntityType()) {
            String foreignKey = ((EntityType) ot.getType()).getIdPropertyName();
            if (attr.endsWith("." + foreignKey)) {
              if (null == value) {
                setValue(parentAttr, null, entity);
              } else {
                Object foreignValue = PropertyUtils.getProperty(entity, attr);
                // 如果外键已经有值
                if (null != foreignValue) {
                  if (!foreignValue.toString().equals(value.toString())) {
                    setValue(parentAttr, null, entity);
                    initProperty(entity, entityName, parentAttr);
                    setValue(attr, value, entity);
                  }
                } else {
                  setValue(attr, value, entity);
                }
              }
            } else {
              setValue(attr, value, entity);
            }
          } else {
            setValue(attr, value, entity);
          }
        } catch (Exception e) {
          logger.error("error attr:[" + attr + "] value:[" + value + "]", e);
        }
      }
      if (logger.isDebugEnabled()) {
        logger.debug("populate attr:[" + attr + "] value:[" + value + "]");
      }
    }
    return entity;
  }

  private void setValue(final String attr, final Object value, final Object target) {
    try {
      beanUtils.copyProperty(target, attr, value);
    } catch (Exception e) {
      logger.error("copy property failure:[class:" + target.getClass().getName() + " attr:" + attr
          + " value:" + value + "]:", e);
    }
  }
}
