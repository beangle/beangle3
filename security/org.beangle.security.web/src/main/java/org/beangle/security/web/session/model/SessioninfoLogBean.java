/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.StringIdObject;

/**
 * 登录和退出日志
 * 
 * @author chaostone
 */
@Entity
public class SessioninfoLogBean extends StringIdObject {

	private static final long serialVersionUID = -3144771635148215917L;

	/** 服务器 */
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

	/** 在线时间 */
	@NotNull
	private Long onlineTime;

	/** 退出时间 */
	@NotNull
	private Date logoutAt;

	/** 备注 */
	@Size(max = 100)
	private String remark;

	public SessioninfoLogBean() {
		super();
	}

	public SessioninfoLogBean(SessioninfoBean info) {
		super();
		this.id = info.getId();
		this.serverName = info.getServerName();
		this.username = info.getUsername();
		this.fullname = info.getFullname();
		this.loginAt = info.getLoginAt();
		
		if (null != info.getExpiredAt()) this.logoutAt = info.getExpiredAt();
		else this.logoutAt = new Date();
		
		this.calcOnlineTime();

		this.os = info.getOs();
		this.ip = info.getIp();
		this.agent = info.getAgent();
		this.remark = info.getRemark();
	}

	public String toString() {
		String str = " User:[" + getUsername() + "]";
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

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Date getLogoutAt() {
		return logoutAt;
	}

	public void setLogoutAt(Date logoutAt) {
		this.logoutAt = logoutAt;
	}

	public Date getLoginAt() {
		return loginAt;
	}

	public void setLoginAt(Date loginAt) {
		this.loginAt = loginAt;
	}

	public Long getOnlineTime() {
		return onlineTime;
	}

	public void setOnlineTime(Long onlineTime) {
		this.onlineTime = onlineTime;
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
