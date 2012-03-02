/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.pojo;

import javax.persistence.MappedSuperclass;

import org.beangle.dao.Entity;

/**
 * @author chaostone
 * @version $Id: StringIdEntity.java Jul 15, 2011 7:58:42 AM chaostone $
 */
@MappedSuperclass
public interface StringIdEntity extends Entity<String> {

	public String getId();

	public void setId(String id);

}
