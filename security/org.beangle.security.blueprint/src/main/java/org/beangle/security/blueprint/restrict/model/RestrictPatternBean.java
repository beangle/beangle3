/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.model;

import org.beangle.model.pojo.LongIdObject;
import org.beangle.security.blueprint.restrict.RestrictObject;
import org.beangle.security.blueprint.restrict.RestrictPattern;

public class RestrictPatternBean extends LongIdObject implements RestrictPattern {

	private static final long serialVersionUID = 3491583230212588933L;

	private String remark;

	private String content;

	private RestrictObject object;

	public String getContent() {
		return content;
	}

	public void setContent(String pattern) {
		this.content = pattern;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String discription) {
		this.remark = discription;
	}

	public RestrictObject getObject() {
		return object;
	}

	public void setObject(RestrictObject paramGroup) {
		this.object = paramGroup;
	}

}
