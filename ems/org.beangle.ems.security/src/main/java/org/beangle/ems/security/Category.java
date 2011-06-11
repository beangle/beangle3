/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.security;

import org.beangle.model.pojo.LongIdEntity;

/**
 * 用户类别
 * 
 * @author chaostone
 */
public interface Category extends LongIdEntity {

	public String getName();

	public void setName(String name);

}
