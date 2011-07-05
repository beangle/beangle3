/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.util.List;

import org.beangle.ems.security.Category;
import org.beangle.ems.security.session.CategoryProfile;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.core.session.category.CategorySessionController;
import org.beangle.security.core.session.category.ClusterCategorySessionController;
import org.beangle.security.core.session.category.ClusterCategorySessionControllerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 基于配置的分类用户控制器
 * 
 * @author chaostone
 * @version $Id: DbCategorySessionControllerFactory.java Jun 19, 2011 8:03:54 AM chaostone $
 */
public class CategoryProfileSessionControllerFactory extends ClusterCategorySessionControllerFactory {

	public CategorySessionController doGetInstance(Object category) {
		ProfileCategorySessionController controller = new ProfileCategorySessionController(getServerName(),
				((Category) category).getTitle());
		controller.setEntityDao(entityDao);
		controller.afterPropertiesSet();
		return controller;
	}

}

class ProfileCategorySessionController extends ClusterCategorySessionController implements InitializingBean {

	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		List<CategoryProfile> profiles = entityDao.get(CategoryProfile.class, "category.title",
				sessionStatus.getCategory());
		if (!profiles.isEmpty()) {
			sessionStatus.setUserMaxSessions(profiles.get(0).getUserMaxSessions());
		}
	}

	@Override
	protected int getMaxCapacity() {
		OqlBuilder<?> spbuilder = OqlBuilder.from(CategoryProfile.class, "sp");
		spbuilder.where("sp.category.title=:category", sessionStatus.getCategory());
		spbuilder.select("sp.capacity");
		List<?> sps = entityDao.search(spbuilder);
		return ((Number) sps.get(0)).intValue();
	}

	public ProfileCategorySessionController(String serverName, String category) {
		super(serverName, category);
	}
}
