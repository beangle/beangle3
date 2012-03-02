/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.config.model;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.beangle.dao.pojo.LongIdObject;

/**
 * 系统配置项
 * 
 * @author chaostone
 */
@Entity(name="org.beangle.ems.config.model.PropertyConfigItemBean")
public class PropertyConfigItemBean extends LongIdObject {

	private static final long serialVersionUID = 1L;

	@NotNull
	@Size(max = 100)
	private String name;

	@NotNull
	@Size(max = 500)
	private String value;

	@NotNull
	@Size(max = 500)
	private String description;

	@NotNull
	@Size(max = 500)
	private String type;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
