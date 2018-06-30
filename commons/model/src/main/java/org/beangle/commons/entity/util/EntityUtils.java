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
package org.beangle.commons.entity.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;
import org.beangle.commons.bean.PropertyUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.Component;
import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.TemporalAt;
import org.beangle.commons.entity.TemporalOn;
import org.beangle.commons.entity.metadata.EntityType;
import org.beangle.commons.entity.metadata.Model;
import org.beangle.commons.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实体类辅助工具箱
 *
 * @author chaostone 2005-10-31
 * @version $Id: $
 */
public final class EntityUtils {

  private static final Logger logger = LoggerFactory.getLogger(EntityUtils.class);

  private EntityUtils() {
  }

  /**
   * <p>
   * extractIds.
   * </p>
   *
   * @param entities a {@link java.util.Collection} object.
   * @param <T> a T object.
   * @return a {@link java.util.List} object.
   */
  public static <T extends Entity<?>> List<?> extractIds(Collection<T> entities) {
    List<Object> idList = CollectUtils.newArrayList();
    for (Iterator<T> iter = entities.iterator(); iter.hasNext();) {
      Entity<?> element = iter.next();
      try {
        idList.add(PropertyUtils.getProperty(element, "id"));
      } catch (Exception e) {
        logger.error("getProperty error", e);
        continue;
      }
    }
    return idList;
  }

  /**
   * <p>
   * getCommandName.
   * </p>
   *
   * @param clazz a {@link java.lang.Class} object.
   * @return a {@link java.lang.String} object.
   */
  public static String getCommandName(Class<?> clazz) {
    String name = clazz.getName();
    return Strings.uncapitalize(name.substring(name.lastIndexOf('.') + 1));
  }

  /**
   * <p>
   * getCommandName.
   * </p>
   *
   * @param entityName a {@link java.lang.String} object.
   * @return a {@link java.lang.String} object.
   */
  public static String getCommandName(String entityName) {
    return Strings.uncapitalize(Strings.substringAfterLast(entityName, "."));
  }

  /**
   * <p>
   * getCommandName.
   * </p>
   *
   * @param obj a {@link java.lang.Object} object.
   * @return a {@link java.lang.String} object.
   */
  public static String getCommandName(Object obj) {
    String name = obj.getClass().getName();
    int dollar = name.indexOf("_$$_");
    if (-1 == dollar) {
      name = name.substring(name.lastIndexOf('.') + 1);
    } else {
      name = name.substring(name.lastIndexOf('.') + 1, dollar);
    }
    return Strings.uncapitalize(name);
  }

  /**
   * <p>
   * extractIdSeq.
   * </p>
   *
   * @param entities a {@link java.util.Collection} object.
   * @param <T> a T object.
   * @return a {@link java.lang.String} object.
   */
  public static <T extends Entity<?>> String extractIdSeq(Collection<T> entities) {
    if (null == entities || entities.isEmpty()) { return ""; }
    StringBuilder idBuf = new StringBuilder(",");
    for (Iterator<T> iter = entities.iterator(); iter.hasNext();) {
      T element = iter.next();
      try {
        idBuf.append(String.valueOf(PropertyUtils.getProperty(element, "id")));
        idBuf.append(',');
      } catch (Exception e) {
        throw new RuntimeException(e.getMessage());
      }
    }
    return idBuf.toString();
  }

  /**
   * 判断实体类中的属性是否全部为空
   *
   * @param entity a {@link org.beangle.commons.entity.Entity} object.
   * @param ignoreDefault
   *          忽略数字和字符串的默认值
   * @return a boolean.
   */
  public static boolean isEmpty(Entity<?> entity, boolean ignoreDefault) {
    try {
      for (final String attr : PropertyUtils.getWritableProperties(entity.getClass())) {
        Object value = PropertyUtils.getProperty(entity, attr);
        if (null == value) continue;
        if (ignoreDefault) {
          if (value instanceof Number) {
            if (((Number) value).intValue() != 0) { return false; }
          } else if (value instanceof String) {
            String str = (String) value;
            if (Strings.isNotEmpty(str)) { return false; }
          } else {
            return false;
          }
        } else {
          return false;
        }
      }
    } catch (Exception e) {
      logger.error("isEmpty error", e);
    }
    return true;
  }

  /**
   * 为了取出CGLIB代来的代理命名
   *
   * @param clazz a {@link java.lang.Class} object.
   * @return a {@link java.lang.String} object.
   */
  public static String getEntityClassName(Class<?> clazz) {
    String name = clazz.getName();
    int dollar = name.indexOf("_$$_");
    if (-1 == dollar) {
      return name;
    } else {
      return name.substring(0, dollar);
    }
  }

  /**
   * <p>
   * isExpired.
   * </p>
   *
   * @param entity a {@link org.beangle.commons.entity.TemporalAt} object.
   * @return a boolean.
   */
  public static boolean isExpired(TemporalAt entity) {
    Date now = new Date();
    if (null == entity.getBeginAt()) return true;
    return entity.getBeginAt().after(now) || (null != entity.getEndAt() && !now.before(entity.getEndAt()));
  }

  public static boolean isExpired(TemporalOn entity) {
    Date now = new Date();
    if (null == entity.getBeginOn()) return true;
    return entity.getBeginOn().after(now) || (null != entity.getEndOn() && !now.before(entity.getEndOn()));
  }

  /**
   * 清除实体类中引用的无效(外键)属性.<br>
   *
   * <pre>
   *  hibernate在保存对象是检查对象是否引用了未持久化的对象，如果引用则保存出错.
   *  实体类(Entity)是否已经持久化通过检查其id是否为有效值来判断的.
   *  对于实体类中包含的
   * &lt;code&gt;
   * Component
   * &lt;/code&gt;
   *  则递归处理
   *  因为集合为空仍有其含义，这里对集合不做随意处理.如
   *  // evict collection
   *  if (value instanceof Collection) {
   *    if (((Collection) value).isEmpty())
   *    map.put(attr, null);
   *  }
   * </pre>
   *
   * @see ValidEntityPredicate
   * @param entity
   */
  public static void evictEmptyProperty(Object entity) {
    if (null == entity) { return; }
    boolean isEntity = false;
    if (entity instanceof Entity) {
      isEntity = true;
    }
    BeanMap map = new BeanMap(entity);
    List<String> attList = new ArrayList<String>();
    for (Object o : map.keySet()) {
      attList.add((String) o);
    }
    attList.remove("class");
    for (String attr : attList) {
      if (!PropertyUtils.isWriteable(entity, attr)) {
        continue;
      }
      Object value = map.get(attr);
      if (null == value) {
        continue;
      } else {
        // evict invalid entity key
        if (isEntity && attr.equals("id")) {
          if (!ValidEntityKeyPredicate.Instance.apply(value)) {
            map.put(attr, null);
          }
        }
        // evict invalid entity
        if (value instanceof Entity && !ValidEntityPredicate.Instance.apply(value)) {
          map.put(attr, null);
        } else if (value instanceof Component) {
          // evict component recursively
          evictEmptyProperty(value);
        }
      }
    }
  }

  public static void populate(Map params, Entity entity) {
    if (null == entity) {
      throw new RuntimeException("Cannot populate to null.");
    } else {
      EntityType type = Model.getType(entity.getClass());
      Model.getPopulator().populate(entity, type, params);
    }
  }

  /**
   * <pre>
   *    merge函数主要用来将页面回传对象(orig)的参数合并到数据库已经存在的对象中.（浅拷贝）
   *    合并的依据是待合并对象是否为空.如果对象是平面的component 则递归处理，如果是entity则检查是否是
   *    有效的持久化对象（id不为空或者0）,那么将该对象复制过去，否则复制null.
   *
   *    这些页面对象中的集合属性不包括在复制和合并范围内.因为大多数页面参数中，很少有集合(Set)参数
   *    况且如果把空集合合并到已有对象上，反而会造成(hibernate)删除已有对象和这些对象的关联，甚至对象本身
   *    将orig中不为空的属性复制到dest中 added at
   *    两个对象必须是同一类型的
   * </pre>
   *
   * @deprecated
   * @see EntityUtil#evictEmptyProperty
   * @param dest
   * @param orig
   *          影响dest
   */
  public static void merge(Object dest, Object orig) {
    String attr = "";
    try {
      Set attrs = org.apache.commons.beanutils.PropertyUtils.describe(orig).keySet();
      attrs.remove("class");
      for (Iterator it = attrs.iterator(); it.hasNext();) {
        attr = (String) it.next();
        if (!PropertyUtils.isWriteable(orig, attr)) {
          continue;
        }
        Object value = PropertyUtils.getProperty(orig, attr);
        if (null != value) {
          if (value instanceof Component) {
            Object savedValue = PropertyUtils.getProperty(dest, attr);
            if (null == savedValue) {
              PropertyUtils.setProperty(dest, attr, value);
            } else {
              merge(savedValue, value);
            }
          } else if (value instanceof Collection) {
            continue;
          } else if (value instanceof Entity) {
            Serializable key = (Serializable) PropertyUtils.getProperty(value, "id");
            if (null == key) {
              continue;
            } else if (new EmptyKeyPredicate().apply(key)) {
              PropertyUtils.setProperty(dest, attr, null);
            } else {
              PropertyUtils.setProperty(dest, attr, value);
            }
          } else {
            PropertyUtils.setProperty(dest, attr, value);
          }
        }
      }
    } catch (Exception e) {
      logger.error("meger error", e);
      if (logger.isDebugEnabled()) {
        logger.debug("error occur in reflection of attr:" + attr + " of entity " + dest.getClass().getName());
      }
      return;
    }
  }

}
