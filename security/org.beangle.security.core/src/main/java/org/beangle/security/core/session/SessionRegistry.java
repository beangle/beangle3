/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.core.session;

import java.util.List;

import org.beangle.security.core.Authentication;

/**
 * 记录session信息的注册表
 * 
 * @author chaostone
 */
public interface SessionRegistry {

	/**
	 * 注册
	 * 
	 * @param authentication
	 * @param sessionid
	 */
	public void register(Authentication authentication, String sessionid) throws SessionException;

	/**
	 * 注销指定sessionid
	 * 
	 * @param sessionid
	 * @return
	 */
	public Sessioninfo remove(String sessionid);

	/**
	 * 过期指定会话
	 * 
	 * @param sessionid
	 * @return
	 */
	public void expire(String sessionid);

	/**
	 * 查询在线记录
	 * 
	 * @return
	 */
	public List<Sessioninfo> getAll();

	/**
	 * 查询某帐号的在线信息
	 * 
	 * @param principal
	 * @param includeExpiredSessions
	 * @return
	 */
	public List<Sessioninfo> getSessioninfos(String principal, boolean includeExpiredSessions);

	/**
	 * 查询对应sessionid的信息
	 * 
	 * @param sessionid
	 * @return
	 */
	public Sessioninfo getSessioninfo(String sessionid);

	/**
	 * 查询会话状态
	 * 
	 * @param sessionid
	 * @return
	 */
	public SessionStatus getSessionStatus(String sessionid);

	/**
	 * 查询帐号是否还有没有过期的在线记录
	 * 
	 * @param principal
	 * @return
	 */
	public boolean isRegisted(String principal);

	/**
	 * session count
	 * 
	 * @return
	 */
	public int count();

	/**
	 * 查询控制器
	 * 
	 * @return
	 */
	public SessionController getController();

}
