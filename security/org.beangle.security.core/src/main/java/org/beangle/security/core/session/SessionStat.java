/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import java.util.Date;
import java.util.Map;

/**
 * 服务器会话统计
 * 
 * @author chaostone
 * @version $Id: SessionStat.java Jun 18, 2011 2:53:57 PM chaostone $
 */
public class SessionStat {

	private final String serverName;

	private final int sessions;

	private final int capacity;
	
	private final Date statAt;

	private final Map<?, Integer> details;

	public SessionStat(String serverName, Date statAt,int capacity, int sessions, Map<?, Integer> details) {
		super();
		this.serverName = serverName;
		this.capacity=capacity;
		this.sessions = sessions;
		this.statAt = statAt;
		this.details = details;
	}

	public String getServerName() {
		return serverName;
	}

	public int getCapacity() {
		return capacity;
	}

	public int getSessions() {
		return sessions;
	}

	public Date getStatAt() {
		return statAt;
	}

	public Map<?, Integer> getDetails() {
		return details;
	}

}
