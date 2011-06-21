/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session.category;

import java.util.List;

import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.springframework.beans.factory.InitializingBean;

/**
 * 抽象分类会话控制器
 * 
 * @author chaostone
 * @version $Id: AbstractCategorySessionController.java Jun 18, 2011 10:31:12 PM chaostone $
 */
public abstract class ClusterCategorySessionController implements CategorySessionController, InitializingBean {

	protected EntityDao entityDao;

	protected CategorySessionStat sessionStatus;

	public CategorySessionStat getSessionStat() {
		return sessionStatus;
	}

	public ClusterCategorySessionController(String serverName, String category) {
		super();
		sessionStatus = new CategorySessionStat(serverName, category, 0, 1);
	}

	public synchronized boolean allocate(String sessionId) {
		if (!sessionStatus.allocate(sessionId)) {
			int newAllocated = allocate(100);
			sessionStatus.adjust(newAllocated);
			return sessionStatus.allocate(sessionId);
		} else {
			return true;
		}
	}

	public void afterPropertiesSet() {
		entityDao.saveOrUpdate(sessionStatus);
	}

	public synchronized void free(String sessionId) {
		sessionStatus.free(sessionId);
		// FIXME gc
	}

	protected int allocate(int required) {
		OqlBuilder<?> builder = OqlBuilder.from(CategorySessionStat.class, "status");
		builder.where("status.category=:category", sessionStatus.getCategory())
				.select("sum(status.capacity)");
		List<?> rs = entityDao.search(builder);
		int allocated = ((Number) rs.get(0)).intValue();
		int maxCapacity = getMaxCapacity();
		int quota = (required <= maxCapacity - allocated) ? required : maxCapacity - allocated;
		return quota;
		// String
		// sql="update SessionStatus status set status.capacity=status.capacity + :quota where st.serverName=:serverName and st.category";
		//
		// String
		// updateSql="update SessionStatus status set status.capacity=status.capacity + 100  where status.serverName=:serverName and (select sum(b.capacity) from SessionStatus b)<:total";
		// String
		// bottomSql="update SessionStatus status set status.capacity=(:total-(select sum(b.capacity) from SessionStatus b))";
		//
	}

	protected abstract int getMaxCapacity();

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}
}
