/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.model;

import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.security.blueprint.restrict.RestrictField;
import org.beangle.security.blueprint.restrict.RestrictEntity;

public class RestrictFieldBean extends LongIdObject implements RestrictField {
	private static final long serialVersionUID = 1L;

	private String name;

	private String type;

	private String remark;

	private String source;

	private Set<RestrictEntity> entities = CollectUtils.newHashSet();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Set<RestrictEntity> getEntities() {
		return entities;
	}

	public void setEntities(Set<RestrictEntity> objects) {
		this.entities = objects;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
