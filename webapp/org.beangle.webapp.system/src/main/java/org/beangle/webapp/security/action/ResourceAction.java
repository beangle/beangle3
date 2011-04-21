/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.security.action;

import java.util.Collections;
import java.util.List;

import org.beangle.commons.lang.StrUtils;
import org.beangle.model.Entity;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Authority;
import org.beangle.security.blueprint.Category;
import org.beangle.security.blueprint.Menu;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.restrict.RestrictEntity;
import org.beangle.security.blueprint.service.CacheableAuthorityManager;
import org.beangle.struts2.convention.route.Action;

/**
 * 系统模块管理响应类
 * 
 * @author chaostone 2005-10-9
 */
public class ResourceAction extends SecurityActionSupport {

	private CacheableAuthorityManager authorityManager;

	/**
	 * 禁用或激活一个或多个模块
	 * 
	 * @return
	 */
	public String activate() {
		Long[] resourceIds = getEntityIds();
		Boolean enabled = getBoolean("enabled");
		if (null == enabled) {
			enabled = Boolean.FALSE;
		}
		authorityService.updateState(resourceIds, enabled.booleanValue());
		authorityManager.refreshCache();
		return redirect("search", "info.save.success");
	}

	@Override
	public String index() {
		return forward(new Action(this, "search"));
	}

	protected void editSetting(Entity<?> entity) {
		Resource resource = (Resource) entity;
		List<RestrictEntity> objects = entityDao.getAll(RestrictEntity.class);
		objects.removeAll(resource.getEntities());
		put("restrictObjects", objects);
		put("categories", entityDao.getAll(Category.class));
	}

	protected String saveAndForward(Entity<?> entity) {
		Resource resource = (Resource) entity;
		if (null != resource) {
			if (entityDao.duplicate(Resource.class, resource.getId(), "name", resource.getName())) { return redirect(
					"edit", "error.notUnique"); }
		}

		Long[] patternIds = StrUtils.splitToLong(get("restrictObjectIds"));
		List<RestrictEntity> objects = Collections.emptyList();
		if (null != patternIds) {
			objects = entityDao.get(RestrictEntity.class, patternIds);
		}
		resource.getEntities().clear();
		resource.getEntities().addAll(objects);

		String categoryIds = get("categoryIds");
		List<Category> categories = entityDao.get(Category.class, StrUtils.splitToLong(categoryIds));
		resource.getCategories().clear();
		resource.getCategories().addAll(categories);

		entityDao.saveOrUpdate(resource);
		authorityManager.refreshCache();

		logger.info("save resource success {}", resource.getTitle());
		return redirect("search", "info.save.success");
	}

	public String info() {
		Long entityId = getEntityId(getShortName());
		Entity<?> entity = getModel(getEntityName(), entityId);
		OqlBuilder<Menu> query = OqlBuilder.from(Menu.class, "menu");
		query.join("menu.resources", "r").where("r.id=:resourceId", entity.getIdentifier())
				.orderBy("menu.profile.id,menu.code");

		OqlBuilder<Authority> groupQuery = OqlBuilder.from(Authority.class, "auth");
		groupQuery.where("auth.resource=:resource", entity).select("auth.group");

		OqlBuilder<?> userQuery = OqlBuilder.hql("select distinct u from " + User.class.getName()
				+ " u join u.groups g," + Authority.class.getName()
				+ " a where g=a.group and a.resource=:resource");
		userQuery.param("resource", entity);

		put(getShortName(), entity);
		put("users", entityDao.search(userQuery));
		put("groups", entityDao.search(groupQuery));
		put("menus", entityDao.search(query));
		put("categories", entityDao.getAll(Category.class));
		return forward();
	}

	public void setAuthorityManager(CacheableAuthorityManager authorityManager) {
		this.authorityManager = authorityManager;
	}

	@Override
	protected String getEntityName() {
		return Resource.class.getName();
	}

}
