/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.web.session.category;

/**
 * 分类用户会话控制器
 * 
 * @author chaostone
 * @version $Id: CategorySessionController.java Jun 18, 2011 9:39:08 PM chaostone $
 */
public interface CategorySessionController {

	public CategorySessionStat getSessionStat();

	public void free(String sessionId);

	public boolean allocate(String sessionId);

}
