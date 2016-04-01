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
package org.beangle.commons.entity.metadata.impl;

import static org.beangle.commons.bean.PropertyUtils.copyProperty;
import static org.beangle.commons.bean.PropertyUtils.getProperty;
import static org.beangle.commons.bean.PropertyUtils.setProperty;

import java.util.Map;

import org.beangle.commons.conversion.Conversion;
import org.beangle.commons.conversion.impl.DefaultConversion;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.ObjectAndType;
import org.beangle.commons.entity.metadata.Populator;
import org.beangle.commons.entity.metadata.Type;
import org.beangle.commons.lang.Objects;
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

  private Conversion conversion;

  /**
   * <p>
   * Constructor for ConvertPopulatorBean.
   * </p>
   */
  public ConvertPopulatorBean() {
    this(DefaultConversion.Instance);
  }

  /**
   * <p>
   * Constructor for ConvertPopulatorBean.
   * </p>
   */
  public ConvertPopulatorBean(Conversion conversion) {
    this.conversion = conversion;
  }

  /**
   * Initialize target's attribuate path,Return the last property value and type.
   */
  public ObjectAndType initProperty(final Object target, Type type, final String attr) {
    Object propObj = target;
    Object property = null;

    int index = 0;
    String[] attrs = Strings.split(attr, ".");
    while (index < attrs.length) {
      try {
        property = getProperty(propObj, attrs[index]);
        Type propertyType = type.getPropertyType(attrs[index]);
        // 初始化
        if (null == propertyType) {
          logger.error("Cannot find property type [{}] of {}", attrs[index], propObj.getClass());
          throw new RuntimeException("Cannot find property type " + attrs[index] + " of "
              + propObj.getClass().getName());
        }
        if (null == property) {
          property = propertyType.newInstance();
          setProperty(propObj, attrs[index], property);
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
   * 安静的拷贝属性，如果属性非法或其他错误则记录日志
   */
  public boolean populateValue(final Object target, EntityType type, final String attr, final Object value) {
    try {
      if (attr.indexOf('.') > -1) {
        ObjectAndType ot = initProperty(target, type, Strings.substringBeforeLast(attr, "."));
        String lastAttr = Strings.substringAfterLast(attr, ".");
        setProperty(ot.getObj(), lastAttr, convert(ot.getType(), lastAttr, value));
      } else {
        setProperty(target, attr, convert(type, attr, value));
      }
      return true;
    } catch (Exception e) {
      logger.warn("copy property failure:[class:" + type.getEntityName() + " attr:" + attr + " value:"
          + value + "]:", e);
      return false;
    }
  }

  /**
   * 将params中的属性([attr(string)->value(object)]，放入到实体类中。
   * <p>
   * 如果引用到了别的实体，那么<br>
   * 如果params中的id为null，则将该实体的置为null.<br>
   * 否则新生成一个实体，将其id设为params中指定的值。 空字符串按照null处理
   */
  public Object populate(Object entity, EntityType type, Map<String, Object> params) {
    for (final Map.Entry<String, Object> paramEntry : params.entrySet()) {
      String attr = paramEntry.getKey();
      Object value = paramEntry.getValue();
      if (value instanceof String) {
        if (Strings.isEmpty((String) value)) value = null;
        else if (TRIM_STR) value = ((String) value).trim();
      }
      // 主键
      // if (type.isEntityType() && attr.equals(((EntityType) type).getIdName())) {
      // setProperty(entity, attr, convert(type, attr, value));
      // continue;
      // }
      // 普通属性
      if (-1 == attr.indexOf('.')) {
        copyValue(entity, attr, value);
      } else {
        String parentAttr = Strings.substring(attr, 0, attr.lastIndexOf('.'));
        try {
          ObjectAndType ot = initProperty(entity, type, parentAttr);
          if (null == ot) {
            logger.error("error attr:[" + attr + "] value:[" + value + "]");
            continue;
          }
          // 属性也是实体类对象
          if (ot.getType().isEntityType()) {
            String foreignKey = ((EntityType) ot.getType()).getIdName();
            if (attr.endsWith("." + foreignKey)) {
              if (null == value) {
                copyValue(entity, parentAttr, null);
              } else {
                Object oldValue = getProperty(entity, attr);
                Object newValue = convert(ot.getType(), foreignKey, value);
                if (!Objects.equals(oldValue, newValue)) {
                  // 如果外键已经有值
                  if (null != oldValue) {
                    copyValue(entity, parentAttr, null);
                    initProperty(entity, type, parentAttr);
                  }
                  setProperty(entity, attr, newValue);
                }
              }
            } else {
              copyValue(entity, attr, value);
            }
          } else {
            copyValue(entity, attr, value);
          }
        } catch (Exception e) {
          logger.error("error attr:[" + attr + "] value:[" + value + "]", e);
        }
      }
    }
    return entity;
  }

  private Object convert(Type type, String attr, Object value) {
    Object attrValue = null;
    if (null != value) attrValue = conversion.convert(value, type.getPropertyType(attr).getReturnedClass());
    return attrValue;
  }

  private Object copyValue(final Object target, final String attr, final Object value) {
    return copyProperty(target, attr, value, conversion);
  }
}
