/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.collection.page;

import java.util.List;

/**
 * <p>
 * PagedList class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class PagedList<E> extends PageWapper<E> {

  private final List<E> datas;

  private int pageNo = 0;

  private int maxPageNo;

  private int pageSize;

  /**
   * <p>
   * Constructor for PagedList.
   * </p>
   * 
   * @param datas a {@link java.util.List} object.
   * @param pageSize a int.
   */
  public PagedList(List<E> datas, int pageSize) {
    this(datas, new PageLimit(1, pageSize));
  }

  /**
   * <p>
   * Constructor for PagedList.
   * </p>
   * 
   * @param datas a {@link java.util.List} object.
   * @param limit a {@link org.beangle.commons.collection.page.PageLimit} object.
   */
  public PagedList(List<E> datas, PageLimit limit) {
    super();
    this.datas = datas;
    this.pageSize = limit.getPageSize();
    this.pageNo = limit.getPageNo() - 1;
    if (datas.size() <= pageSize) {
      this.maxPageNo = 1;
    } else {
      final int remainder = datas.size() % pageSize;
      final int quotient = datas.size() / pageSize;
      this.maxPageNo = (0 == remainder) ? quotient : (quotient + 1);
    }
    this.next();
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
   * Getter for the field <code>pageSize</code>.
   * </p>
   * 
   * @return a int.
   */
  public int getPageSize() {
    return pageSize;
  }

  /**
   * <p>
   * getTotal.
   * </p>
   * 
   * @return a int.
   */
  public int getTotal() {
    return datas.size();
  }

  /**
   * <p>
   * getNextPageNo.
   * </p>
   * 
   * @return a int.
   */
  public final int getNextPageNo() {
    return getPage().getNextPageNo();
  }

  /**
   * <p>
   * getPreviousPageNo.
   * </p>
   * 
   * @return a int.
   */
  public final int getPreviousPageNo() {
    return getPage().getPreviousPageNo();
  }

  /**
   * <p>
   * hasNext.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean hasNext() {
    return getPageNo() < getMaxPageNo();
  }

  /**
   * <p>
   * hasPrevious.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean hasPrevious() {
    return getPageNo() > 1;
  }

  /**
   * <p>
   * next.
   * </p>
   * 
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  public Page<E> next() {
    return moveTo(pageNo + 1);
  }

  /**
   * <p>
   * previous.
   * </p>
   * 
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  public Page<E> previous() {
    return moveTo(pageNo - 1);
  }

  /** {@inheritDoc} */
  public Page<E> moveTo(int pageNo) {
    if (pageNo < 1) { throw new RuntimeException("error pageNo:" + pageNo); }
    this.pageNo = pageNo;
    int toIndex = pageNo * pageSize;
    SinglePage<E> newPage = new SinglePage<E>(pageNo, pageSize, datas.size(), datas.subList((pageNo - 1)
        * pageSize, (toIndex < datas.size()) ? toIndex : datas.size()));
    setPage(newPage);
    return this;
  }

}
