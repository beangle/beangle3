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

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * Pages class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public class Pages {

  private static final Page<Object> EMPTY_PAGE = new EmptyPage<Object>();

  /**
   * <p>
   * emptyPage.
   * </p>
   * 
   * @param <T> a T object.
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  @SuppressWarnings("unchecked")
  public static final <T> Page<T> emptyPage() {
    return (Page<T>) EMPTY_PAGE;
  }

  private static class EmptyPage<E> extends AbstractList<E> implements Page<E> {

    public int getFirstPageNo() {
      return 0;
    }

    public int getMaxPageNo() {
      return 0;
    }

    public int getNextPageNo() {
      return 0;
    }

    public int getPageNo() {
      return 0;
    }

    public int getPageSize() {
      return 0;
    }

    public int getPreviousPageNo() {
      return 0;
    }

    public int getTotal() {
      return 0;
    }

    public boolean hasNext() {
      return false;
    }

    public boolean hasPrevious() {
      return false;
    }

    public Page<E> next() {
      return this;
    }

    public Page<E> previous() {
      return this;
    }

    public E get(int index) {
      return null;
    }

    public int size() {
      return 0;
    }

    public Page<E> moveTo(int pageNo) {
      return this;
    }

    public List<E> getItems() {
      return new ArrayList<E>(0);
    }

  }

}
