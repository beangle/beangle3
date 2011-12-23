/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate;

import java.sql.Date;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.collection.CollectUtils;
import org.beangle.model.Component;
import org.beangle.model.Entity;
import org.beangle.model.util.ValidEntityKeyPredicate;
import org.hibernate.Criteria;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 条件查询工具类 主要为简单实体查询提供帮助. 对样例（Example）查询提供了扩展对实体类的标识符和排除非空属性上（包括""和0）提供更进一步的支持，
 * 该工具类多对一关联也提供了查询支持. 对于实体类中的属性映射为Component的在原来的Example就提供支持，不用另外写给予他们属性的查询.
 * 
 * @deprecated
 * @author chaostone 2005-10-28
 */
@SuppressWarnings("unchecked")
public final class CriterionUtils {

	private CriterionUtils() {
	}

	private static final Logger logger = LoggerFactory.getLogger(CriterionUtils.class);

	public static void addCriterionsFor(Criteria criteria, List<? extends Criterion> criterions) {
		for (Iterator<? extends Criterion> iter = criterions.iterator(); iter.hasNext();) {
			criteria.add(iter.next());
		}
	}

	public static List<Criterion> getEntityCriterions(Object entity) {
		return getEntityCriterions("", entity, null, MatchMode.ANYWHERE, true);
	}

	public static List<Criterion> getEntityCriterions(Object entity, boolean ignoreZero) {
		return getEntityCriterions("", entity, null, MatchMode.ANYWHERE, ignoreZero);
	}

	public static List<Criterion> getEntityCriterions(Object entity, String[] excludePropertes) {
		return getEntityCriterions("", entity, excludePropertes, MatchMode.ANYWHERE, true);
	}

	public static List<Criterion> getEntityCriterions(String nestedName, Object entity) {
		return getEntityCriterions(nestedName, entity, null, MatchMode.ANYWHERE, true);
	}

	public static List<Criterion> getEntityCriterions(String nestedName, Object entity,
			String[] excludePropertes) {
		return getEntityCriterions(nestedName, entity, excludePropertes, MatchMode.ANYWHERE, true);
	}

	public static Example getExampleCriterion(Object entity) {
		return getExampleCriterion(entity, null, MatchMode.ANYWHERE);
	}

	public static Example getExampleCriterion(Object entity, String[] excludePropertes, MatchMode mode) {
		Example example = Example.create(entity).setPropertySelector(new NotEmptyPropertySelector());
		if (null != mode) {
			example.enableLike(mode);
		}
		if (null != excludePropertes) {
			for (int i = 0; i < excludePropertes.length; i++) {
				example.excludeProperty(excludePropertes[i]);
			}
		}
		return example;
	}

	/**
	 * 获得实体类的属性和多对一属性（主键）的查询条件. （包括外键和组件以及组件内的外键），字符串类型可以采用模糊查询.
	 * 
	 * @param entity
	 * @param excludePropertes
	 * @param mode
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static List<Criterion> getEntityCriterions(String nestedName, Object entity,
			String[] excludePropertes, MatchMode mode, boolean ignoreZero) {
		if (null == entity) { return Collections.emptyList(); }
		List<Criterion> criterions = CollectUtils.newArrayList();
		BeanMap map = new BeanMap(entity);
		Set keySet = map.keySet();
		Collection properties = null;
		if (null == excludePropertes) {
			List proList = CollectUtils.newArrayList();
			proList.addAll(keySet);
			properties = proList;
		} else {
			properties = CollectionUtils.subtract(keySet, Arrays.asList(excludePropertes));
		}
		properties.remove("class");

		for (Iterator iter = properties.iterator(); iter.hasNext();) {
			String propertyName = (String) iter.next();
			if (!PropertyUtils.isWriteable(entity, propertyName)) {
				continue;
			}
			Object value = map.get(propertyName);
			addCriterion(nestedName, entity, excludePropertes, propertyName, value, criterions, mode,
					ignoreZero);
		}
		return criterions;
	}

	public static List<Criterion> getEqCriterions(Object entity, String[] properties) {
		List<Criterion> criterions = CollectUtils.newArrayList();
		BeanMap map = new BeanMap(entity);
		for (int i = 0; i < properties.length; i++) {
			criterions.add(Restrictions.eq(properties[i], map.get(properties[i])));
		}
		return criterions;
	}

	public static List<Criterion> getForeignerCriterions(Object entity) {
		BeanMap map = new BeanMap(entity);
		return getForeignerCriterions(entity, map.keySet());
	}

	public static List<Criterion> getForeignerCriterions(Object entity, Collection<String> properties) {
		List<Criterion> criterions = CollectUtils.newArrayList();
		BeanMap map = new BeanMap(entity);
		for (Iterator<String> iter = properties.iterator(); iter.hasNext();) {
			String propertyName = iter.next();
			Object foreigner = map.get(propertyName);
			if (foreigner instanceof Entity) {
				BeanMap foreignerMap = new BeanMap(foreigner);
				Object foreignKey = foreignerMap.get("id");
				// 该值不能为空，而且要么不是String类型，要么是不空String类型变量.
				if (ValidEntityKeyPredicate.getInstance().evaluate(foreignKey)) {
					// 在查询中添加该键值.
					criterions.add(Restrictions.eq(propertyName + ".id", foreignKey));
				}
			}
		}
		return criterions;
	}

	public static List<Criterion> getForeignerCriterions(Object entity, String[] properties) {
		return getForeignerCriterions(entity, Arrays.asList(properties));
	}

	public static List<Criterion> getLikeCriterions(Object entity, String[] Properties) {
		return getLikeCriterions(entity, Properties, MatchMode.ANYWHERE);
	}

	/**
	 * 返回非空字符串属性的like条件列表
	 * 
	 * @param entity
	 * @param properties
	 * @param mode
	 * @return
	 */
	public static List<Criterion> getLikeCriterions(Object entity, String[] properties, MatchMode mode) {
		List<Criterion> criterions = CollectUtils.newArrayList();
		BeanMap map = new BeanMap(entity);
		for (int i = 0; i < properties.length; i++) {
			Object value = map.get(properties[i]);
			if ((value instanceof String) && (StringUtils.isNotEmpty((String) value))) {
				criterions.add(Restrictions.like(properties[i], (String) value, mode));
			}
		}
		return criterions;
	}

	/**
	 * 返回默认采取MatchMode.ANYWHERE的实体参数map
	 * 
	 * @param entity
	 * @return
	 */
	public static Map<String, Object> getParamsMap(Entity<?> entity) {
		if (null == entity) { return Collections.EMPTY_MAP; }
		return getParamsMap(entity, MatchMode.ANYWHERE);
	}

	/**
	 * 将一个实体类中的非空属性及其值.<br>
	 * 实体类中的component的属性将会级联描述，<br>
	 * 其内部的属性完全看作没有component包装一样，但结果map中的名称是component.attr形式的.
	 * 
	 * @param entity
	 *            ,传递null,返回空map.
	 * @param mode
	 *            若含有非空字符串，采用的like策略
	 * @return
	 */
	public static Map<String, Object> getParamsMap(Entity<?> entity, MatchMode mode) {
		if (null == entity) { return Collections.EMPTY_MAP; }
		Map<String, Object> datas = CollectUtils.newHashMap();
		String attr = "";
		try {
			Map<String, Object> beanMap = PropertyUtils.describe(entity);
			for (Iterator<String> iter = beanMap.keySet().iterator(); iter.hasNext();) {
				attr = iter.next();
				Object value = PropertyUtils.getProperty(entity, attr);
				if (value == null) {
					continue;
				} else {
					addTrivialAttr(datas, attr, value, mode);
					if (value instanceof Entity) {
						String key = "id";
						value = PropertyUtils.getProperty(entity, attr + "." + key);
						if (ValidEntityKeyPredicate.getInstance().evaluate(value)) {
							datas.put(attr + "." + key, value);
						}
					}
				}
			}
			return datas;
		} catch (Exception e) {
			logger.error("[converToMap]:error occur in converToMap of bean" + entity + "with attr named "
					+ attr, e);
		}
		return Collections.emptyMap();
	}

	static Criterion eqCriterion(String name, Object value) {
		logger.debug("[CriterionUtils]:name {} value {}", name, value);
		return Restrictions.eq(name, value);
	}

	static Criterion likeCriterion(String name, String value, MatchMode mode) {
		logger.debug("[CriterionUtils]:name {} value {}", name, value);
		return Restrictions.like(name, value, mode);
	}

	/**
	 * 添加一个查询条件
	 * 
	 * @param entity
	 * @param excludePropertes
	 * @param path
	 * @param value
	 * @param criterions
	 * @param mode
	 */
	private static void addCriterion(String nestedName, Object entity, String[] excludePropertes,
			String path, Object value, List<Criterion> criterions, MatchMode mode, boolean ignoreZero) {
		if (null == value) { return; }
		addPrimativeCriterion(nestedName + path, value, criterions, ignoreZero);

		if (value instanceof String) {
			if (StringUtils.isNotEmpty((String) value)) {
				criterions.add(likeCriterion(nestedName + path, (String) value, mode));
			}
		} else if (value instanceof Entity) {
			BeanMap foreignerMap = new BeanMap(value);
			Object foreignKey = foreignerMap.get("id");
			// 该值不能为空，而且要么不是String类型，要么是不空String类型变量.
			if (ValidEntityKeyPredicate.getInstance().evaluate(foreignKey)) {
				// 在查询中添加该键值.
				criterions.add(eqCriterion(nestedName + path + ".id", foreignKey));
			}
		} else if (value instanceof Component) {
			criterions.addAll(getComponentCriterions(nestedName, entity, path, excludePropertes, mode,
					ignoreZero));
		}
	}

	/**
	 * 针对内建数据类型和日期类型添加查询条件 因为很多从页面回传的""字符串在转化成数字时为0,所以这里忽略0
	 * 
	 * @param name
	 * @param value
	 * @param criterions
	 */
	private static void addPrimativeCriterion(String name, Object value, List<Criterion> criterions,
			boolean ignoreZero) {
		Criterion criterion = null;
		if (value instanceof Number) {
			if (ignoreZero) {
				if (0 != ((Number) value).intValue()) {
					criterion = eqCriterion(name, value);
				}
			} else {
				criterion = eqCriterion(name, value);
			}
		}
		if ((value instanceof Character) || (value instanceof Boolean)) {
			criterion = eqCriterion(name, value);
		}
		if ((value instanceof Date)) {
			criterion = eqCriterion(name, value);
		}
		if (null != criterion) {
			criterions.add(criterion);
		}
	}

	/**
	 * 为converToMap使用的私有方法
	 * 
	 * @param datas
	 * @param name
	 * @param value
	 * @param mode
	 */
	private static void addTrivialAttr(Map<String, Object> datas, String name, Object value, MatchMode mode) {
		if (value instanceof Number && ((Number) value).intValue() != 0) {
			datas.put(name, value);
		}
		if (value instanceof String && StringUtils.isNotBlank((String) value)) {
			StringBuilder strBuilder = new StringBuilder((String) value);
			if (mode.equals(MatchMode.ANYWHERE)) {
				strBuilder.insert(0, '%').append('%');
			} else if (mode.equals(MatchMode.START)) {
				strBuilder.append('%');
			} else if (mode.equals(MatchMode.END)) {
				strBuilder.insert(0, '%');
			}
			datas.put(name, strBuilder.toString());
		}
		if (value instanceof Component) {
			datas.putAll(converToMap(name, (Component) value, mode));
		}
		if (value instanceof Entity) {
			try {
				String key = "id";
				Object propertyValue = PropertyUtils.getProperty(value, key);
				if (ValidEntityKeyPredicate.getInstance().evaluate(propertyValue)) {
					datas.put(name + "." + key, propertyValue);
				}
			} catch (Exception e) {
				logger.error("getProperty error", e);
			}
		}
	}

	private static Map<String, Object> converToMap(String prefix, Component component, MatchMode mode) {
		if (null == component) { return Collections.EMPTY_MAP; }
		Map<String, Object> datas = CollectUtils.newHashMap();
		String attr = "";
		try {
			Map<String, Object> beanMap = PropertyUtils.describe(component);
			for (Iterator<String> iter = beanMap.keySet().iterator(); iter.hasNext();) {
				attr = iter.next();
				Object value = PropertyUtils.getProperty(component, attr);
				if (value == null) {
					continue;
				} else {
					addTrivialAttr(datas, prefix + "." + attr, value, mode);
				}
			}
			return datas;

		} catch (Exception e) {
			logger.error("[converToMap]:error occur in converToMap of component" + component
					+ "with attr named " + attr, e);

		}
		return Collections.EMPTY_MAP;
	}

	/**
	 * 返回实体类内部组件的查询条件
	 * 
	 * @param entity
	 * @param property
	 *            组件在实体类中的名称允许级联例如outcomponent.innercomponent
	 * @param excludePropertes
	 *            每个元素形式如entityProperty.componentProperty
	 * @param enableLike
	 * @return
	 */
	private static List<Criterion> getComponentCriterions(String nestedName, Object entity, String property,
			String[] excludePropertes, MatchMode mode, boolean ignoreZero) {
		List<Criterion> criterions = CollectUtils.newArrayList();
		Component component = null;
		try {
			component = (Component) PropertyUtils.getProperty(entity, property);
		} catch (Exception e) {
			return Collections.emptyList();
		}
		if (null == component) { return Collections.emptyList(); }
		BeanMap map = new BeanMap(component);
		Set<String> properties = map.keySet();
		Set<String> excludeSet = null;
		if (null == excludePropertes) {
			excludeSet = Collections.emptySet();
		} else {
			excludeSet = CollectUtils.newHashSet();
			excludeSet.addAll(Arrays.asList(excludePropertes));
		}
		for (Iterator<String> iter = properties.iterator(); iter.hasNext();) {
			String propertyName = iter.next();
			String cascadeName = property + "." + propertyName;
			if (excludeSet.contains(cascadeName) || "class".equals(propertyName)) {
				continue;
			}
			if (!PropertyUtils.isWriteable(component, propertyName)) {
				continue;
			}
			Object value = map.get(propertyName);

			addCriterion(nestedName, entity, excludePropertes, cascadeName, value, criterions, mode,
					ignoreZero);
		}
		return criterions;
	}

	public static void addSortListFor(Criteria criteria, List<org.beangle.collection.Order> sortList) {
		if (null == sortList) { return; }
		for (final org.beangle.collection.Order order : sortList) {
			if (order.isAscending()) {
				criteria.addOrder(Order.asc(order.getProperty()));
			} else {
				criteria.addOrder(Order.desc(order.getProperty()));
			}
		}
	}
}
