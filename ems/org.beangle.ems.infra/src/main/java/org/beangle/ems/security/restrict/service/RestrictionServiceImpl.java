/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.Authority;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.Resource;
import org.beangle.ems.security.SecurityUtils;
import org.beangle.ems.security.User;
import org.beangle.ems.security.PropertyMeta;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.ems.security.service.AuthorityService;
import org.beangle.ems.security.service.UserDataProvider;
import org.beangle.ems.security.service.UserDataResolver;
import org.beangle.ems.security.service.UserService;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.Condition;
import org.beangle.model.query.builder.OqlBuilder;

public class RestrictionServiceImpl extends BaseServiceImpl implements RestrictionService {

	protected UserService userService;

	protected Map<String, UserDataProvider> providers = CollectUtils.newHashMap();

	protected UserDataResolver dataResolver;

	protected AuthorityService authorityService;

	/**
	 * 查询用户在指定模块的数据权限
	 */
	@SuppressWarnings("unchecked")
	public List<Restriction> getRestrictions(final User user, final Resource resource) {
		List<Restriction> restrictions = CollectUtils.newArrayList();
		// 权限上的限制
		if (null != resource) restrictions.addAll(getAuthorityRestrictions(user, resource));
		// 用户组自身限制
		for (Group group : authorityService.filter(user.getGroups(), resource)) {
			restrictions.addAll(group.getRestrictions());
		}
		return restrictions;
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

	private List<?> getPropertyValues(PropertyMeta field) {
		if (null == field.getSource()) return Collections.emptyList();
		String source = field.getSource();
		String prefix = StringUtils.substringBefore(source, ":");
		source = StringUtils.substringAfter(source, ":");
		UserDataProvider provider = providers.get(prefix);
		if (null != provider) {
			return provider.getData(field, source);
		} else {
			throw new RuntimeException("not support data provider:" + prefix);
		}
	}

	public List<?> getPropertyValues(String propertyName) {
		return getPropertyValues(getUserProperty(propertyName));
	}

	public Object getPropertyValue(String propertyName, User user) {
		PropertyMeta prop=getUserProperty(propertyName);
		return unmarshal(user.getProperty(prop),prop);
	}

	/**
	 * 获取数据限制的某个属性的值
	 * 
	 * @param property
	 * @param restriction
	 * @return
	 */
	private Object unmarshal(String value, PropertyMeta property) {
		try {
			List<Object> returned = dataResolver.unmarshal(property, value);
			if (property.isMultiple()) {
				return returned;
			} else {
				return (1 != returned.size()) ? null : returned.get(0);
			}
		} catch (Exception e) {
			logger.error("exception with param type:" + property.getValueType() + " value:" + value, e);
			return null;
		}
	}

	public void apply(OqlBuilder<?> query, Collection<? extends Restriction> restrictions) {
		StringBuilder conBuffer = new StringBuilder();
		List<Object> paramValues = CollectUtils.newArrayList();
		int index = 0;
		for (final Restriction restriction : restrictions) {
			// 处理限制对应的模式
			if (StringUtils.isEmpty(restriction.getContent())) {
				continue;
			}
			String patternContent = restriction.getContent();
			patternContent = StringUtils.replace(patternContent, "{alias}", query.getAlias());
			String[] contents = StringUtils.split(StringUtils.replace(patternContent, " and ", "$"), "$");

			User user = userService.get(SecurityUtils.getUserId());
			StringBuilder singleConBuf = new StringBuilder("(");
			for (int i = 0; i < contents.length; i++) {
				String content = contents[i];
				Condition c = new Condition(content);
				List<String> params = c.getParamNames();
				for (final String paramName : params) {
					PropertyMeta prop = getUserProperty(paramName);
					String value = user.getProperty(prop);
					if (StringUtils.isNotEmpty(value)) {
						if (value.equals(Restriction.ALL)) {
							content = "";
						} else {
							content = StringUtils.replace(content, ":" + prop.getName(), ":" + prop.getName()
									+ index);
							paramValues.add(unmarshal(value, prop));
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

	private PropertyMeta getUserProperty(String fieldName) {
		List<PropertyMeta> fields = entityDao.get(PropertyMeta.class, "name", fieldName);
		if (1 != fields.size()) { throw new RuntimeException("bad pattern parameter named :" + fieldName); }
		return fields.get(0);
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setAuthorityService(AuthorityService authorityService) {
		this.authorityService = authorityService;
	}

	public Map<String, UserDataProvider> getProviders() {
		return providers;
	}

	public void setProviders(Map<String, UserDataProvider> providers) {
		this.providers = providers;
	}

	public void setDataResolver(UserDataResolver dataResolver) {
		this.dataResolver = dataResolver;
	}

}
