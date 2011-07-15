/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import java.util.Date;

/**
 * 会话状态
 * 
 * @author chaostone
 * @version $Id: SessionStatus.java Jul 14, 2011 7:11:39 PM chaostone $
 */
public final class SessionStatus {

	final String username;

	final Date expiredAt;

	public SessionStatus(Sessioninfo info) {
		super();
		this.username = info.getUsername();
		this.expiredAt = info.getExpiredAt();
	}
	public SessionStatus(String username, Date expiredAt) {
		super();
		this.username = username;
		this.expiredAt = expiredAt;
	}

	public String getUsername() {
		return username;
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	public boolean isExpired() {
		return null != expiredAt;
	}
}
