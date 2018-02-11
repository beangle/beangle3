/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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
