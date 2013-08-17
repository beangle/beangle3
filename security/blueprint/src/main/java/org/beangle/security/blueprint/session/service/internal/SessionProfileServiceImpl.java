/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.security.blueprint.session.service.internal;

import java.util.List;

import org.beangle.commons.dao.impl.BaseServiceImpl;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Role;
import org.beangle.security.blueprint.session.model.SessionProfileBean;
import org.beangle.security.blueprint.session.service.SessionProfileService;
import org.beangle.security.core.session.category.CategoryProfile;
import org.beangle.security.core.session.category.CategoryProfileUpdateEvent;

/**
 * @author chaostone
 * @version $Id: CategoryProfileProviderImpl.java Jul 11, 2011 10:50:36 AM chaostone $
 */
public class SessionProfileServiceImpl extends BaseServiceImpl implements SessionProfileService {

  public List<? extends CategoryProfile> getProfiles() {
    return entityDao.search(OqlBuilder.from(SessionProfileBean.class, "p").select(
        "new " + SessionProfileBean.class.getName()
            + "(p.id,p.role.id,p.role.indexno,p.role.name,p.capacity,p.userMaxSessions,p.inactiveInterval)"));
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
        + "(p.id,p.role.id,p.role.indexno,p.role.name,p.capacity,p.userMaxSessions,p.inactiveInterval)");
    builder.where("p.role.name=:category", category).cacheable();
    return entityDao.uniqueResult(builder);
  }

}
