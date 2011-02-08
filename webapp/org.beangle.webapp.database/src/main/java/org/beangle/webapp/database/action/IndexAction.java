/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.database.action;

import java.util.List;

import javax.sql.DataSource;

import org.beangle.webapp.database.model.DatasourceBean;
import org.beangle.webapp.database.service.DatasourceService;
import org.beangle.webapp.security.action.SecurityActionSupport;

import com.opensymphony.xwork2.ActionContext;

public class IndexAction extends SecurityActionSupport {

	private DatasourceService datasourceService;

	private QueryContext getQueryContext() {
		return (QueryContext) ActionContext.getContext().getSession().get("QueryContext");
	}

	public String connect() {
		QueryContext queryContext = getQueryContext();
		Long datasourceId = getLong("datasource.id");
		if (null == queryContext) {
			if (null == datasourceId) {
				List<?> datasources = entityDao.getAll(DatasourceBean.class);
				put("datasources", datasources);
				return forward();
			} else {
				DataSource datasource = datasourceService.getDatasource(datasourceId);
				DatasourceBean dsbean = entityDao.get(DatasourceBean.class, datasourceId);
				dsbean.getProvider().getDialect();
				ActionContext.getContext().getSession()
						.put("QueryContext", new QueryContext(datasource, dsbean));
				return redirect("index", "info.action.success");
			}
		}
		return forward();
	}

	public String index() {
		
		return forward();
	}

	public String disconnect() {
		ActionContext.getContext().getSession().remove("QueryContext");
		return redirect("index", "info.action.success");
	}

	public void setDatasourceService(DatasourceService datasourceService) {
		this.datasourceService = datasourceService;
	}
}
