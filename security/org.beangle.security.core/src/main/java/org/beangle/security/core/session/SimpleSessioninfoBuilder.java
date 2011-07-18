/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import java.util.Date;

import org.beangle.model.pojo.StringIdObject;
import org.beangle.security.core.Authentication;

/**
 * @author chaostone
 * @version $Id: SimpleSessioninfoBuilder.java Jul 18, 2011 10:42:08 AM chaostone $
 */
public class SimpleSessioninfoBuilder implements SessioninfoBuilder {

	public Class<?> getSessioninfoClass() {
		return SimpleSessioninfo.class;
	}

	public Sessioninfo build(Authentication auth, String serverName, String sessionid) {
		return new SimpleSessioninfo(sessionid, serverName, auth.getName(), auth.getName());
	}

	public Object buildLog(Sessioninfo info) {
		return null;
	}

}

class SimpleSessioninfo extends StringIdObject implements Sessioninfo {

	private static final long serialVersionUID = 1462323011613320213L;

	/** 会话所处的服务器节点 */
	private String serverName;

	/** 系统登录用户 */
	private String username;

	/** 用户真实姓名 */
	private String fullname;

	/** 登录时间 */
	private Date loginAt;

	/** 过期时间 */
	private Date expiredAt;

	/** 备注 */
	private String remark;

	public SimpleSessioninfo() {
		super();
	}

	public SimpleSessioninfo(String id, String serverName, String username, String fullname) {
		super();
		this.id = id;
		this.serverName = serverName;
		this.username = username;
		this.fullname = fullname;
		this.loginAt = new Date(System.currentTimeMillis());
	}

	public String toString() {
		String str = " User:[" + getUsername() + "]";
		long onlineTime = System.currentTimeMillis() - loginAt.getTime();
		long minute = (onlineTime / 1000) / 60;
		long second = (onlineTime / 1000) % 60;
		str += "OnLine time:[" + minute + " minute " + second + " second]";
		return str;
	}

	public void addRemark(String added) {
		if (null == remark) {
			remark = added;
		} else {
			remark += added;
		}
	}

	public long getOnlineTime() {
		if (null == expiredAt) return System.currentTimeMillis() - loginAt.getTime();
		else return expiredAt.getTime() - loginAt.getTime();
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public Date getLoginAt() {
		return loginAt;
	}

	public void setLoginAt(Date loginAt) {
		this.loginAt = loginAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isExpired() {
		return null != expiredAt;
	}

	public void expireNow() {
		if (null == expiredAt) {
			this.expiredAt = new Date();
		}
	}

	public Date getExpiredAt() {
		return expiredAt;
	}

	public void setExpiredAt(Date expiredAt) {
		this.expiredAt = expiredAt;
	}

}
