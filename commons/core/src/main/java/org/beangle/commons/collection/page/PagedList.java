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

  private int pageIndex = 0;

  private int totalPages;

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
    this.pageIndex = limit.getPageIndex() - 1;
    if (datas.size() <= pageSize) {
      this.totalPages = 1;
    } else {
      final int remainder = datas.size() % pageSize;
      final int quotient = datas.size() / pageSize;
      this.totalPages = (0 == remainder) ? quotient : (quotient + 1);
    }
    this.next();
  }

  /**
   * <p>
   * Getter for the field <code>totalPages</code>.
   * </p>
   *
   * @return a int.
   */
  public int getTotalPages() {
    return totalPages;
  }

  /**
   * Getter for the field <code>pageIndex</code>.
   *
   * @return a int.
   */
  public int getPageIndex() {
    return pageIndex;
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
   * getTotal.
   *
   * @return a int.
   */
  public int getTotalItems() {
    return datas.size();
  }

  /**
   * <p>
   * getNextPageIndex.
   * </p>
   *
   * @return a int.
   */
  public final int getNextPageIndex() {
    return getPage().getNextPageIndex();
  }

  /**
   * <p>
   * getPreviousPageIndex.
   * </p>
   *
   * @return a int.
   */
  public final int getPreviousPageIndex() {
    return getPage().getPreviousPageIndex();
  }

  /**
   * <p>
   * hasNext.
   * </p>
   *
   * @return a boolean.
   */
  public boolean hasNext() {
    return getPageIndex() < getTotalPages();
  }

  /**
   * <p>
   * hasPrevious.
   * </p>
   *
   * @return a boolean.
   */
  public boolean hasPrevious() {
    return getPageIndex() > 1;
  }

  /**
   * <p>
   * next.
   * </p>
   *
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  public Page<E> next() {
    return moveTo(pageIndex + 1);
  }

  /**
   * <p>
   * previous.
   * </p>
   *
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  public Page<E> previous() {
    return moveTo(pageIndex - 1);
  }

  /** {@inheritDoc} */
  public Page<E> moveTo(int pageIndex) {
    if (pageIndex < 1) { throw new RuntimeException("error pageIndex:" + pageIndex); }
    this.pageIndex = pageIndex;
    int toIndex = pageIndex * pageSize;
    SinglePage<E> newPage = new SinglePage<E>(pageIndex, pageSize, datas.size(), datas.subList((pageIndex - 1)
        * pageSize, (toIndex < datas.size()) ? toIndex : datas.size()));
    setPage(newPage);
    return this;
  }

}
