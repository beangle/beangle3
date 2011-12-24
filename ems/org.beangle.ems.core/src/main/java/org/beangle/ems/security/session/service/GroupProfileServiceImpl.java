/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.session.service;

import java.util.List;

import org.beangle.collection.CollectUtils;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.session.model.GroupSessionProfileBean;
import org.beangle.ems.security.session.model.SessionProfileBean;
import org.beangle.model.persist.impl.BaseServiceImpl;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.core.session.category.CategoryProfile;
import org.beangle.security.core.session.category.CategoryProfileProvider;
import org.beangle.security.core.session.category.CategoryProfileUpdateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author chaostone
 * @version $Id: CategoryProfileProviderImpl.java Jul 11, 2011 10:50:36 AM chaostone $
 */
public class GroupProfileServiceImpl extends BaseServiceImpl implements CategoryProfileProvider,
		GroupProfileService {

	private static final Logger log = LoggerFactory.getLogger(GroupProfileServiceImpl.class);
	private final static String DefauleServerName = "default_server";
	private String serverName;

	public List<CategoryProfile> getCategoryProfiles() {
		List<CategoryProfile> profiles = CollectUtils.newArrayList();
		OqlBuilder<?> cbuilder = OqlBuilder.from(GroupSessionProfileBean.class, "cp");
		cbuilder.where("cp.sessionProfile=:profile", getProfile());
		cbuilder.select("cp.group.name,cp.capacity,cp.userMaxSessions,cp.inactiveInterval");
		for (Object data : entityDao.search(cbuilder)) {
			Object[] datas = (Object[]) data;
			profiles.add(new CategoryProfile((String) datas[0], (Integer) datas[1], (Integer) datas[2],
					(Integer) datas[3]));
		}
		return profiles;

	}

	public boolean hasProfile(Group group) {
		OqlBuilder<?> cbuilder = OqlBuilder.from(GroupSessionProfileBean.class, "cp");
		cbuilder.where("cp.sessionProfile.name=:serverName", getServerName()).select("cp.id")
				.where("cp.group=:group", group).cacheable();
		return !entityDao.search(cbuilder).isEmpty();
	}

	private SessionProfileBean getProfile() {
		SessionProfileBean profile = null;
		final OqlBuilder<SessionProfileBean> builder = OqlBuilder.from(SessionProfileBean.class, "p");
		if (null != serverName) {
			builder.where("p.name=:serverName", serverName);
		}
		List<SessionProfileBean> profiles = entityDao.search(builder);
		if (profiles.isEmpty()) {
			profile = new SessionProfileBean(DefauleServerName, 1, 30);
			entityDao.saveOrUpdate(profile);
		} else {
			profile = profiles.get(0);
			if (profiles.size() > 1) {
				log.warn("Multiple SessionProfile found,choose {} id {}.You should specify :serverName",
						profile.getName(), profile.getId());
			}
		}
		return profile;
	}

	public String getServerName() {
		if (null == serverName) {
			serverName = getProfile().getName();
		}
		return serverName;
	}

	public void saveOrUpdate(List<GroupSessionProfileBean> profiles) {
		entityDao.saveOrUpdate(profiles);
		for (GroupSessionProfileBean profile : profiles) {
			publish(new CategoryProfileUpdateEvent(new CategoryProfile(profile.getGroup().getName(),
					profile.getCapacity(), profile.getUserMaxSessions(), profile.getInactiveInterval())));
		}
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

}
