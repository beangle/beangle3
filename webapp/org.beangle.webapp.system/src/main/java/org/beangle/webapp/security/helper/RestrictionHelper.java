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
import org.beangle.security.blueprint.restrict.RestrictObject;
import org.beangle.security.blueprint.restrict.Restriction;
import org.beangle.security.blueprint.restrict.RestrictionHolder;
import org.beangle.security.blueprint.restrict.service.RestrictionService;
import org.beangle.struts2.helper.ContextHelper;
import org.beangle.struts2.helper.Params;

public class RestrictionHelper {

	public static final Map<String, String> restrictionTypeMap = CollectUtils.newHashMap();
	static {
		restrictionTypeMap.put("user", "org.beangle.security.blueprint.UserRestriction");
		restrictionTypeMap.put("group", "org.beangle.security.blueprint.GroupRestriction");
		restrictionTypeMap.put("authority", "org.beangle.security.blueprint.AuthorityRestriction");
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
		Collections.sort(restrictions, new PropertyComparator<Object>("paramGroup.name"));
		Map<String, Map<String, Object>> paramMaps = CollectUtils.newHashMap();
		for (final Restriction restriction : restrictions) {
			Map<String, Object> aoParams = CollectUtils.newHashMap();
			for (RestrictField param : restriction.getPattern().getObject().getFields()) {
				String value = restriction.getItem(param);
				if (StringUtils.isNotEmpty(value)) {
					if (null == param.getSource()) {
						aoParams.put(param.getName(), value);
					} else {
						aoParams.put(param.getName(), restrictionService.select(
								restrictionService.getValues(param), restriction, param));
					}
				}
			}
			paramMaps.put(restriction.getId().toString(), aoParams);
		}
		String forEdit = Params.get("forEdit");
		if (StringUtils.isNotEmpty(forEdit)) {
			List<RestrictObject> restrictObjects = CollectUtils.newArrayList();
			if (holder instanceof Authority) {
				Authority au = (Authority) holder;
				restrictObjects.addAll(au.getResource().getObjects());
			} else {
				restrictObjects = entityDao.getAll(RestrictObject.class);
			}
			ContextHelper.put("restrictObjects", restrictObjects);
		}
		ContextHelper.put("paramMaps", paramMaps);
		ContextHelper.put("restrictions", restrictions);
	}

	public RestrictionService getRestrictionService() {
		return restrictionService;
	}

	public void setRestrictionService(RestrictionService restrictionService) {
		this.restrictionService = restrictionService;
	}

}
