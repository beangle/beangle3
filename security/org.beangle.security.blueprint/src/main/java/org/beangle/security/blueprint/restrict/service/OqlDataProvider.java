/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.security.blueprint.restrict.service;

import java.util.List;

import org.beangle.model.persist.impl.BaseServiceImpl;

public class OqlDataProvider extends BaseServiceImpl implements DataProvider {

	public <T> List<T> getData(Class<T> type, String source) {
		return (List<T>) entityDao.searchHQLQuery(source);
	}

	public String asString(List<?> objects) {
		return null;
	}

	public String getName() {
		return "oql";
	}

}
