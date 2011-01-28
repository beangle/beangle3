/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.service;

import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.GroupMember;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.restrict.RestrictField;
import org.beangle.security.blueprint.restrict.RestrictEntity;
import org.beangle.security.blueprint.restrict.RestrictPattern;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.restrict.RestrictionHolder;
import org.beangle.security.blueprint.service.UserService;

public class RestrictionServiceImpl extends BaseServiceImpl implements RestrictionService {

	protected UserService userService;

	protected Map<String, DataProvider> providers = CollectUtils.newHashMap();

	/**
	 * 查询用户在指定模块的数据权限
	 */
	@SuppressWarnings("unchecked")
	public List<Restriction> getRestrictions(final User user, final Resource resource) {
		List<Restriction> restrictions = CollectUtils.newArrayList();
		final Set<RestrictEntity> entities = CollectUtils.newHashSet(resource.getEntities());
		// 权限上的限制
		restrictions.addAll(getAuthorityRestrictions(user, resource));
		List<Group> groups = userService.getGroups(user, GroupMember.Ship.MEMBER);
		// 用户组自身限制
		for (Group group : groups) {
			restrictions.addAll(group.getRestrictions());
		}
		// 用户自身限制
		RestrictionHolder userHolder = (RestrictionHolder) user;
		restrictions.addAll(userHolder.getRestrictions());
		// 模式过滤
		return (List<Restriction>) CollectionUtils.select(restrictions, new Predicate() {
			public boolean evaluate(Object obj) {
				Restriction restriciton = (Restriction) obj;
				if (restriciton.isEnabled()
						&& entities.contains(restriciton.getPattern().getEntity())) return true;
				else return false;
			}
		});
	}

	public List<Restriction> getAuthorityRestrictions(User user, Resource resource) {
		OqlBuilder<Restriction> query = OqlBuilder.hql("select restriction from Authority r "
				+ "join r.group.users as user join r.restrictions as restriction"
				+ " where user=:user and r.resource=:resource" + " and restriction.enabled=true");
		Map<String, Object> params = CollectUtils.newHashMap();
		params.put("user", user);
		params.put("resource", resource);
		query.params(params);
		return entityDao.search(query);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List getValues(RestrictField param) {
		if (null == param.getSource()) return Collections.emptyList();
		String source = param.getSource();
		String prefix = StringUtils.substringBefore(source, ":");
		source=StringUtils.substringAfter(source, ":");
		DataProvider provider = providers.get(prefix);
		if (null != provider) {
			try {
				return provider.getData(Class.forName(param.getType()), source);
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		} else {
			throw new RuntimeException("not support data provider:" + prefix);
		}
	}

	/**
	 * 获取数据限制的某个属性的值
	 * 
	 * @param restriction
	 * @param field
	 * @return
	 */
	public Object getValue(Restriction restriction, RestrictField field) {
		String value = restriction.getItem(field);
		if (null == value) { return null; }
		if (ObjectUtils.equals(Restriction.ALL, value)) { return Restriction.ALL; }
		try {
			Constructor<?> con = Class.forName(field.getType()).getConstructor(
					new Class[] { String.class });
			if (StringUtils.isEmpty(value)) { return null; }
			if (StringUtils.contains(value, ',')) {
				Set<Object> valueSet = CollectUtils.newHashSet();
				String[] values = StringUtils.split(value, ",");
				for (int i = 0; i < values.length; i++) {
					valueSet.add(con.newInstance(new Object[] { values[i] }));
				}
				return valueSet;
			} else {
				return con.newInstance(new Object[] { value });
			}
		} catch (Exception e) {
			throw new RuntimeException("exception with param type:" + field.getType() + " value:"
					+ value, e);
		}
	}

	public <T> Set<T> select(Collection<T> values, List<? extends Restriction> restrictions,
			RestrictField param) {
		Set<T> selected = CollectUtils.newHashSet();
		for (final Restriction restriction : restrictions) {
			selected.addAll(select(values, restriction, param));
		}
		return selected;
	}

	public <T> Set<T> select(Collection<T> values, final Restriction restriction,
			RestrictField param) {
		Set<T> selected = CollectUtils.newHashSet();
		String value = restriction.getItem(param);
		if (StringUtils.isNotEmpty(value)) {
			if (value.equals(Restriction.ALL)) {
				selected.addAll(values);
				return selected;
			}
			@SuppressWarnings("unchecked")
			final Set<T> paramValue = (Set<T>) getValue(restriction, param);
			for (T obj : values) {
				try {
					// if (paramValue.contains(PropertyUtils.getProperty(obj,
					// param.getEditor()
					// .getIdProperty()))) {
					if (paramValue.contains(obj)) {
						selected.add(obj);
					}
				} catch (Exception e) {
					throw new RuntimeException(e.getMessage());
				}
			}
		}
		return selected;
	}

	public void apply(OqlBuilder<?> query, Collection<? extends Restriction> restrictions) {
		String prefix = "(";
		StringBuilder conBuffer = new StringBuilder();
		List<Object> paramValues = CollectUtils.newArrayList();
		int index = 0;

		for (final Restriction restriction : restrictions) {
			// 处理限制对应的模式
			RestrictPattern pattern = restriction.getPattern();
			if (null == pattern || StringUtils.isEmpty(pattern.getContent())) {
				continue;
			}
			// FIXME pattern.getObject().getType().equals(anObject))
			// if(pattern.getObject().getType().equals(anObject))
			String patternContent = pattern.getContent();
			patternContent = StringUtils.replace(patternContent, "{alias}", query.getAlias());
			String[] contents = StringUtils.split(
					StringUtils.replace(patternContent, " and ", "$"), "$");

			StringBuilder singleConBuf = new StringBuilder(prefix);
			for (int i = 0; i < contents.length; i++) {
				String content = contents[i];
				Condition c = new Condition(content);
				List<String> params = c.getParamNames();
				for (final String paramName : params) {
					RestrictField param = pattern.getEntity().getField(paramName);
					String value = restriction.getItem(param);
					if (StringUtils.isNotEmpty(value)) {
						if (value.equals(Restriction.ALL)) {
							content = "";
						} else {
							content = StringUtils.replace(content, ":" + param.getName(), ":"
									+ param.getName() + index);
							paramValues.add(getValue(restriction, param));
						}
					} else {
						throw new RuntimeException(paramName + " had not been initialized");
					}
				}
				if (singleConBuf.length() > 1 && content.length() > 0) {
					singleConBuf.append(" and ");
				}
				singleConBuf.append(content);
			}

			if (singleConBuf.length() > 1) {
				singleConBuf.append(')');
				if (conBuffer.length() > 0) {
					conBuffer.append(" or ");
				}
				conBuffer.append(singleConBuf.toString());
			}
			index++;
		}
		if (conBuffer.length() > 0) {
			Condition con = new Condition(conBuffer.toString());
			con.params(paramValues);
			query.where(con);
		}
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public Map<String, DataProvider> getProviders() {
		return providers;
	}

	public void setProviders(Map<String, DataProvider> providers) {
		this.providers = providers;
	}

}
