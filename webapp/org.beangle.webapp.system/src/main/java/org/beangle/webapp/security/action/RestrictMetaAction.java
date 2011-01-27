/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.StrUtils;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.blueprint.restrict.RestrictField;
import org.beangle.security.blueprint.restrict.RestrictObject;
import org.beangle.security.blueprint.restrict.RestrictPattern;

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
		put("objects", entityDao.getAll(RestrictObject.class));
		return forward();
	}

	private OqlBuilder<RestrictField> getFieldQueryBuilder() {
		OqlBuilder<RestrictField> query = OqlBuilder.from(RestrictField.class, "field");
		populateConditions(query);
		query.orderBy(get("orderBy")).limit(getPageLimit());
		Long objectId = getLong("object.id");
		if (null != objectId) {
			query.join("field.objects", "object");
			query.where("object.id=:object", objectId);
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
		put("objects", entityDao.getAll(RestrictObject.class));
		return forward("patternForm");
	}

	public String savePattern() {
		RestrictPattern pattern = populateEntity(RestrictPattern.class, "pattern");
		entityDao.saveOrUpdate(pattern);
		return redirect("patterns", "info.save.success");
	}

	public String saveObject() {
		RestrictObject group = (RestrictObject) populateEntity(RestrictObject.class, "object");
		entityDao.saveOrUpdate(group);
		logger.info("save restrict object with name {}", group.getName());
		return redirect("index", "info.save.success");
	}

	public String removeObject() {
		Long groupId = getLong("objectId");
		if (null != groupId) {
			RestrictObject group = (RestrictObject) entityDao.get(RestrictObject.class, groupId);
			entityDao.remove(group);
			logger.info("remove group with name {}", group.getName());
		}
		return redirect("index", "info.remove.success");
	}

	public String editField() {
		RestrictField field = getEntity(RestrictField.class, "field");
		List<RestrictObject> objects = entityDao.getAll(RestrictObject.class);
		objects.removeAll(field.getObjects());
		put("objects", objects);
		put("field", field);
		return forward("fieldForm");
	}

	public String saveField() {
		String objectIds = get("objectIds");
		List<RestrictObject> paramGroups = CollectUtils.newArrayList();
		if (StringUtils.isNotBlank(objectIds)) {
			paramGroups = entityDao.get(RestrictObject.class, StrUtils.splitToLong(objectIds));
		}
		RestrictField field = populateEntity(RestrictField.class, "field");
		field.getObjects().clear();
		field.getObjects().addAll(paramGroups);
		saveOrUpdate(field);
		return redirect("fields", "info.save.success");
	}

}
