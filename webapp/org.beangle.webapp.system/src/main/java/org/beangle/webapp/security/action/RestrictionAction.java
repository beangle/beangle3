/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.entity.Model;
import org.beangle.security.blueprint.Authority;
import org.beangle.security.blueprint.GroupMember;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.restrict.RestrictField;
import org.beangle.security.blueprint.restrict.RestrictPattern;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.restrict.RestrictionHolder;
import org.beangle.struts2.helper.Params;
import org.beangle.webapp.security.helper.RestrictionHelper;

public class RestrictionAction extends SecurityActionSupport {

	public String tip() {
		return forward();
	}

	/**
	 * 删除数据限制权限
	 */
	public String remove() {
		Restriction restriction = getRestriction();
		RestrictionHolder holer = new RestrictionHelper(entityDao).getHolder();
		holer.getRestrictions().remove(restriction);
		entityDao.saveOrUpdate(holer);
		return redirect("info", "info.delete.success");
	}

	/**
	 * 查看限制资源界面
	 */
	public String info() {
		RestrictionHelper helper = new RestrictionHelper(entityDao);
		helper.setRestrictionService(restrictionService);
		helper.populateInfo(helper.getHolder());
		return forward();
	}

	public String save() {
		Restriction restriction = getRestriction();
		RestrictionHolder holder = new RestrictionHelper(entityDao).getHolder();
		List<Restriction> myRestrictions = getMyRestrictions(restriction.getPattern(), holder);
		Set<RestrictField> ignoreParams = getIgnoreParams(myRestrictions);
		boolean isAdmin = isAdmin(getUser());
		for (RestrictField param : restriction.getPattern().getObject().getFields()) {
			String value = get(param.getName());
			if ((ignoreParams.contains(param) || isAdmin) && getBool("ignoreParam" + param.getId())) {
				restriction.setItem(param, "*");
			} else {
				if (StringUtils.isEmpty(value)) {
					restriction.getItems().remove(param.getId());
				} else {
					restriction.setItem(param, value);
				}
			}
		}
		if (restriction.getItems().isEmpty()) {
			holder.getRestrictions().remove(restriction);
			entityDao.saveOrUpdate(holder);
			return redirect("info", "info.save.success");
		} else {
			if (!restriction.isPersisted()) {
				holder.getRestrictions().add(restriction);
				entityDao.saveOrUpdate(holder);
			} else {
				entityDao.saveOrUpdate(
						(String) RestrictionHelper.restrictionTypeMap.get(get("restrictionType")),
						restriction);
			}
			return redirect("info", "info.save.success");
		}
	}

	/**
	 * 编辑权限<br>
	 */
	public String edit() {
		// 取得各参数的值
		Restriction restriction = getRestriction();
		boolean isAdmin = isAdmin(getUser());
		Map<String, Object> mngParams = CollectUtils.newHashMap();
		Map<String, Object> aoParams = CollectUtils.newHashMap();
		List<Restriction> myRestricitons = getMyRestrictions(restriction.getPattern(),
				restriction.getHolder());
		Set<RestrictField> ignores = getIgnoreParams(myRestricitons);
		put("ignoreParams", ignores);
		Set<RestrictField> holderIgnoreParams = CollectUtils.newHashSet();
		put("holderIgnoreParams", holderIgnoreParams);
		for (RestrictField param : restriction.getPattern().getObject().getFields()) {
			List<Object> mngParam = restrictionService.getValues(param);
			if (!isAdmin) {
				mngParam.retainAll(getMyRestrictionValues(myRestricitons, param.getName()));
			} else {
				ignores.add(param);
			}
			String paramValue = restriction.getItem(param);
			if ("*".equals(paramValue)) {
				holderIgnoreParams.add(param);
			}
			mngParams.put(param.getName(), mngParam);
			if (null == param.getSource()) {
				aoParams.put(param.getName(), paramValue);
			} else {
				Set<Object> aoParam = restrictionService.select(
						restrictionService.getValues(param), restriction, param);
				aoParams.put(param.getName(), aoParam);
			}
		}
		put("mngParams", mngParams);
		put("aoParams", aoParams);
		put("restriction", restriction);
		return forward();
	}

	private List<Restriction> getMyRestrictions(RestrictPattern pattern, RestrictionHolder holder) {
		String type = get("restrictionType");
		List<Restriction> restrictions = CollectUtils.newArrayList();
		User me = getUser();
		if (type.equals("user")) {
			restrictions = CollectUtils.newArrayList(((RestrictionHolder) me).getRestrictions());
		} else if (type.equals("group")) {
			for (GroupMember group : me.getGroups()) {
				restrictions.addAll(group.getGroup().getRestrictions());
			}
		} else if (type.equals("authority")) {
			restrictions = restrictionService.getAuthorityRestrictions(me,
					((Authority) holder).getResource());
		}
		List<Restriction> rt = CollectUtils.newArrayList();
		for (Restriction restriction : restrictions) {
			if (!restriction.getPattern().equals(pattern)) {
				continue;
			}
			rt.add(restriction);
		}
		return rt;
	}

	private Set<RestrictField> getIgnoreParams(List<Restriction> restrictions) {
		Set<RestrictField> ignores = CollectUtils.newHashSet();
		for (Restriction restriction : restrictions) {
			for (RestrictField param : restriction.getPattern().getObject().getFields()) {
				String value = restriction.getItem(param);
				if ("*".equals(value)) {
					ignores.add(param);
				}
			}
		}
		return ignores;
	}

	private List<Object> getMyRestrictionValues(List<Restriction> restrictions, String name) {
		List<Object> values = CollectUtils.newArrayList();
		for (Restriction restriction : restrictions) {
			RestrictField param = restriction.getPattern().getObject().getField(name);
			if (null != param) {
				String value = restriction.getItem(param);
				if (null != value) {
					values.addAll(restrictionService.select(restrictionService.getValues(param),
							restriction, param));
				}
			}
		}
		return values;
	}

	private Restriction getRestriction() {
		Long restrictionId = getLong("restriction.id");
		Restriction restriction = null;
		String entityName = (String) RestrictionHelper.restrictionTypeMap
				.get(get("restrictionType"));
		if (null == restrictionId) {
			restriction = (Restriction) Model.getEntityType(entityName).newInstance();
		} else {
			restriction = (Restriction) entityDao.get(entityName, restrictionId);
		}
		populate(Params.sub("restriction"), restriction, entityName);
		if (null == restrictionId) {
			restriction.setPattern((RestrictPattern) entityDao.get(RestrictPattern.class,
					restriction.getPattern().getId()));
		}
		return restriction;
	}
}
