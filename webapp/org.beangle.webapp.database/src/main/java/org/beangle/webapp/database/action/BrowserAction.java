/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.database.action;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.List;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang.xwork.StringUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.db.dialect.Dialect;
import org.beangle.db.meta.MetadataLoader;
import org.beangle.db.meta.Table;
import org.beangle.webapp.database.model.DatasourceBean;
import org.beangle.webapp.database.service.DatasourceService;
import org.beangle.webapp.security.action.SecurityActionSupport;

import com.opensymphony.xwork2.ActionContext;

public class BrowserAction extends SecurityActionSupport {

	public String table() {
		// DataSource datasource=getDataSource();
		return forward();
	}

	public String index() throws Exception {
		loadObjects();
		return forward();
	}
	
	private QueryContext getQueryContext() {
		return (QueryContext) ActionContext.getContext().getSession().get("QueryContext");
	}
	private void loadObjects() throws Exception {
		QueryContext queryConext = getQueryContext();
		DataSource datasource = queryConext.getDataSource();
		DatasourceBean dsbean = queryConext.getDatasourceBean();
		DatabaseMetaData meta = datasource.getConnection().getMetaData();
		List<String> schemas = CollectUtils.newArrayList();
		ResultSet rs = meta.getSchemas();
		while (rs.next()) {
			schemas.add(rs.getString(1));
		}
		MetadataLoader loader = new MetadataLoader((Dialect) Class.forName(
				dsbean.getProvider().getDialect()).newInstance(), meta);
		Set<Table> tables = CollectUtils.newHashSet();
		if (!schemas.isEmpty()) {
			String schema = get("schema");
			if (StringUtils.isNotEmpty(schema)) {
				queryConext.setSchema(schema);
			}
			if (StringUtils.isEmpty(schema)) {
				schema = queryConext.getSchema();
				if (StringUtils.isEmpty(schema)) {
					schema = schemas.get(0);
				}
			}
			put("schema", schema);
			tables = loader.loadTables(null, schema, false);
		}
		put("schemas", schemas);
		put("tables", tables);
	}
}
