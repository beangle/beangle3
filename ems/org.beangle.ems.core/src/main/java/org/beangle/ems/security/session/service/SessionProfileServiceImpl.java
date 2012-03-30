/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.session.service;

import java.util.List;

import org.beangle.collection.CollectUtils;
import org.beangle.dao.impl.BaseServiceImpl;
import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.ems.security.Role;
import org.beangle.ems.security.session.model.SessionProfileBean;
import org.beangle.security.core.session.category.CategoryProfile;
import org.beangle.security.core.session.category.CategoryProfileProvider;
import org.beangle.security.core.session.category.CategoryProfileUpdateEvent;

/**
 * @author chaostone
 * @version $Id: CategoryProfileProviderImpl.java Jul 11, 2011 10:50:36 AM chaostone $
 */
public class SessionProfileServiceImpl extends BaseServiceImpl implements CategoryProfileProvider,
		SessionProfileService {

	public List<CategoryProfile> getCategoryProfiles() {
		List<CategoryProfile> profiles = CollectUtils.newArrayList();
		OqlBuilder<?> cbuilder = OqlBuilder.from(SessionProfileBean.class, "cp");
		cbuilder.select("cp.role.name,cp.capacity,cp.userMaxSessions,cp.inactiveInterval");
		for (Object data : entityDao.search(cbuilder)) {
			Object[] datas = (Object[]) data;
			profiles.add(new CategoryProfile((String) datas[0], (Integer) datas[1], (Integer) datas[2],
					(Integer) datas[3]));
		}
		return profiles;

	}

	public boolean hasProfile(Role role) {
		OqlBuilder<?> cbuilder = OqlBuilder.from(SessionProfileBean.class, "cp");
		cbuilder.select("cp.id").where("cp.role=:role", role).cacheable();
		return !entityDao.search(cbuilder).isEmpty();
	}

	public void saveOrUpdate(List<SessionProfileBean> profiles) {
		entityDao.saveOrUpdate(profiles);
		for (SessionProfileBean profile : profiles) {
			publish(new CategoryProfileUpdateEvent(new CategoryProfile(profile.getRole().getName(),
					profile.getCapacity(), profile.getUserMaxSessions(), profile.getInactiveInterval())));
		}
	}

}
