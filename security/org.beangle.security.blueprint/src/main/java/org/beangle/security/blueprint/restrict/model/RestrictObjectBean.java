/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.model;

import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.model.pojo.LongIdObject;
import org.beangle.security.blueprint.restrict.RestrictField;
import org.beangle.security.blueprint.restrict.RestrictObject;

public class RestrictObjectBean extends LongIdObject implements RestrictObject {

	private static final long serialVersionUID = -5761007041977213647L;

	private String name;

	private String type;

	private String remark;

	private Set<RestrictField> fields = CollectUtils.newHashSet();

	public RestrictField getField(String paramName) {
		for (final RestrictField param : fields) {
			if (param.getName().equals(paramName)) { return param; }
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<RestrictField> getFields() {
		return fields;
	}

	public void setFields(Set<RestrictField> params) {
		this.fields = params;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
