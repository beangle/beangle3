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
	private Authentication authentication;

	/** 会话id */
	private String sessionid;

	/** 登录时间 */
	private Date loginAt;

	private Date lastAccessAt;

	private String remark;

	private boolean expired = false;

	public SessionInfo(Authentication auth) {
		setAuthentication(auth);
		Date now = new Date();
		setLoginAt(now);
		setLastAccessAt(now);
	}

	public void expireNow() {
		this.expired = true;
	}

	public void refreshLastRequest() {
		this.lastAccessAt = new Date();
	}

	public Date getLastAccessAt() {
		return lastAccessAt;
	}

	public void setLastAccessAt(Date lastRequest) {
		this.lastAccessAt = lastRequest;
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
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

	public Date getLoginAt() {
		return loginAt;
	}

	public void setLoginAt(Date loginAt) {
		this.loginAt = loginAt;
	}

	public Long getOnlineTime() {
		return Long.valueOf(System.currentTimeMillis() - getLoginAt().getTime());
	}

	public Authentication getAuthentication() {
		return authentication;
	}

	public void setAuthentication(Authentication authentication) {
		this.authentication = authentication;
	}

}
