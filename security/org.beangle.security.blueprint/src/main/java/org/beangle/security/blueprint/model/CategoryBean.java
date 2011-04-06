/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.model;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.security.blueprint.Category;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity(name = "org.beangle.security.blueprint.Category")
@Cacheable
@Cache(region = "beangle.security", usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class CategoryBean extends LongIdObject implements Category {

	private static final long serialVersionUID = -5929038500510261629L;

	@NotNull
	@Size(max = 50)
	@Column(unique = true)
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CategoryBean() {
		super();
	}

	public CategoryBean(Long id) {
		super();
		this.setId(id);
	}

	@Override
	public String toString() {
		return name;
	}

}
