/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

import org.beangle.model.Entity;

/**
 *
 * @author chaostone
 * @version $Id: IntIdEntity.java Oct 25, 2011 8:32:11 AM chaostone $
 */
public interface IntegerIdEntity extends Entity<Integer>{

	public Integer getId();

	public void setId(Integer id);
}
