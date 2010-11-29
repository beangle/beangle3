/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict;

import java.util.Set;

import org.beangle.model.pojo.LongIdEntity;

/**
 * 数据限制对象
 * 
 * @author chaostone
 */
public interface RestrictObject extends LongIdEntity {

	public String getName();

	public void setName(String name);

	public String getType();

	public void setType(String type);

	public String getRemark();

	public void setRemark(String remark);

	public Set<RestrictField> getFields();

	public void setFields(Set<RestrictField> params);

	public RestrictField getField(String paramName);

}
