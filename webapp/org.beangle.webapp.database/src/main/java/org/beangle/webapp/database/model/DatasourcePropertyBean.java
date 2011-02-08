/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.database.model;

import org.beangle.model.pojo.LongIdObject;

public class DatasourcePropertyBean extends LongIdObject {
	private static final long serialVersionUID = 1L;
	private DatasourceBean source;
	private String name;
	private String value;

	public DatasourceBean getSource() {
		return source;
	}

	public void setSource(DatasourceBean datasource) {
		this.source = datasource;
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

}
