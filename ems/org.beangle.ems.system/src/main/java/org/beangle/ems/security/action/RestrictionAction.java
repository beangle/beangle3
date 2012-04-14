/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.action;

import java.util.List;

import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.ems.security.profile.PropertyMeta;
import org.beangle.ems.security.restrict.RestrictEntity;
import org.beangle.ems.security.restrict.model.RestrictionBean;
import org.beangle.struts2.action.EntityActionSupport;
import org.beangle.struts2.convention.route.Action;

/**
 * 数据限制元信息配置类
 * 
 * @author chaostone
 * @version $Id: RestrictionAction.java Apr 13, 2012 10:01:36 PM chaostone $
 */
public class RestrictionAction extends EntityActionSupport {

	public String fields() {
		OqlBuilder<PropertyMeta> query = OqlBuilder.from(PropertyMeta.class, "propertyMeta");
		populateConditions(query);
		query.orderBy(get("orderBy")).limit(getPageLimit());
		put("propertyMetas", search(query));
		return forward();
	}

	public String removeField() {
		Long fieldId = getId("propertyMeta");
		if (null != fieldId) {
			PropertyMeta field = entityDao.get(PropertyMeta.class, fieldId);
			try {
				entityDao.remove(field);
			} catch (Exception e) {
				return redirect("entityInfo", "info.remove.failure");
			}
			logger.info("remove field with name {}", field.getName());
		}
		return redirect("fields", "info.remove.success");
	}

	public String editField() {
		put("propertyMeta", getEntity(PropertyMeta.class, "propertyMeta"));
		return forward("fieldForm");
	}

	public String saveField() {
		PropertyMeta field = populateEntity(PropertyMeta.class, "propertyMeta");
		if (entityDao.duplicate(PropertyMeta.class, field.getId(), "name", field.getName())) {
			addFlashErrorNow("名称重复");
			return forward(new Action(this, "editField"));
		}
		entityDao.saveOrUpdate(field);
		return redirect("fields", "info.save.success");
	}

	public String entities() {
		List<RestrictEntity> entities = entityDao.getAll(RestrictEntity.class);
		put("entities", entities);
		return forward();
	}

	public String saveEntity() {
		RestrictEntity entity = (RestrictEntity) populateEntity(RestrictEntity.class, "entity");
		if (null != entity.getName()) {
			if (entityDao.duplicate(RestrictEntity.class, entity.getId(), "name", entity.getName())) {
				addFlashErrorNow("名称重复");
				return forward(new Action(this, "editEntity"));
			}
			entityDao.saveOrUpdate(entity);
			logger.info("save restrict entity with name {}", entity.getName());
		}
		return redirect("entities", "info.save.success");
	}

	public String removeEntity() {
		Long entityId = getId("entity");
		if (null != entityId) {
			RestrictEntity entity = entityDao.get(RestrictEntity.class, entityId);
			try {
				entityDao.remove(entity);
			} catch (Exception e) {
				return redirect("entities", "info.remove.failure");
			}
			logger.info("remove entity with name {}", entity.getName());
		}
		return redirect("entities", "info.remove.success");
	}

	public String editEntity() {
		RestrictEntity entity = getEntity(RestrictEntity.class, "entity");
		put("entity", entity);
		return forward("entityForm");
	}

	// public String entityInfo() {
	// RestrictEntity entity = getEntity(RestrictEntity.class, "entity");
	// put("entity", entity);
	// Map<RestrictEntity, List<RestrictField>> fieldMap = CollectUtils.newHashMap();
	// Map<RestrictEntity, List<RestrictPattern>> patternMap = CollectUtils.newHashMap();
	// OqlBuilder<RestrictField> query = OqlBuilder.from(RestrictField.class, "field");
	// query.join("field.entities", "entity");
	// query.where("entity.id=:entityId", entity.getId());
	// fieldMap.put(entity, entityDao.search(query));
	// patternMap.put(entity, entityDao.get(RestrictPattern.class, "entity", entity));
	// put("fieldMap", fieldMap);
	// put("patternMap", patternMap);
	// return forward();
	// }
	//

	public String editRestriction() {
		RestrictionBean pattern = getEntity(RestrictionBean.class, "restriction");
		Long entityId = getLong("restriction.entity.id");
		if (null == entityId) {
			entityId = getLong("entity.id");
		}
		pattern.setEntity(entityDao.get(RestrictEntity.class, entityId));
		put("restriction", pattern);
		return forward("restrictionForm");
	}

	public String saveRestriction() {
		RestrictionBean pattern = populateEntity(RestrictionBean.class, "pattern");
		if (entityDao.duplicate(RestrictionBean.class, pattern.getId(), "remark", pattern.getRemark())) {
			addFlashErrorNow("限制模式描述重复");
			return forward(new Action(this, "editPattern"));
		} else {
			entityDao.saveOrUpdate(pattern);
			return redirect("entityInfo", "info.save.success");
		}
	}

	public String removePattern() {
		Long[] patternIds = getIds("restriction");
		if (null != patternIds) {
			try {
				entityDao.remove(entityDao.get(RestrictionBean.class, patternIds));
			} catch (Exception e) {
				return redirect("entityInfo", "info.remove.failure");
			}
		}
		return redirect("entityInfo", "info.remove.success");
	}
}
