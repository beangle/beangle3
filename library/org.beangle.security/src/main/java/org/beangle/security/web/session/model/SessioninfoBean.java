/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.dao.pojo.StringIdObject;
import org.beangle.security.core.session.category.CategorySessioninfo;

/**
 * 默认会话活动bean
 * 
 * @author chaostone
 * @version $Id: SessioninfoBean.java Jul 7, 2011 10:04:48 PM chaostone $
 */
@Entity(name="org.beangle.security.web.session.model.SessioninfoBean")
public class SessioninfoBean extends StringIdObject implements CategorySessioninfo {
	private static final long serialVersionUID = -6144822263608415556L;

	/** 会话所处的服务器节点 */
	@NotNull
	@Size(max = 100)
	private String serverName;

	/** 系统登录用户 */
	@NotNull
	@Size(max = 40)
	private String username;

	/** 用户真实姓名 */
	@NotNull
	@Size(max = 50)
	private String fullname;

	private String category;

	/** 登录IP */
	@Size(max = 100)
	private String ip;

	/** OS */
	@Size(max = 50)
	private String os;

	/** agent */
	@Size(max = 50)
	private String agent;

	/** 登录时间 */
	@NotNull
	private Date loginAt;

	/** 过期时间 */
	private Date expiredAt;

	/** 最后访问时间*/
	private Date lastAccessAt;
	
	/** 备注 */
	@Size(max = 100)
	private String remark;

	public SessioninfoBean() {
		super();
	}

	public SessioninfoBean(String id, String serverName, String username, String fullname) {
		super();
		this.id = id;
		this.serverName = serverName;
		this.username = username;
		this.fullname = fullname;
		this.loginAt = new Date(System.currentTimeMillis());
		this.lastAccessAt=loginAt;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Date getLoginAt() {
		return loginAt;
	}

	public void setLoginAt(Date loginAt) {
		this.loginAt = loginAt;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public String getAgent() {
		return agent;
	}

	public void setAgent(String agent) {
		this.agent = agent;
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

	public Date getLastAccessAt() {
		return lastAccessAt;
	}

	public void setLastAccessAt(Date lastAccessAt) {
		this.lastAccessAt = lastAccessAt;
	}
}
