/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.pojo;

import java.util.Date;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class LongIdTimeObject extends LongIdObject implements LongIdTimeEntity {
	private static final long serialVersionUID = -5395713578471562117L;

	/** 创建时间 */
	protected Date createdAt;

	/** 最后修改时间 */
	protected Date updatedAt;

	public LongIdTimeObject() {
		super();
	}

	public LongIdTimeObject(Long id) {
		super(id);
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

}
