/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.service;

import java.lang.management.ManagementFactory;
import java.util.List;

import org.beangle.ems.security.session.CategoryProfile;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.web.session.category.AbstractCategorySessionController;
import org.beangle.security.web.session.category.CategorySessionController;
import org.beangle.security.web.session.category.CategorySessionControllerFactory;
import org.springframework.beans.factory.InitializingBean;

/**
 * 基于配置的分类用户控制器
 * 
 * @author chaostone
 * @version $Id: DbCategorySessionControllerFactory.java Jun 19, 2011 8:03:54 AM chaostone $
 */
public class CategoryProfileSessionControllerFactory extends BaseServiceImpl implements
		CategorySessionControllerFactory {

	public CategorySessionController getInstance(Object category) {
		ProfileCategorySessionController controller = new ProfileCategorySessionController(ManagementFactory
				.getRuntimeMXBean().getName(), String.valueOf(category));
		controller.setEntityDao(entityDao);
		controller.afterPropertiesSet();
		return controller;
	}
}

class ProfileCategorySessionController extends AbstractCategorySessionController implements InitializingBean {

	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		List<CategoryProfile> profiles = entityDao.get(CategoryProfile.class, "category.name",
				sessionStatus.getCategory());
		if (!profiles.isEmpty()) {
			sessionStatus.setUserMaxSessions(profiles.get(0).getUserMaxSessions());
		}
	}

	@Override
	protected int getMaxCapacity() {
		OqlBuilder<?> spbuilder = OqlBuilder.from(CategoryProfile.class, "sp");
		spbuilder.where("sp.category.name=:category", sessionStatus.getCategory());
		spbuilder.select("sp.capacity");
		List<?> sps = entityDao.search(spbuilder);
		int capacity = ((Number) sps.get(0)).intValue();
		return capacity;
	}

	public ProfileCategorySessionController(String serverName, String category) {
		super(serverName, category);
	}
}
