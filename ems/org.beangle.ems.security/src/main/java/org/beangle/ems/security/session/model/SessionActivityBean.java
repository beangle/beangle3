/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.session.model;

import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.ems.security.Category;
import org.beangle.ems.security.session.SessionActivity;

/**
 * 登录和退出日志
 * 
 * @author chaostone
 */
@Entity(name = "org.beangle.ems.security.session.SessionActivity")
public class SessionActivityBean extends LongIdObject implements SessionActivity {

	private static final long serialVersionUID = -3144771635148215917L;

	/** 会话ID */
	@NotNull
	@Size(max = 100)
	private String sessionid;

	/** 系统登录用户 */
	@NotNull
	@Size(max = 40)
	private String name;

	/** 用户真实姓名 */
	@NotNull
	@Size(max = 50)
	private String fullname;

	/** 登录IP */
	@Size(max = 100)
	private String host;

	/** OS */
	@Size(max = 50)
	private String os;

	/** agent */
	@Size(max = 50)
	private String agent;

	/** 登录时间 */
	@NotNull
	private Date loginAt;

	/** 最后访问时间 */
	@NotNull
	private Date lastAccessAt;

	/** 在线时间 */
	@NotNull
	private Long onlineTime;

	/** 用户类型 */
	@NotNull
	private Category category;

	/** 退出时间 */
	@NotNull
	private Timestamp logoutAt;

	/** 备注 */
	@Size(max = 100)
	private String remark;

	public SessionActivityBean() {
		super();
	}

	public SessionActivityBean(String sessionId, String name, String fullname, Category category) {
		super();
		this.sessionid = sessionId;
		this.name = name;
		this.fullname = fullname;
		this.loginAt = new Date(System.currentTimeMillis());
		this.lastAccessAt = new Date(System.currentTimeMillis());
		this.category = category;
	}

	public String toString() {
		String str = " User:[" + getName() + "]";
		long onlineTime = System.currentTimeMillis() - loginAt.getTime();
		long minute = (onlineTime / 1000) / 60;
		long second = (onlineTime / 1000) % 60;
		str += "OnLine time:[" + minute + " minute " + second + " second]";
		return str;
	}

	public void calcOnlineTime() {
		if (null == logoutAt) {
			setOnlineTime(Long.valueOf(System.currentTimeMillis() - loginAt.getTime()));
		} else {
			setOnlineTime(Long.valueOf(logoutAt.getTime() - loginAt.getTime()));
		}
	}

	public String getSessionid() {
		return sessionid;
	}

	public void setSessionid(String sessionId) {
		this.sessionid = sessionId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Date getLoginAt() {
		return loginAt;
	}

	public void setLoginAt(Date loginAt) {
		this.loginAt = loginAt;
	}

	public Date getLastAccessAt() {
		return lastAccessAt;
	}

	public void setLastAccessAt(Date lastAccessAt) {
		this.lastAccessAt = lastAccessAt;
	}

	public Long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Long onlineTime) {
		this.onlineTime = onlineTime;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public Timestamp getLogoutAt() {
		return logoutAt;
	}

	public void setLogoutAt(Timestamp logoutAt) {
		this.logoutAt = logoutAt;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

}
