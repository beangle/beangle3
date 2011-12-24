/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import org.beangle.model.pojo.LongIdTimeEntity;

/**
 * @author chaostone
 * @version $Id: GroupMember.java Nov 2, 2010 6:24:48 PM chaostone $
 */
public interface GroupMember extends LongIdTimeEntity {

	/**
	 * get group
	 * 
	 * @return
	 */
	public Group getGroup();

	/**
	 * set group
	 * 
	 * @param group
	 */
	public void setGroup(Group group);

	/**
	 * membership
	 * 
	 * @return
	 */
	public boolean is(Ship ship);

	public User getUser();

	public void setUser(User user);

	public boolean isMember();

	public void setMember(boolean member);

	public boolean isManager();

	public void setManager(boolean manager);

	public boolean isGranter();

	public void setGranter(boolean granter);

	/**
	 * 成员关系
	 * 
	 * @author chaostone
	 */
	public enum Ship {
		/**
		 * just group member
		 */
		MEMBER,

		/**
		 * manage group perperties and authorities
		 */
		MANAGER,

		/**
		 * Can grant/revoke group to/from member
		 */
		GRANTER;

	}

}
