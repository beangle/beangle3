/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security.restrict.service;

import java.util.Collection;
import java.util.List;

import org.beangle.ems.security.Resource;
import org.beangle.ems.security.User;
import org.beangle.ems.security.restrict.Restriction;
import org.beangle.model.query.builder.OqlBuilder;

/**
 * 资源访问约束服务
 * 
 * @author chaostone
 */
public interface RestrictionService {

	public List<Restriction> getRestrictions(User user, Resource resource);

	public List<Restriction> getAuthorityRestrictions(User user, Resource resource);

	/**
	 * Get field enumerated values.
	 * 
	 * @param propertyName
	 * @return
	 */
	public Object getPropertyValue(String propertyName,User user);

	/**
	 * @param builder
	 * @param restrictions
	 */
	public void apply(OqlBuilder<?> builder, Collection<? extends Restriction> restrictions);

}
