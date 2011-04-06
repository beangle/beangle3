/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session.category;

import java.util.List;

import org.beangle.security.core.session.SessionController;

/**
 * 登录会话控制器
 * 
 * @author chaostone
 * @version $Id: ConcurrentSessionController.java Nov 21, 2010 3:44:13 PM
 *          chaostone $
 */
public interface CategorySessionController extends SessionController {

	/**
	 * load profile
	 * 
	 * @return
	 */
	public List<LimitProfile> getProfiles();

	/**
	 * 返回某一类用户的最大用户数
	 * 
	 * @param category
	 * @return
	 */
	public LimitProfile getProfile(Object category);

	/**
	 * reserve space for given sessionid
	 * 
	 * @param category
	 * @param sessionId
	 * @return
	 */
	public boolean reserve(Object category, String sessionId);

	/**
	 * left given category
	 * 
	 * @param category
	 */
	public void left(Object category);

}
