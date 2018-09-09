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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 分页对象
 *
 * @author chaostone
 * @version $Id: $
 */
public class SinglePage<E> implements Page<E> {

  private int pageIndex;

  private int pageSize;

  private int totalItems;

  private List<E> items;

  /**
   * <p>
   * Constructor for SinglePage.
   * </p>
   */
  public SinglePage() {
    super();
  }

  /**
   * <p>
   * Constructor for SinglePage.
   * </p>
   *
   * @param pageIndex a int.
   * @param pageSize a int.
   * @param total a int.
   * @param dataItems a {@link java.util.List} object.
   */
  public SinglePage(int pageIndex, int pageSize, int total, List<E> dataItems) {
    this.items = dataItems;
    if (pageSize < 1) {
      this.pageSize = 2;
    } else {
      this.pageSize = pageSize;
    }
    if (pageIndex < 1) {
      this.pageIndex = 1;
    } else {
      this.pageIndex = pageIndex;
    }
    this.totalItems = total;
  }

  /**
   * <p>
   * getFirstPageIndex.
   * </p>
   *
   * @return a int.
   */
  public final int getFirstPageIndex() {
    return 1;
  }

  /**
   * <p>
   * getTotalPages.
   * </p>
   *
   * @return a int.
   */
  public final int getTotalPages() {
    if (getTotalItems() < getPageSize()) {
      return 1;
    } else {
      final int remainder = getTotalItems() % getPageSize();
      final int quotient = getTotalItems() / getPageSize();
      return (0 == remainder) ? quotient : (quotient + 1);
    }
  }

  /**
   * <p>
   * getNextPageIndex.
   * </p>
   *
   * @return a int.
   */
  public final int getNextPageIndex() {
    if (getPageIndex() == getTotalPages()) {
      return getTotalPages();
    } else {
      return getPageIndex() + 1;
    }
  }

  /**
   * <p>
   * getPreviousPageIndex.
   * </p>
   *
   * @return a int.
   */
  public final int getPreviousPageIndex() {
    if (getPageIndex() == 1) {
      return getPageIndex();
    } else {
      return getPageIndex() - 1;
    }
  }

  /**
   * <p>
   * Getter for the field <code>pageIndex</code>.
   * </p>
   *
   * @return a int.
   */
  public final int getPageIndex() {
    return pageIndex;
  }

  /**
   * <p>
   * Getter for the field <code>pageSize</code>.
   * </p>
   *
   * @return a int.
   */
  public final int getPageSize() {
    return pageSize;
  }

  /**
   * <p>
   * Getter for the field <code>items</code>.
   * </p>
   *
   * @return a {@link java.util.List} object.
   */
  public final List<E> getItems() {
    return items;
  }

  /**
   * <p>
   * Getter for the field <code>total</code>.
   * </p>
   *
   * @return a int.
   */
  public final int getTotalItems() {
    return totalItems;
  }

  /** {@inheritDoc} */
  public boolean add(final Object obj) {
    throw new RuntimeException("unsupported add");
  }

  /** {@inheritDoc} */
  public boolean addAll(final Collection<? extends E> datas) {
    throw new RuntimeException("unsupported addAll");
  }

  /**
   * <p>
   * clear.
   * </p>
   */
  public void clear() {
    throw new RuntimeException("unsupported clear");
  }

  /** {@inheritDoc} */
  public boolean contains(final Object obj) {
    return items.contains(obj);
  }

  /** {@inheritDoc} */
  public boolean containsAll(final Collection<?> datas) {
    return items.containsAll(datas);
  }

  /**
   * <p>
   * isEmpty.
   * </p>
   *
   * @return a boolean.
   */
  public boolean isEmpty() {
    return items.isEmpty();
  }

  /**
   * <p>
   * iterator.
   * </p>
   *
   * @return a {@link java.util.Iterator} object.
   */
  public Iterator<E> iterator() {
    return items.iterator();
  }

  /** {@inheritDoc} */
  public boolean remove(final Object obj) {
    throw new RuntimeException("unsupported removeAll");
  }

  /** {@inheritDoc} */
  public boolean removeAll(final Collection<?> datas) {
    throw new RuntimeException("unsupported removeAll");
  }

  /** {@inheritDoc} */
  public boolean retainAll(final Collection<?> datas) {
    throw new RuntimeException("unsupported retailAll");
  }

  /**
   * <p>
   * size.
   * </p>
   *
   * @return a int.
   */
  public int size() {
    return items.size();
  }

  /**
   * <p>
   * toArray.
   * </p>
   *
   * @return an array of {@link java.lang.Object} objects.
   */
  public Object[] toArray() {
    return items.toArray();
  }

  /**
   * <p>
   * toArray.
   * </p>
   *
   * @param datas an array of T objects.
   * @param <T> a T object.
   * @return an array of T objects.
   */
  public <T> T[] toArray(final T[] datas) {
    return items.toArray(datas);
  }

  /**
   * <p>
   * Setter for the field <code>pageIndex</code>.
   * </p>
   *
   * @param pageIndex a int.
   */
  public void setPageIndex(final int pageIndex) {
    this.pageIndex = pageIndex;
  }

  /**
   * <p>
   * Setter for the field <code>pageSize</code>.
   * </p>
   *
   * @param pageSize a int.
   */
  public void setPageSize(final int pageSize) {
    this.pageSize = pageSize;
  }

  /**
   * <p>
   * Setter for the field <code>total</code>.
   * </p>
   *
   * @param total a int.
   */
  public void setTotalItems(final int total) {
    this.totalItems = total;
  }

  /**
   * <p>
   * Setter for the field <code>items</code>.
   * </p>
   *
   * @param dataItems a {@link java.util.List} object.
   */
  public void setItems(final List<E> dataItems) {
    this.items = dataItems;
  }

  // dummy method
  /**
   * <p>
   * hasNext.
   * </p>
   *
   * @return a boolean.
   */
  public boolean hasNext() {
    return getTotalPages() > pageIndex;
  }

  /**
   * <p>
   * hasPrevious.
   * </p>
   *
   * @return a boolean.
   */
  public boolean hasPrevious() {
    return pageIndex > 1;
  }

  /**
   * <p>
   * next.
   * </p>
   *
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  public Page<E> next() {
    return this;
  }

  /**
   * <p>
   * previous.
   * </p>
   *
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  public Page<E> previous() {
    return this;
  }

  /** {@inheritDoc} */
  public Page<E> moveTo(int pageIndex) {
    return this;
  }

  /**
   * <p>
   * add.
   * </p>
   *
   * @param arg0 a int.
   * @param arg1 a E object.
   */
  public void add(int arg0, E arg1) {
    items.add(arg0, arg1);
  }

  /** {@inheritDoc} */
  public E get(int index) {
    return items.get(index);
  }

  /** {@inheritDoc} */
  public int indexOf(Object o) {
    return items.indexOf(o);
  }

  /** {@inheritDoc} */
  public int lastIndexOf(Object o) {
    return items.lastIndexOf(o);
  }

  /**
   * <p>
   * listIterator.
   * </p>
   *
   * @return a {@link java.util.ListIterator} object.
   */
  public ListIterator<E> listIterator() {
    return items.listIterator();
  }

  /** {@inheritDoc} */
  public ListIterator<E> listIterator(int index) {
    return items.listIterator(index);
  }

  /**
   * <p>
   * remove.
   * </p>
   *
   * @param index a int.
   * @return a E object.
   */
  public E remove(int index) {
    return items.remove(index);
  }

  /** {@inheritDoc} */
  public boolean addAll(int arg0, Collection<? extends E> arg1) {
    return items.addAll(arg0, arg1);
  }

  /**
   * <p>
   * set.
   * </p>
   *
   * @param arg0 a int.
   * @param arg1 a E object.
   * @return a E object.
   */
  public E set(int arg0, E arg1) {
    return items.set(arg0, arg1);
  }

  /** {@inheritDoc} */
  public List<E> subList(int fromIndex, int toIndex) {
    return items.subList(fromIndex, toIndex);
  }

}
