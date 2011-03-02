/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public interface CodeEntity {

	public String getCode();

	public void setCode(String code);
}
