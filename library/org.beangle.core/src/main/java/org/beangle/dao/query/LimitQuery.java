/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.query;

import org.beangle.collection.page.PageLimit;

public interface LimitQuery<T> extends Query<T> {

	public PageLimit getLimit();

	public LimitQuery<T> limit(final PageLimit limit);

	public Query<T> getCountQuery();
}