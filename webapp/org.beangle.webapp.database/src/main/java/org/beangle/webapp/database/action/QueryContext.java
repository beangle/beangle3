/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.database.action;

import javax.sql.DataSource;

import org.beangle.webapp.database.model.DatasourceBean;

public class QueryContext {

	private DataSource dataSource;

	private DatasourceBean datasourceBean;

	private String schema;

	public QueryContext() {
		super();
	}

	public QueryContext(DataSource dataSource, DatasourceBean datasourceBean) {
		super();
		this.dataSource = dataSource;
		this.datasourceBean = datasourceBean;
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public DatasourceBean getDatasourceBean() {
		return datasourceBean;
	}

	public void setDatasourceBean(DatasourceBean datasourceBean) {
		this.datasourceBean = datasourceBean;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

}
