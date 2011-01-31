/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.security.blueprint.Resource;
import org.beangle.security.blueprint.User;
import org.beangle.security.blueprint.restrict.RestrictField;
import org.beangle.security.blueprint.restrict.Restriction;

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
	 * @param fieldName
	 * @return
	 */
	public List<?> getFieldValues(String fieldName);
	
	/**
	 * Extract field values;
	 * @param restrictions
	 * @param fieldName
	 * @return
	 */
	public List<?> getFieldValues(String fieldName,List<? extends Restriction> restrictions);

	/**
	 * 
	 * @param field
	 * @param restriction
	 * @return
	 */
	public Object getFieldValue(RestrictField field, Restriction restriction);
//	/**
//	 * 从总的集合中找出item中规定的集合
//	 * 
//	 * @param values
//	 * @param item
//	 * @return
//	 */
//	public <T> Set<T> select(Collection<T> values, Restriction res, RestrictField param);
//
//	/**
//	 * 从总的集合中找出items中规定的集合的并集
//	 * 
//	 * @param values
//	 * @param items
//	 * @return
//	 */
//	public <T> Set<T> select(Collection<T> values, List<? extends Restriction> restrictions,
//			RestrictField param);

	public void apply(OqlBuilder<?> builder, Collection<? extends Restriction> restrictions);

}