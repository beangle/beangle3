/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session;

import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.persist.impl.AbstractBaseDao;
import org.beangle.security.core.Authentication;
import org.beangle.security.core.session.SessionInfo;
import org.beangle.security.core.session.SessionRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBSessionRegistry extends AbstractBaseDao implements SessionRegistry {

	protected static final Logger logger = LoggerFactory.getLogger(DBSessionRegistry.class);

	public List<SessionInfo> getAll() {
		return entityDao.searchHQLQuery("from " + SessionInfo.class.getName());
	}

	public boolean isRegisted(Object principal) {
		return true;
	}

	public List<SessionInfo> getSessionInfos(Object principal, boolean includeExpiredSessions) {
		return CollectUtils.newArrayList();
	}

	public void register(String sessionId, Object principal, SessionInfo newActivity) {

	}

	public SessionInfo getSessionInfo(String sessionId) {
		return null;
	}

	public void refreshLastRequest(String sessionId) {
		SessionInfo info = getSessionInfo(sessionId);
		if (info != null) {
			info.refreshLastRequest();
		}
	}

	public void register(String sessionId, Authentication authentication) {
	}
	
	public void expire(String sessionId) {
	}

	public SessionInfo remove(String sessionId) {
		return null;
	}

	public int count() {
		return 0;
	}

}
