/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint;

import org.beangle.model.pojo.LongIdEntity;
import org.beangle.security.blueprint.restrict.AuthorityRestriction;
import org.beangle.security.blueprint.restrict.RestrictionHolder;

/**
 * 权限
 * 
 * @author chaostone 2005-9-26
 */
public interface Authority extends LongIdEntity, Cloneable, RestrictionHolder<AuthorityRestriction> {
	/**
	 * 系统资源
	 * 
	 * @return
	 */
	public Resource getResource();

	/**
	 * 设置资源
	 * 
	 * @param resource
	 */
	public void setResource(Resource resource);

	/**
	 * 设置授权对象
	 * 
	 * @param group
	 */
	public void setGroup(Group group);

	/**
	 * 获得授权对象
	 * 
	 * @param ao
	 */
	public Group getGroup();

	public void merge(Authority other);

	public Object clone();
}
