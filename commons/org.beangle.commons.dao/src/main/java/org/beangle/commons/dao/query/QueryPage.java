/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.query;

import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.SinglePage;
import org.beangle.commons.dao.EntityDao;
import org.beangle.commons.dao.query.builder.OqlBuilder;
import org.beangle.commons.dao.query.limit.AbstractQueryPage;

/**
 * <p>
 * QueryPage class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class QueryPage<T> extends AbstractQueryPage<T> {

  private EntityDao entityDao;

  /**
   * <p>
   * Constructor for QueryPage.
   * </p>
   * 
   * @param query a {@link org.beangle.commons.dao.query.LimitQuery} object.
   * @param entityDao a {@link org.beangle.commons.dao.EntityDao} object.
   */
  public QueryPage(LimitQuery<T> query, EntityDao entityDao) {
    super(query);
    this.entityDao = entityDao;
    next();
  }

  /**
   * <p>
   * Constructor for QueryPage.
   * </p>
   * 
   * @param builder a {@link org.beangle.commons.dao.query.builder.OqlBuilder} object.
   * @param entityDao a {@link org.beangle.commons.dao.EntityDao} object.
   */
  public QueryPage(OqlBuilder<T> builder, EntityDao entityDao) {
    super((LimitQuery<T>) builder.build());
    this.entityDao = entityDao;
    next();
  }

  /**
   * <p>
   * Setter for the field <code>entityDao</code>.
   * </p>
   * 
   * @param entityDao a {@link org.beangle.commons.dao.EntityDao} object.
   */
  public void setEntityDao(EntityDao entityDao) {
    this.entityDao = entityDao;
  }

  /** {@inheritDoc} */
  public Page<T> moveTo(int pageNo) {
    query.getLimit().setPageNo(pageNo);
    SinglePage<T> datas = (SinglePage<T>) entityDao.search(query);
    setPageData(datas);
    return this;
  }
}
