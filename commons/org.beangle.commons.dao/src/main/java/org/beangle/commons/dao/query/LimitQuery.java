/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.query;

import org.beangle.commons.collection.page.PageLimit;

/**
 * <p>
 * LimitQuery interface.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public interface LimitQuery<T> extends Query<T> {

  /**
   * <p>
   * getLimit.
   * </p>
   * 
   * @param <T> a T object.
   * @return a {@link org.beangle.commons.collection.page.PageLimit} object.
   */
  public PageLimit getLimit();

  /**
   * <p>
   * limit.
   * </p>
   * 
   * @param limit a {@link org.beangle.commons.collection.page.PageLimit} object.
   * @return a {@link org.beangle.commons.dao.query.LimitQuery} object.
   */
  public LimitQuery<T> limit(final PageLimit limit);

  /**
   * <p>
   * getCountQuery.
   * </p>
   * 
   * @return a {@link org.beangle.commons.dao.query.Query} object.
   */
  public Query<T> getCountQuery();
}
