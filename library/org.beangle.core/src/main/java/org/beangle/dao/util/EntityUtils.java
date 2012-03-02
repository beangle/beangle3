/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.util;

import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.collection.CollectUtils;
import org.beangle.dao.Entity;
import org.beangle.dao.pojo.TemporalActiveEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 实体类辅助工具箱
 * 
 * @author chaostone 2005-10-31
 */
public final class EntityUtils {

	private static final Logger logger = LoggerFactory.getLogger(EntityUtils.class);

	private EntityUtils() {
	}

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

	public static String getCommandName(Class<?> clazz) {
		String name = clazz.getName();
		return StringUtils.uncapitalize(name.substring(name.lastIndexOf('.') + 1));
	}

	public static String getCommandName(String entityName) {
		return StringUtils.uncapitalize(StringUtils.substringAfterLast(entityName, "."));
	}

	public static String getCommandName(Object obj) {
		String name = obj.getClass().getName();
		int dollar = name.indexOf('$');
		if (-1 == dollar) {
			name = name.substring(name.lastIndexOf('.') + 1);
		} else {
			name = name.substring(name.lastIndexOf('.') + 1, dollar);
		}
		return StringUtils.uncapitalize(name);
	}

	public static <T extends Entity<?>> String extractIdSeq(Collection<T> entities) {
		if (null == entities || entities.isEmpty()) { return ""; }
		StringBuilder idBuf = new StringBuilder(",");
		for (Iterator<T> iter = entities.iterator(); iter.hasNext();) {
			T element = iter.next();
			try {
				idBuf.append(PropertyUtils.getProperty(element, "id"));
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
	 * @param entity
	 * @param ignoreDefault
	 *        忽略数字和字符串的默认值
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Entity<?> entity, boolean ignoreDefault) {
		BeanMap map = new BeanMap(entity);
		List<String> attList = CollectUtils.newArrayList();
		attList.addAll(map.keySet());
		attList.remove("class");
		try {
			for (final String attr : attList) {
				if (!PropertyUtils.isWriteable(entity, attr)) {
					continue;
				}
				Object value = map.get(attr);
				if (null == value) {
					continue;
				}
				if (ignoreDefault) {
					if (value instanceof Number) {
						if (((Number) value).intValue() != 0) { return false; }
					} else if (value instanceof String) {
						String str = (String) value;
						if (StringUtils.isNotEmpty(str)) { return false; }
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
	 * @param clazz
	 * @return
	 */
	public static String getEntityClassName(Class<?> clazz) {
		String name = clazz.getName();
		int dollar = name.indexOf('$');
		if (-1 == dollar) {
			return name;
		} else {
			return name.substring(0, dollar);
		}
	}

	public static boolean isExpired(TemporalActiveEntity entity) {
		Date now = new Date();
		if (null == entity.getEffectAt()) return true;
		return entity.getEffectAt().after(now)
				|| (null != entity.getInvalidAt() && !now.before(entity.getInvalidAt()));
	}

}
