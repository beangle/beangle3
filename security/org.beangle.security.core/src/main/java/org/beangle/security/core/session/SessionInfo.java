/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import java.util.Date;

import org.beangle.security.core.Authentication;

/**
 * 用户会话信息
 * 
 * @author chaostone
 */
public class SessionInfo {

	private static final long serialVersionUID = -4828041170356897582L;

	/** login */
	private final Authentication authentication;

	/** 会话id */
	private final String sessionid;

	/** 登录时间 */
	private final Date loginAt = new Date();

	/**最后访问的系统微秒数*/
	private long lastAccessMillis;

	private String remark;

	private boolean expired = false;

	public SessionInfo(final String sessionid, final Authentication auth) {
		this.sessionid = sessionid;
		this.authentication = auth;
		refreshLastRequest();
	}

	public void expireNow() {
		this.expired = true;
	}

	public void refreshLastRequest() {
		this.lastAccessMillis = System.currentTimeMillis();
	}

	public Date getLastAccessAt() {
		return new Date(lastAccessMillis);
	}

	public String getSessionid() {
		return sessionid;
	}

	public boolean isExpired() {
		return expired;
	}

	public Date getLoginAt() {
		return loginAt;
	}

	public Long getOnlineTime() {
		return Long.valueOf(System.currentTimeMillis() - getLoginAt().getTime());
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void appendRemark(String added) {
		if (null == remark) {
			remark = added;
		} else {
			remark += added;
		}
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
