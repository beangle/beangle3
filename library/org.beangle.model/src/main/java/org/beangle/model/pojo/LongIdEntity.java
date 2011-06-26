/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

import javax.persistence.MappedSuperclass;

import org.beangle.model.Entity;

@MappedSuperclass
public interface LongIdEntity extends Entity<Long> {

	public Long getId();

	public void setId(Long id);
}
