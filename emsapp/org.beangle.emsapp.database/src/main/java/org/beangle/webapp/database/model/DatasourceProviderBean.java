/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.database.model;

import org.beangle.model.pojo.LongIdObject;

public class DatasourceProviderBean extends LongIdObject {

	private static final long serialVersionUID = -7792635675766559389L;

	private String dialect;

	private String name;

	public String getDialect() {
		return dialect;
	}

	public void setDialect(String dialect) {
		this.dialect = dialect;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
