/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.service;

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
import org.beangle.ems.security.Authority;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.User;
import org.beangle.ems.security.restrict.RestrictEntity;
import org.beangle.ems.security.restrict.RestrictField;
import org.beangle.ems.security.restrict.RestrictPattern;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.ems.security.restrict.RestrictionHolder;
import org.beangle.ems.security.service.AuthorityService;
import org.beangle.ems.security.service.UserService;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.OqlBuilder;

public class RestrictionServiceImpl extends BaseServiceImpl implements RestrictionService {

	protected UserService userService;

	protected Map<String, DataProvider> providers = CollectUtils.newHashMap();

	protected DataResolver dataResolver;

	protected AuthorityService authorityService;

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
		for (Group group : authorityService.filter(groups, resource)) {
			restrictions.addAll(group.getRestrictions());
		}
		// 用户自身限制
		RestrictionHolder<?> userHolder = user;
		restrictions.addAll(userHolder.getRestrictions());
		// 实体过滤
		return (List<Restriction>) CollectionUtils.select(restrictions, new Predicate() {
			public boolean evaluate(Object obj) {
				Restriction restriciton = (Restriction) obj;
				if (restriciton.isEnabled() && entities.contains(restriciton.getPattern().getEntity())) return true;
				else return false;
			}
		});
	}

	public List<Restriction> getAuthorityRestrictions(User user, Resource resource) {
		OqlBuilder<Restriction> query = OqlBuilder.hql("select restriction from " + Authority.class.getName()
				+ " r " + "join r.group.members as gmember join r.restrictions as restriction"
				+ " where gmember.user=:user and gmember.member=true and r.resource=:resource"
				+ " and restriction.enabled=true");
		Map<String, Object> params = CollectUtils.newHashMap();
		params.put("user", user);
		params.put("resource", resource);
		query.params(params);
		return entityDao.search(query);
	}

	private List<?> getFieldValues(RestrictField field) {
		if (null == field.getSource()) return Collections.emptyList();
		String source = field.getSource();
		String prefix = StringUtils.substringBefore(source, ":");
		source = StringUtils.substringAfter(source, ":");
		DataProvider provider = providers.get(prefix);
		if (null != provider) {
			return provider.getData(field, source);
		} else {
			throw new RuntimeException("not support data provider:" + prefix);
		}
	}

	public List<?> getFieldValues(String fieldName) {
		return getFieldValues(getRestrictField(fieldName));
	}

	public List<?> getFieldValues(String fieldName, List<? extends Restriction> restrictions) {
		Set<Object> values = CollectUtils.newHashSet();
		RestrictField field = getRestrictField(fieldName);
		boolean gotIt = false;
		for (Restriction restriction : restrictions) {
			Object value = getFieldValue(field, restriction);
			if (null != value) {
				gotIt = true;
				if (value instanceof Collection<?>) {
					values.addAll((Collection<?>) value);
				} else {
					values.add(value);
				}
			}
		}
		if (!gotIt) {
			return getFieldValues(field);
		} else {
			return CollectUtils.newArrayList(values);
		}
	}

	/**
	 * 获取数据限制的某个属性的值
	 * 
	 * @param field
	 * @param restriction
	 * @return
	 */
	public Object getFieldValue(RestrictField field, Restriction restriction) {
		String value = restriction.getItem(field);
		if (StringUtils.isEmpty(value)) return null;
		if (ObjectUtils.equals(Restriction.ALL, value)) { return getFieldValues(field); }
		try {
			List<Object> returned = dataResolver.unmarshal(field, value);
			if (field.isMultiple()) {
				return returned;
			} else {
				return (1 != returned.size()) ? null : returned.get(0);
			}
		} catch (Exception e) {
			logger.error("exception with param type:" + field.getType() + " value:" + value, e);
			return null;
		}
	}

	public void apply(OqlBuilder<?> query, Collection<? extends Restriction> restrictions) {
		StringBuilder conBuffer = new StringBuilder();
		List<Object> paramValues = CollectUtils.newArrayList();
		int index = 0;
		for (final Restriction restriction : restrictions) {
			// 处理限制对应的模式
			RestrictPattern pattern = restriction.getPattern();
			if (null == pattern || StringUtils.isEmpty(pattern.getContent())) {
				continue;
			}
			String patternContent = pattern.getContent();
			patternContent = StringUtils.replace(patternContent, "{alias}", query.getAlias());
			String[] contents = StringUtils.split(StringUtils.replace(patternContent, " and ", "$"), "$");

			StringBuilder singleConBuf = new StringBuilder("(");
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
							content = StringUtils.replace(content, ":" + param.getName(),
									":" + param.getName() + index);
							paramValues.add(getFieldValue(param, restriction));
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

	private RestrictField getRestrictField(String fieldName) {
		List<RestrictField> fields = entityDao.get(RestrictField.class, "name", fieldName);
		if (1 != fields.size()) { throw new RuntimeException("bad pattern parameter named :" + fieldName); }
		return fields.get(0);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public Map<String, DataProvider> getProviders() {
		return providers;
	}

	public void setProviders(Map<String, DataProvider> providers) {
		this.providers = providers;
	}

	public void setDataResolver(DataResolver dataResolver) {
		this.dataResolver = dataResolver;
	}

}
