/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.beangle.model.pojo.LongIdTimeObject;
import org.beangle.ems.security.Group;
import org.beangle.ems.security.GroupMember;
import org.beangle.ems.security.User;

/**
 * @author chaostone
 * @version $Id: GroupMemberBean.java Nov 2, 2010 6:45:48 PM chaostone $
 */
@Entity(name = "org.beangle.ems.security.GroupMember")
public class GroupMemberBean extends LongIdTimeObject implements GroupMember {

	private static final long serialVersionUID = -3882917413656652492L;
	@NotNull
	private Group group;
	@NotNull
	private User user;
	@NotNull
	private boolean member;
	@NotNull
	private boolean manager;
	@NotNull
	private boolean granter;

	public GroupMemberBean() {
		super();
	}

	public GroupMemberBean(Group group, User user, GroupMember.Ship ship) {
		super();
		this.group = group;
		this.user = user;
		this.createdAt = new Date();
		this.updatedAt = new Date();
		if (null != ship) {
			if (ship.equals(GroupMember.Ship.MEMBER)) {
				member = true;
			} else if (ship.equals(GroupMember.Ship.MANAGER)) {
				manager = true;
			} else {
				granter = true;
			}
		}
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public boolean isMember() {
		return member;
	}

	public void setMember(boolean member) {
		this.member = member;
	}

	public boolean isManager() {
		return manager;
	}

	public void setManager(boolean manager) {
		this.manager = manager;
	}

	public boolean isGranter() {
		return granter;
	}

	public void setGranter(boolean granter) {
		this.granter = granter;
	}

	public boolean is(GroupMember.Ship ship) {
		if (ship.equals(GroupMember.Ship.MEMBER)) { return member; }
		if (ship.equals(GroupMember.Ship.MANAGER)) { return manager; }
		if (ship.equals(GroupMember.Ship.GRANTER)) { return granter; }
		return false;
	}

}
