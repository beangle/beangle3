/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session.category;

import java.lang.management.ManagementFactory;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.AbstractSessionController;
import org.beangle.security.core.session.SessionInfo;
import org.beangle.security.core.session.SessionStat;

/**
 * @author chaostone
 * @version $Id: ProfileSessionController.java Jun 18, 2011 7:18:44 PM chaostone $
 */
public class MultiCategorySessionController extends AbstractSessionController {

	private CategorySessionControllerFactory controllerFactory;

	private Map<Object, CategorySessionController> profiles = CollectUtils.newHashMap();

	protected CategorySessionController getController(Authentication auth) {
		Object category = ((CategoryPrincipal) auth.getPrincipal()).getCategory();
		CategorySessionController controller = profiles.get(category);
		if (null == controller) {
			synchronized (this) {
				controller = controllerFactory.getInstance(category);
				profiles.put(category, controller);
			}
		}
		return controller;
	}

	public int getMaxSessions(Authentication auth) {
		return getController(auth).getSessionStat().getUserMaxSessions();
	}

	public void onLogout(SessionInfo info) {
		CategorySessionController controller = getController(info.getAuthentication());
		controller.free(info.getSessionid());
	}

	public List<SessionStat> getSessionStats() {
		Map<Object, Integer> details = CollectUtils.newHashMap();
		int total = 0;
		int capacity = 0;
		for (CategorySessionController controller : profiles.values()) {
			CategorySessionStat status = controller.getSessionStat();
			details.put(status.getCategory(), status.getOnline());
			total += status.getOnline();
			capacity += status.getCapacity();
		}
		return Collections.singletonList(new SessionStat(ManagementFactory.getRuntimeMXBean().getName(),
				new Date(), capacity, total, details));
	}

	@Override
	protected boolean allocate(Authentication auth, String sessionId) {
		return getController(auth).allocate(sessionId);
	}

	public void setControllerFactory(CategorySessionControllerFactory controllerFactory) {
		this.controllerFactory = controllerFactory;
	}

	// public boolean changeCategory(Object from, Object to) {
	// LimitProfile fromProfile = profileMap.get(from);
	// boolean reserved = false;
	// synchronized (fromProfile) {
	// reserved = fromProfile.reserve();
	// }
	// if (!reserved) { return false; }
	// LimitProfile toProfile = profileMap.get(to);
	// synchronized (toProfile) {
	// toProfile.left();
	// }
	// return true;
	// }

}
