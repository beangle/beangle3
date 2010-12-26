/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.webapp.database.action;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.webapp.database.service.DatasourceService;
import org.beangle.webapp.security.action.SecurityActionSupport;

public class DatasourceAction extends SecurityActionSupport {

	private DatasourceService datasourceService;

	public String test() {
		String name = get("name");
		DataSource dataSource = datasourceService.getDatasources(name);
		List<String> result = CollectUtils.newArrayList();
		Connection con = null;
		try {
			con = dataSource.getConnection();
			if (con != null) {
				java.sql.DatabaseMetaData dm = con.getMetaData();
				result.add("Driver Information");
				result.add("\tDriver Name: " + dm.getDriverName());
				result.add("\tDriver Version: " + dm.getDriverVersion());
				result.add("\nDatabase Information ");
				result.add("\tDatabase Name: " + dm.getDatabaseProductName());
				result.add("\tDatabase Version: " + dm.getDatabaseProductVersion());
				result.add("Avalilable Catalogs ");
				java.sql.ResultSet rs = dm.getCatalogs();
				while (rs.next()) {
					result.add("\tcatalog: " + rs.getString(1));
				}
				rs.close();
			} else {
				result.add("Error: No active Connection");
			}
		} catch (Exception e) {
			result.add(ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		} finally {
			try {
				if (con != null) con.close();
				con = null;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		put("result", result);
		return forward();
	}

	@Override
	protected String getShortName() {
		return "datasource";
	}

	public void setDatasourceService(DatasourceService dataSourceService) {
		this.datasourceService = dataSourceService;
	}

}
