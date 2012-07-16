/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

import java.util.List;

import org.beangle.commons.bean.Initializing;
import org.beangle.commons.context.event.Event;
import org.beangle.commons.context.event.EventListener;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.lang.Assert;
import org.beangle.security.auth.Principals;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.AbstractSessionController;
import org.beangle.security.core.session.Sessioninfo;
import org.beangle.security.core.session.SessioninfoBuilder;

/**
 * 基于数据库的集中session控制器
 * 
 * @author chaostone
 * @version $Id: DbCategorySessionController.java Jul 8, 2011 9:08:14 AM chaostone $
 */
public class DbCategorySessionController extends AbstractSessionController implements Initializing,
    EventListener<CategoryProfileUpdateEvent> {

  private CategoryProfileProvider categoryProfileProvider = new SimpleCategoryProfileProvider();

  private SessioninfoBuilder sessioninfoBuilder;

  @Override
  protected boolean allocate(Authentication auth, String sessionId) {
    CategoryPrincipal principal = (CategoryPrincipal) auth.getPrincipal();
    if (Principals.ROOT.equals(principal.getId())) {
      return true;
    } else {
      String category = principal.getCategory();
      int result = entityDao.executeUpdateHql("update " + SessionStat.class.getName()
          + " stat set stat.online = stat.online + 1 "
          + "where stat.online < stat.capacity and stat.category=?", category);
      return result > 0;
    }
  }

  @SuppressWarnings({ "rawtypes", "unchecked" })
  public int getMaxSessions(Authentication auth) {
    CategoryPrincipal principal = (CategoryPrincipal) auth.getPrincipal();
    if (Principals.ROOT.equals(principal.getId())) {
      return -1;
    } else {
      String category = principal.getCategory();
      OqlBuilder builder = OqlBuilder.from(SessionStat.class, "stat");
      builder.select("stat.userMaxSessions").where("stat.category=:category", category).cacheable();
      List nums = entityDao.search(builder);
      if (nums.isEmpty()) return 1;
      else return ((Number) nums.get(0)).intValue();
    }
  }

  public void onLogout(Sessioninfo info) {
    CategorySessioninfo categoryinfo = (CategorySessioninfo) info;
    if (!info.isExpired()) {
      entityDao.executeUpdateHql("update " + SessionStat.class.getName()
          + " stat set stat.online=stat.online -1 " + "where stat.online>0 and stat.category=?",
          categoryinfo.getCategory());
    }
  }

  public void init() throws Exception {
    Assert.notNull(categoryProfileProvider);
    for (CategoryProfile profile : categoryProfileProvider.getCategoryProfiles()) {
      OqlBuilder<SessionStat> builder = OqlBuilder.from(SessionStat.class, "stat");
      builder.where("stat.category=:category", profile.getCategory());
      SessionStat stat = entityDao.uniqueResult(builder);
      if (null == stat) {
        stat = new SessionStat(profile.getCategory(), profile.getCapacity(), profile.getInactiveInterval());
        entityDao.saveOrUpdate(stat);
      }
    }
  }

  public void onEvent(CategoryProfileUpdateEvent event) {
    CategoryProfile profile = (CategoryProfile) event.getSource();
    int cnt = entityDao.executeUpdateHql("update " + SessionStat.class.getName()
        + " stat  set stat.capacity=?,stat.userMaxSessions=?,stat.inactiveInterval=?"
        + " where stat.category=?", profile.getCapacity(), profile.getUserMaxSessions(),
        profile.getInactiveInterval(), profile.getCategory());
    if (cnt == 0) {
      SessionStat stat = new SessionStat(profile.getCategory(), profile.getCapacity(),
          profile.getInactiveInterval());
      entityDao.saveOrUpdate(stat);
    }
  }

  public void stat() {
    entityDao.executeUpdateHql("update " + SessionStat.class.getName()
        + " stat  set stat.online=(select count(*) from "
        + sessioninfoBuilder.getSessioninfoClass().getName()
        + " info where info.expiredAt is null and info.category=stat.category)");
  }

  public CategoryProfileProvider getCategoryProfileProvider() {
    return categoryProfileProvider;
  }

  public void setCategoryProfileProvider(CategoryProfileProvider categoryProfileProvider) {
    this.categoryProfileProvider = categoryProfileProvider;
  }

  public void setSessioninfoBuilder(SessioninfoBuilder sessioninfoBuilder) {
    this.sessioninfoBuilder = sessioninfoBuilder;
  }

  public boolean supportsEventType(Class<? extends Event> eventType) {
    return eventType.equals(CategoryProfileUpdateEvent.class);
  }

  public boolean supportsSourceType(Class<?> sourceType) {
    return true;
  }

}
