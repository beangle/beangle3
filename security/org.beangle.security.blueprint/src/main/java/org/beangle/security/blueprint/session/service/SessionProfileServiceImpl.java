/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.session.service;

import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.session.model.SessionProfileBean;
import org.beangle.security.core.session.category.CategoryProfile;
import org.beangle.security.core.session.category.CategoryProfileProvider;
import org.beangle.security.core.session.category.CategoryProfileUpdateEvent;

/**
 * @author chaostone
 * @version $Id: CategoryProfileProviderImpl.java Jul 11, 2011 10:50:36 AM chaostone $
 */
public class SessionProfileServiceImpl extends BaseServiceImpl implements CategoryProfileProvider,
    SessionProfileService {

  public List<? extends CategoryProfile> getProfiles() {
    return entityDao.search(OqlBuilder.from(SessionProfileBean.class, "p").select(
        "new " + SessionProfileBean.class.getName()
            + "(p.id,p.role.name,p.capacity,p.userMaxSessions,p.inactiveInterval)"));
  }

  public boolean hasProfile(Role role) {
    OqlBuilder<?> cbuilder = OqlBuilder.from(SessionProfileBean.class, "cp");
    cbuilder.select("cp.id").where("cp.role=:role", role).cacheable();
    return !entityDao.search(cbuilder).isEmpty();
  }

  public void saveOrUpdate(List<SessionProfileBean> profiles) {
    entityDao.saveOrUpdate(profiles);
    for (SessionProfileBean profile : profiles)
      publish(new CategoryProfileUpdateEvent(profile));
  }

  public CategoryProfile getProfile(String category) {
    OqlBuilder<SessionProfileBean> builder = OqlBuilder.from(SessionProfileBean.class, "p");
    builder.select("new " + SessionProfileBean.class.getName()
        + "(p.id,p.role.name,p.capacity,p.userMaxSessions,p.inactiveInterval)");
    builder.where("p.role.name=:category", category).cacheable();
    return entityDao.uniqueResult(builder);
  }

}
