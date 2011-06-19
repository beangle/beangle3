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
	 * @param sessionId
	 */
	public void register(Authentication authentication, String sessionId) throws SessionException;

	/**
	 * 注销指定sessionId
	 * 
	 * @param sessionId
	 * @return
	 */
	public SessionInfo remove(String sessionId);

	/**
	 * 过期指定会话
	 * 
	 * @param sessionId
	 * @return
	 */
	public void expire(String sessionId);

	/**
	 * 查询在线记录
	 * 
	 * @return
	 */
	public List<SessionInfo> getAll();

	/**
	 * 查询某帐号的在线信息
	 * 
	 * @param principal
	 * @param includeExpiredSessions
	 * @return
	 */
	public List<SessionInfo> getSessionInfos(Object principal, boolean includeExpiredSessions);

	/**
	 * 查询对应sessionId的信息
	 * 
	 * @param sessionId
	 * @return
	 */
	public SessionInfo getSessionInfo(String sessionId);

	/**
	 * 查询帐号是否还有没有过期的在线记录
	 * 
	 * @param principal
	 * @return
	 */
	public boolean isRegisted(Object principal);

	/**
	 * 更新对应sessionId的最后访问时间
	 * 
	 * @param sessionId
	 */
	public void refreshLastRequest(String sessionId);

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
