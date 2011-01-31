/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict;

import java.util.Set;

import org.beangle.model.pojo.LongIdEntity;

public interface RestrictField extends LongIdEntity {

	public String getName();

	public void setName(String name);

	public String getType();

	public void setType(String type);

	public String getRemark();

	public void setRemark(String remark);

	public String getSource();

	public void setSource(String source);

	public Set<RestrictEntity> getEntities();

	public void setEntities(Set<RestrictEntity> objects);

	public boolean isMultiple();

	public void setMultiple(boolean multiple);

}
