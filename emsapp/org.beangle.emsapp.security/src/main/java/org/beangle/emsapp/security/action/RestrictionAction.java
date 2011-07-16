/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.security.Authority;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;
import org.beangle.ems.security.restrict.RestrictField;
import org.beangle.ems.security.restrict.RestrictPattern;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.ems.security.restrict.RestrictionHolder;
import org.beangle.ems.security.restrict.service.DataResolver;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.emsapp.security.helper.RestrictionHelper;
import org.beangle.model.entity.Model;
import org.beangle.struts2.helper.Params;

public class RestrictionAction extends SecurityActionSupport {
	protected DataResolver idDataResolver;

	public String tip() {
		return forward();
	}

	/**
	 * 删除数据限制权限
	 */
	public String remove() {
		Restriction restriction = getRestriction();
		RestrictionHolder<Restriction> holer = new RestrictionHelper(entityDao).getHolder();
		holer.getRestrictions().remove(restriction);
		entityDao.remove(restriction);
		entityDao.saveOrUpdate(holer);
		return redirect("info", "info.remove.success");
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
		RestrictionHolder<Restriction> holder = new RestrictionHelper(entityDao).getHolder();
		List<Restriction> myRestrictions = getMyRestrictions(restriction.getPattern(), holder);
		Set<RestrictField> ignoreFields = getIgnoreFields(myRestrictions);
		boolean isAdmin = isAdmin();
		for (final RestrictField field : restriction.getPattern().getEntity().getFields()) {
			String[] values = (String[]) getAll(field.getName());
			if ((ignoreFields.contains(field) || isAdmin) && getBool("ignoreField" + field.getId())) {
				restriction.setItem(field, "*");
			} else {
				if (null == values || values.length == 0) {
					restriction.getItems().remove(field.getId());
				} else {
					String storedValue = null;
					if (null != field.getKeyName()) {
						final Set<String> keys = CollectUtils.newHashSet(values);
						Collection<?> allValues = restrictionService.getFieldValues(field.getName());
						allValues = CollectionUtils.select(allValues, new Predicate() {
							public boolean evaluate(Object arg0) {
								try {
									String keyValue = String.valueOf(PropertyUtils.getProperty(arg0,
											field.getKeyName()));
									return keys.contains(keyValue);
								} catch (Exception e) {
									e.printStackTrace();
								}
								return false;
							}
						});
						storedValue = idDataResolver.marshal(field, allValues);
					} else {
						storedValue = StrUtils.join(values);
					}
					restriction.setItem(field, storedValue);
				}
			}
		}
		if (restriction.getItems().isEmpty()) {
			holder.getRestrictions().remove(restriction);
			if (restriction.isPersisted()) entityDao.remove(restriction);
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
		boolean isAdmin = isAdmin();
		Map<String, Object> mngFields = CollectUtils.newHashMap();
		Map<String, Object> aoFields = CollectUtils.newHashMap();
		List<Restriction> myRestricitons = getMyRestrictions(restriction.getPattern(),
				restriction.getHolder());
		Set<RestrictField> ignores = getIgnoreFields(myRestricitons);
		put("ignoreFields", ignores);
		Set<RestrictField> holderIgnoreFields = CollectUtils.newHashSet();
		put("holderIgnoreFields", holderIgnoreFields);
		for (RestrictField field : restriction.getPattern().getEntity().getFields()) {
			List<?> mngField = restrictionService.getFieldValues(field.getName());
			if (!isAdmin) {
				mngField.retainAll(getMyRestrictionValues(myRestricitons, field.getName()));
			} else {
				ignores.add(field);
			}
			String fieldValue = restriction.getItem(field);
			if ("*".equals(fieldValue)) {
				holderIgnoreFields.add(field);
			}
			mngFields.put(field.getName(), mngField);
			if (null == field.getSource()) {
				aoFields.put(field.getName(), fieldValue);
			} else {
				aoFields.put(field.getName(), restrictionService.getFieldValue(field, restriction));
			}
		}
		put("mngFields", mngFields);
		put("aoFields", aoFields);
		put("restriction", restriction);
		return forward();
	}

	private List<Restriction> getMyRestrictions(RestrictPattern pattern,
			RestrictionHolder<? extends Restriction> holder) {
		String type = get("restrictionType");
		List<Restriction> restrictions = CollectUtils.newArrayList();
		User me = entityDao.get(User.class, getUserId());
		if (type.equals("user")) {
			restrictions = CollectUtils.newArrayList(((RestrictionHolder<?>) me).getRestrictions());
		} else if (type.equals("group")) {
			for (GroupMember group : me.getGroups()) {
				restrictions.addAll(group.getGroup().getRestrictions());
			}
		} else if (type.equals("authority")) {
			Object aa = holder;
			restrictions = restrictionService.getAuthorityRestrictions(me, ((Authority) aa).getResource());
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

	private Set<RestrictField> getIgnoreFields(List<Restriction> restrictions) {
		Set<RestrictField> ignores = CollectUtils.newHashSet();
		for (Restriction restriction : restrictions) {
			for (RestrictField field : restriction.getPattern().getEntity().getFields()) {
				String value = restriction.getItem(field);
				if ("*".equals(value)) ignores.add(field);
			}
		}
		return ignores;
	}

	private List<Object> getMyRestrictionValues(List<Restriction> restrictions, String name) {
		List<Object> values = CollectUtils.newArrayList();
		for (Restriction restriction : restrictions) {
			RestrictField field = restriction.getPattern().getEntity().getField(name);
			if (null != field) {
				String value = restriction.getItem(field);
				if (null != value) {
					if (field.isMultiple()) {
						values.addAll((Collection<?>) restrictionService.getFieldValue(field, restriction));
					} else {
						values.add(restrictionService.getFieldValue(field, restriction));
					}
				}
			}
		}
		return values;
	}

	private Restriction getRestriction() {
		Long restrictionId = getLong("restriction.id");
		Restriction restriction = null;
		String entityName = (String) RestrictionHelper.restrictionTypeMap.get(get("restrictionType"));
		if (null == restrictionId) {
			restriction = (Restriction) Model.getEntityType(entityName).newInstance();
		} else {
			restriction = (Restriction) entityDao.get(entityName, restrictionId);
		}
		populate(Params.sub("restriction"), restriction, entityName);
		if (null == restrictionId) {
			restriction.setPattern((RestrictPattern) entityDao.get(RestrictPattern.class, restriction
					.getPattern().getId()));
		}
		return restriction;
	}

	public void setIdDataResolver(DataResolver idDataResolver) {
		this.idDataResolver = idDataResolver;
	}

}
