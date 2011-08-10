/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.ems.security.restrict.RestrictEntity;
import org.beangle.ems.security.restrict.RestrictField;
import org.beangle.ems.security.restrict.RestrictPattern;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.convention.route.Action;

public class RestrictMetaAction extends SecurityActionSupport {

	public String entities() {
		List<RestrictEntity> entities = entityDao.getAll(RestrictEntity.class);
		put("entities", entities);
		return forward();
	}

	public String entityInfo() {
		RestrictEntity entity = getEntity(RestrictEntity.class, "entity");
		put("entity", entity);
		Map<RestrictEntity, List<RestrictField>> fieldMap = CollectUtils.newHashMap();
		Map<RestrictEntity, List<RestrictPattern>> patternMap = CollectUtils.newHashMap();
		OqlBuilder<RestrictField> query = OqlBuilder.from(RestrictField.class, "field");
		query.join("field.entities", "entity");
		query.where("entity.id=:entityId", entity.getId());
		fieldMap.put(entity, entityDao.search(query));
		patternMap.put(entity, entityDao.get(RestrictPattern.class, "entity", entity));
		put("fieldMap", fieldMap);
		put("patternMap", patternMap);
		return forward();
	}

	public String editPattern() {
		RestrictPattern pattern = getEntity(RestrictPattern.class, "pattern");
		Long entityId = getLong("pattern.entity.id");
		if (null == entityId) {
			entityId = getLong("entity.id");
		}
		pattern.setEntity(entityDao.get(RestrictEntity.class, entityId));
		put("pattern", pattern);
		return forward("patternForm");
	}

	public String savePattern() {
		RestrictPattern pattern = populateEntity(RestrictPattern.class, "pattern");
		if (entityDao.duplicate(RestrictPattern.class, pattern.getId(), "remark", pattern.getRemark())) {
			addFlashErrorNow("限制模式描述重复");
			return forward(new Action(this, "editPattern"));
		} else {
			entityDao.saveOrUpdate(pattern);
			return redirect("entityInfo", "info.save.success");
		}
	}

	public String removePattern() {
		Long[] patternIds = this.getEntityIds("pattern");
		if (null != patternIds) {
			try {
				entityDao.remove(entityDao.get(RestrictPattern.class, patternIds));
			} catch (Exception e) {
				return redirect("entityInfo", "info.remove.failure");
			}
		}
		return redirect("entityInfo", "info.remove.success");
	}

	public String saveEntity() {
		RestrictEntity entity = (RestrictEntity) populateEntity(RestrictEntity.class, "entity");
		if (null != entity.getName()) {
			if (entityDao.duplicate(RestrictEntity.class, entity.getId(), "name", entity.getName())) {
				addFlashErrorNow("名称重复");
				return forward(new Action(this, "editEntity"));
			}
			List<RestrictField> fields = entityDao.get(RestrictField.class, getAll("fieldId", Long.class));
			entity.getFields().clear();
			entity.getFields().addAll(fields);
			entityDao.saveOrUpdate(entity);
			logger.info("save restrict entity with name {}", entity.getName());
		}
		return redirect("entities", "info.save.success");
	}

	public String removeEntity() {
		Long entityId = getEntityId("entity");
		if (null != entityId) {
			RestrictEntity entity = (RestrictEntity) entityDao.get(RestrictEntity.class, entityId);
			try {
				entityDao.remove(entity);
			} catch (Exception e) {
				return redirect("entities", "info.remove.failure");
			}
			logger.info("remove entity with name {}", entity.getName());
		}
		return redirect("entities", "info.remove.success");
	}

	public String removeField() {
		Long fieldId = getEntityId("field");
		if (null != fieldId) {
			RestrictField field = (RestrictField) entityDao.get(RestrictField.class, fieldId);
			try {
				for (RestrictEntity entity : field.getEntities()) {
					entity.getFields().remove(field);
				}
				entityDao.saveOrUpdate(field.getEntities());
				entityDao.remove(field);
			} catch (Exception e) {
				return redirect("entityInfo", "info.remove.failure");
			}
			logger.info("remove field with name {}", field.getName());
		}
		return redirect("fields", "info.remove.success");
	}

	public String editField() {
		RestrictField field = getEntity(RestrictField.class, "field");
		List<RestrictEntity> entities = entityDao.getAll(RestrictEntity.class);
		entities.removeAll(field.getEntities());
		put("entities", entities);
		put("field", field);
		return forward("fieldForm");
	}

	public String editEntity() {
		RestrictEntity entity = getEntity(RestrictEntity.class, "entity");
		put("entity", entity);
		put("fields", entityDao.getAll(RestrictField.class));
		return forward("entityForm");
	}

	public String saveField() {
		List<RestrictEntity> entities = entityDao.get(RestrictEntity.class, getAll("entityId", Long.class));
		// FIXME Too complex
		RestrictField field = populateEntity(RestrictField.class, "field");
		if (entityDao.duplicate(RestrictField.class, field.getId(), "name", field.getName())) {
			addFlashErrorNow("名称重复");
			return forward(new Action(this, "editField"));
		}
		for (RestrictEntity entity : field.getEntities()) {
			entity.getFields().remove(field);
		}
		for (RestrictEntity entity : entities) {
			entity.getFields().add(field);
		}
		field.getEntities().clear();
		field.getEntities().addAll(entities);
		entityDao.saveOrUpdate(field, entities);
		return redirect("fields", "info.save.success");
	}

	public String fields() {
		OqlBuilder<RestrictField> query = OqlBuilder.from(RestrictField.class, "field");
		populateConditions(query);
		query.orderBy(get("orderBy")).limit(getPageLimit());
		Long entityId = getLong("entity.id");
		if (null != entityId) {
			query.join("field.entities", "entity");
			query.where("entity.id=:entityId", entityId);
		}
		put("fields", search(query));
		return forward();
	}

}
