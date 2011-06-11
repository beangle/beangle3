/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.ems.security.restrict.RestrictEntity;
import org.beangle.ems.security.restrict.RestrictField;
import org.beangle.ems.security.restrict.RestrictPattern;
import org.beangle.model.query.builder.OqlBuilder;

public class RestrictMetaAction extends SecurityActionSupport {

	public String patterns() {
		OqlBuilder<RestrictPattern> query = OqlBuilder.from(RestrictPattern.class, "pattern");
		populateConditions(query);
		query.orderBy(get("orderBy")).limit(getPageLimit());
		put("patterns", search(query));
		return forward();
	}

	public String fields() {
		put("fields", search(getFieldQueryBuilder()));
		put("entities", entityDao.getAll(RestrictEntity.class));
		return forward();
	}

	private OqlBuilder<RestrictField> getFieldQueryBuilder() {
		OqlBuilder<RestrictField> query = OqlBuilder.from(RestrictField.class, "field");
		populateConditions(query);
		query.orderBy(get("orderBy")).limit(getPageLimit());
		Long entityId = getLong("entity.id");
		if (null != entityId) {
			query.join("field.entities", "entity");
			query.where("entity.id=:entityId", entityId);
		}
		return query;
	}

	public String patternInfo() {
		put("pattern", getEntity(RestrictPattern.class, "pattern"));
		return forward();
	}

	public String editPattern() {
		RestrictPattern pattern = getEntity(RestrictPattern.class, "pattern");
		put("pattern", pattern);
		put("entities", entityDao.getAll(RestrictEntity.class));
		return forward("patternForm");
	}

	public String savePattern() {
		RestrictPattern pattern = populateEntity(RestrictPattern.class, "pattern");
		entityDao.saveOrUpdate(pattern);
		return redirect("patterns", "info.save.success");
	}

	public String saveEntity() {
		RestrictEntity entity = (RestrictEntity) populateEntity(RestrictEntity.class, "entity");
		if (null != entity.getName()) {
			entityDao.saveOrUpdate(entity);
			logger.info("save restrict entity with name {}", entity.getName());
		}
		return redirect("fields", "info.save.success");
	}

	public String removeEntity() {
		Long entityId = getEntityId("entity");
		if (null != entityId) {
			RestrictEntity entity = (RestrictEntity) entityDao.get(RestrictEntity.class, entityId);
			try {
				entityDao.remove(entity);
			} catch (Exception e) {
				return redirect("fields", "info.remove.failure");
			}
			logger.info("remove entity with name {}", entity.getName());
		}
		return redirect("fields", "info.remove.success");
	}

	public String removeField() {
		Long fieldId = getEntityId("field");
		if (null != fieldId) {
			RestrictField field = (RestrictField) entityDao.get(RestrictField.class, fieldId);
			try {
				for(RestrictEntity entity:field.getEntities()){
					entity.getFields().remove(field);
				}
				entityDao.saveOrUpdate(field.getEntities());
				entityDao.remove(field);
			} catch (Exception e) {
				return redirect("fields", "info.remove.failure");
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

	public String saveField() {
		String entityIds = get("entityIds");
		List<RestrictEntity> entities = CollectUtils.newArrayList();
		if (StringUtils.isNotBlank(entityIds)) {
			entities = entityDao.get(RestrictEntity.class, StrUtils.splitToLong(entityIds));
		}
		//FIXME Too complex
		RestrictField field = populateEntity(RestrictField.class, "field");
		for(RestrictEntity entity:field.getEntities()){
			entity.getFields().remove(field);
		}
		for(RestrictEntity entity:entities){
			entity.getFields().add(field);
		}
		field.getEntities().clear();
		field.getEntities().addAll(entities);
		entityDao.saveOrUpdate(field,entities);
		return redirect("fields", "info.save.success");
	}

}
