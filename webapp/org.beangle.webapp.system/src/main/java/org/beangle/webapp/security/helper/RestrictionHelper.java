/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.helper;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.comparators.PropertyComparator;
import org.beangle.model.persist.EntityDao;
import org.beangle.security.blueprint.Authority;
import org.beangle.security.blueprint.Group;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.restrict.RestrictField;
import org.beangle.security.blueprint.restrict.RestrictEntity;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.restrict.RestrictionHolder;
import org.beangle.security.blueprint.restrict.service.RestrictionService;
import org.beangle.struts2.helper.ContextHelper;
import org.beangle.struts2.helper.Params;

public class RestrictionHelper {

	public static final Map<String, String> restrictionTypeMap = CollectUtils.newHashMap();
	static {
		restrictionTypeMap.put("user", "org.beangle.security.blueprint.restrict.UserRestriction");
		restrictionTypeMap.put("group", "org.beangle.security.blueprint.restrict.GroupRestriction");
		restrictionTypeMap.put("authority", "org.beangle.security.blueprint.restrict.AuthorityRestriction");
	}

	EntityDao entityDao;

	RestrictionService restrictionService;

	public RestrictionHelper(EntityDao entityDao) {
		super();
		this.entityDao = entityDao;
	}

	public RestrictionHolder getHolder() {
		Long restrictionHolderId = Params.getLong("restriction.holder.id");
		String restrictionType = Params.get("restrictionType");
		RestrictionHolder holer = null;
		if ("user".equals(restrictionType)) {
			holer = (RestrictionHolder) entityDao.get(User.class, restrictionHolderId);
		} else if ("group".equals(restrictionType)) {
			holer = (RestrictionHolder) entityDao.get(Group.class, restrictionHolderId);
		} else {
			holer = (RestrictionHolder) entityDao.get(Authority.class, restrictionHolderId);
		}
		return holer;
	}

	/**
	 * 查看限制资源界面
	 */
	public void populateInfo(RestrictionHolder holder) {
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
		String forEdit = Params.get("forEdit");
		if (StringUtils.isNotEmpty(forEdit)) {
			List<RestrictEntity> restrictEntities = CollectUtils.newArrayList();
			if (holder instanceof Authority) {
				Authority au = (Authority) holder;
				restrictEntities.addAll(au.getResource().getEntities());
			} else {
				restrictEntities = entityDao.getAll(RestrictEntity.class);
			}
			ContextHelper.put("restrictEntities", restrictEntities);
		}
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
