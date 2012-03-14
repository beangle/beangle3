/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.helper;

import org.beangle.dao.EntityDao;
import org.beangle.ems.security.restrict.service.RestrictionService;

public class RestrictionHelper {

	EntityDao entityDao;

	RestrictionService restrictionService;

	public RestrictionHelper(EntityDao entityDao) {
		super();
		this.entityDao = entityDao;
	}

//	@SuppressWarnings("unchecked")
//	public <T extends Restriction> RestrictionHolder getHolder() {
//		Long restrictionHolderId = Params.getLong("restriction.holder.id");
//		String restrictionType = Params.get("restrictionType");
//		RestrictionHolder holer = null;
//		if ("user".equals(restrictionType)) {
//			holer = (RestrictionHolder) entityDao.get(User.class, restrictionHolderId);
//		} else if ("group".equals(restrictionType)) {
//			holer = (RestrictionHolder) entityDao.get(Group.class, restrictionHolderId);
//		} else {
//			holer = (RestrictionHolder) entityDao.get(Authority.class, restrictionHolderId);
//		}
//		return holer;
//	}
//
//	/**
//	 * 查看限制资源界面
//	 */
//	public void populateInfo(RestrictionHolder holder) {
//		List<Restriction> restrictions = CollectUtils.newArrayList(holder.getRestrictions());
//		Collections.sort(restrictions, new PropertyComparator<Object>("pattern.entity.name"));
//		Map<String, Map<String, Object>> fieldMaps = CollectUtils.newHashMap();
//		for (final Restriction restriction : restrictions) {
//			Map<String, Object> aoFields = CollectUtils.newHashMap();
////			for (RestrictField field : restriction.getEntity().getFields()) {
////				String value = restriction.getItem(field);
////				if (StringUtils.isNotEmpty(value)) {
////					if (null == field.getSource()) {
////						aoFields.put(field.getName(), value);
////					} else {
////						aoFields.put(field.getName(), restrictionService.getFieldValue(field, restriction));
////					}
////				}
////			}
//			fieldMaps.put(restriction.getId().toString(), aoFields);
//		}
//		ContextHelper.put("fieldMaps", fieldMaps);
//		ContextHelper.put("restrictions", restrictions);
//	}

	public RestrictionService getRestrictionService() {
		return restrictionService;
	}

	public void setRestrictionService(RestrictionService restrictionService) {
		this.restrictionService = restrictionService;
	}

}
