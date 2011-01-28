/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict;

import org.beangle.model.pojo.LongIdEntity;

/**
 * 限制模式
 * 
 * @author chaostone
 */
public interface RestrictPattern extends LongIdEntity {

	public String getContent();

	public void setContent(String content);

	public String getRemark();

	public void setRemark(String remark);

	public RestrictEntity getEntity();

	public void setEntity(RestrictEntity object);

}
