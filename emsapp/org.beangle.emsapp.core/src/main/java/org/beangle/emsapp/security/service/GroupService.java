/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.emsapp.security.service;

import java.util.List;

import org.beangle.emsapp.security.Group;
import org.beangle.emsapp.security.User;

/**
 *
 * @author chaostone
 * @version $Id: GroupService.java Jul 29, 2011 1:59:38 AM chaostone $
 */
public interface GroupService {

	/**
	 * 创建一个用户组
	 * 
	 * @param creator
	 * @param group
	 */
	public void createGroup(User creator, Group group);

	/**
	 * 删除管理者与用户组的管理关系，如果该用户组为其所创建则彻底删除. 1)超级管理员不能被删除.<br>
	 * 2)如果删除人有超级管理员用户组，则可以删除不是自己创建的用户组
	 * 
	 * @param manager
	 * @param group
	 */
	public void removeGroup(User manager, List<Group> groups);

	/**
	 * 移动组
	 * 
	 * @param group
	 * @param parent
	 * @param indexno
	 */
	public void moveGroup(Group group, Group parent, int indexno);

}
