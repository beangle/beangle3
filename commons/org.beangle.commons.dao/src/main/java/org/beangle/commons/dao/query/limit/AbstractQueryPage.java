/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.dao.query.limit;

import java.util.Iterator;

import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.PageLimit;
import org.beangle.commons.collection.page.PageWapper;
import org.beangle.commons.collection.page.SinglePage;
import org.beangle.commons.dao.query.LimitQuery;

/**
 * 基于查询的分页。<br>
 * 当使用或导出大批量数据时，使用者仍以List的方式进行迭代。<br>
 * 该实现则是内部采用分页方式。
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class AbstractQueryPage<T> extends PageWapper<T> {

  protected int pageNo = 0;

  protected int maxPageNo = 0;

  protected LimitQuery<T> query;

  /** {@inheritDoc} */
  abstract public Page<T> moveTo(int pageNo);

  /**
   * <p>
   * Constructor for AbstractQueryPage.
   * </p>
   */
  public AbstractQueryPage() {
    super();
  }

  /**
   * <p>
   * Constructor for AbstractQueryPage.
   * </p>
   * 
   * @param query a {@link org.beangle.commons.dao.query.LimitQuery} object.
   */
  public AbstractQueryPage(LimitQuery<T> query) {
    this.query = query;
    if (null != query) {
      if (null == query.getLimit()) {
        query.limit(new PageLimit(pageNo, Page.DEFAULT_PAGE_SIZE));
      } else {
        pageNo = query.getLimit().getPageNo() - 1;
      }
    }
  }

  /**
   * 按照单个分页数据设置.
   * 
   * @param page a {@link org.beangle.commons.collection.page.SinglePage} object.
   */
  protected void setPageData(SinglePage<T> page) {
    setPage(page);
    this.pageNo = page.getPageNo();
    this.maxPageNo = page.getMaxPageNo();
  }

  /**
   * <p>
   * next.
   * </p>
   * 
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  public Page<T> next() {
    return moveTo(pageNo + 1);
  }

  /**
   * <p>
   * previous.
   * </p>
   * 
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  public Page<T> previous() {
    return moveTo(pageNo - 1);
  }

  /**
   * <p>
   * hasNext.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean hasNext() {
    return maxPageNo > pageNo;
  }

  /**
   * <p>
   * hasPrevious.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean hasPrevious() {
    return pageNo > 1;
  }

  /**
   * <p>
   * getFirstPageNo.
   * </p>
   * 
   * @return a int.
   */
  public int getFirstPageNo() {
    return 1;
  }

  /**
   * <p>
   * Getter for the field <code>maxPageNo</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getMaxPageNo() {
    return maxPageNo;
  }

  /**
   * <p>
   * getNextPageNo.
   * </p>
   * 
   * @return a int.
   */
  public int getNextPageNo() {
    return getPage().getNextPageNo();
  }

  /**
   * <p>
   * Getter for the field <code>pageNo</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getPageNo() {
    return pageNo;
  }

  /**
   * <p>
   * getPageSize.
   * </p>
   * 
   * @return a int.
   */
  public int getPageSize() {
    return query.getLimit().getPageSize();
  }

  /**
   * <p>
   * getPreviousPageNo.
   * </p>
   * 
   * @return a int.
   */
  public int getPreviousPageNo() {
    return getPage().getPreviousPageNo();
  }

  /**
   * <p>
   * getTotal.
   * </p>
   * 
   * @return a int.
   */
  public int getTotal() {
    return getPage().getTotal();
  }

  /**
   * <p>
   * iterator.
   * </p>
   * 
   * @return a {@link java.util.Iterator} object.
   */
  public Iterator<T> iterator() {
    return new PageIterator<T>(this);
  }

}

class PageIterator<T> implements Iterator<T> {

  private final AbstractQueryPage<T> queryPage;

  private int dataIndex;

  public PageIterator(AbstractQueryPage<T> queryPage) {
    this.queryPage = queryPage;
    this.dataIndex = 0;
  }

  public boolean hasNext() {
    return (dataIndex < queryPage.getPage().getItems().size()) || (queryPage.hasNext());
  }

  public T next() {
    if (dataIndex < queryPage.getPage().size()) {
      return queryPage.getPage().getItems().get(dataIndex++);
    } else {
      queryPage.next();
      dataIndex = 0;
      return queryPage.getPage().getItems().get(dataIndex++);
    }
  }

  public void remove() {

  }

}
