/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.query;

import java.util.Map;

import org.beangle.commons.collection.page.PageLimit;

public interface QueryBuilder<T> {

	public Query<T> build();

	public QueryBuilder<T> limit(PageLimit limit);

	public Map<String, Object> getParams();

	public QueryBuilder<T> params(Map<String, Object> newParams);
}
