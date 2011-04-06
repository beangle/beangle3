/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.query;

import org.beangle.commons.collection.page.PageLimit;

public interface LimitQuery<T> extends Query<T> {

	public PageLimit getLimit();

	public LimitQuery<T> limit(final PageLimit limit);

	public Query<T> getCountQuery();
}
