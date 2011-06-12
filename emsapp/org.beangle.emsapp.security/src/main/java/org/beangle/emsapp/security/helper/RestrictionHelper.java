/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.helper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.comparators.PropertyComparator;
import org.beangle.ems.security.Authority;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.User;
import org.beangle.ems.security.restrict.AuthorityRestriction;
import org.beangle.ems.security.restrict.GroupRestriction;
import org.beangle.ems.security.restrict.RestrictField;
import org.beangle.ems.security.restrict.RestrictPattern;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.ems.security.restrict.RestrictionHolder;
import org.beangle.ems.security.restrict.UserRestriction;
import org.beangle.ems.security.restrict.service.RestrictionService;
import org.beangle.model.persist.EntityDao;
import org.beangle.struts2.helper.ContextHelper;
import org.beangle.struts2.helper.Params;

public class RestrictionHelper {

	public static final Map<String, String> restrictionTypeMap = CollectUtils.newHashMap();
	static {
		restrictionTypeMap.put("user", UserRestriction.class.getName());
		restrictionTypeMap.put("group", GroupRestriction.class.getName());
		restrictionTypeMap.put("authority", AuthorityRestriction.class.getName());
	}

	EntityDao entityDao;

	RestrictionService restrictionService;

	public RestrictionHelper(EntityDao entityDao) {
		super();
		this.entityDao = entityDao;
	}

	@SuppressWarnings("unchecked")
	public <T extends Restriction> RestrictionHolder<T> getHolder() {
		Long restrictionHolderId = Params.getLong("restriction.holder.id");
		String restrictionType = Params.get("restrictionType");
		RestrictionHolder<T> holer = null;
		if ("user".equals(restrictionType)) {
			holer = (RestrictionHolder<T>) entityDao.get(User.class, restrictionHolderId);
		} else if ("group".equals(restrictionType)) {
			holer = (RestrictionHolder<T>) entityDao.get(Group.class, restrictionHolderId);
		} else {
			holer = (RestrictionHolder<T>) entityDao.get(Authority.class, restrictionHolderId);
		}
		return holer;
	}

	/**
	 * 查看限制资源界面
	 */
	public void populateInfo(RestrictionHolder<?> holder) {
		List<Restriction> restrictions = CollectUtils.newArrayList(holder.getRestrictions());
		Collections.sort(restrictions, new PropertyComparator<Object>("pattern.entity.name"));
		Map<String, Map<String, Object>> fieldMaps = CollectUtils.newHashMap();
		for (final Restriction restriction : restrictions) {
			Map<String, Object> aoFields = CollectUtils.newHashMap();
			for (RestrictField field : restriction.getPattern().getEntity().getFields()) {
				String value = restriction.getItem(field);
				if (StringUtils.isNotEmpty(value)) {
					if (null == field.getSource()) {
						aoFields.put(field.getName(), value);
					} else {
						aoFields.put(field.getName(), restrictionService.getFieldValue(field, restriction));
					}
				}
			}
			fieldMaps.put(restriction.getId().toString(), aoFields);
		}
		ContextHelper.put("patterns", entityDao.getAll(RestrictPattern.class));
		ContextHelper.put("fieldMaps", fieldMaps);
		ContextHelper.put("restrictions", restrictions);
	}

	public RestrictionService getRestrictionService() {
		return restrictionService;
	}

	public void setRestrictionService(RestrictionService restrictionService) {
		this.restrictionService = restrictionService;
	}

}
