/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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

  private int pageNo;

  private int pageSize;

  private int total;

  private List<E> items;

  /**
   * <p>
   * Constructor for SinglePage.
   * </p>
   * 
   * @param <E> a E object.
   */
  public SinglePage() {
    super();
  }

  /**
   * <p>
   * Constructor for SinglePage.
   * </p>
   * 
   * @param pageNo a int.
   * @param pageSize a int.
   * @param total a int.
   * @param dataItems a {@link java.util.List} object.
   */
  public SinglePage(int pageNo, int pageSize, int total, List<E> dataItems) {
    this.items = dataItems;
    if (pageSize < 1) {
      this.pageSize = 2;
    } else {
      this.pageSize = pageSize;
    }
    if (pageNo < 1) {
      this.pageNo = 1;
    } else {
      this.pageNo = pageNo;
    }
    this.total = total;
  }

  /**
   * <p>
   * getFirstPageNo.
   * </p>
   * 
   * @return a int.
   */
  public final int getFirstPageNo() {
    return 1;
  }

  /**
   * <p>
   * getMaxPageNo.
   * </p>
   * 
   * @return a int.
   */
  public final int getMaxPageNo() {
    if (getTotal() < getPageSize()) {
      return 1;
    } else {
      final int remainder = getTotal() % getPageSize();
      final int quotient = getTotal() / getPageSize();
      return (0 == remainder) ? quotient : (quotient + 1);
    }
  }

  /**
   * <p>
   * getNextPageNo.
   * </p>
   * 
   * @return a int.
   */
  public final int getNextPageNo() {
    if (getPageNo() == getMaxPageNo()) {
      return getMaxPageNo();
    } else {
      return getPageNo() + 1;
    }
  }

  /**
   * <p>
   * getPreviousPageNo.
   * </p>
   * 
   * @return a int.
   */
  public final int getPreviousPageNo() {
    if (getPageNo() == 1) {
      return getPageNo();
    } else {
      return getPageNo() - 1;
    }
  }

  /**
   * <p>
   * Getter for the field <code>pageNo</code>.
   * </p>
   * 
   * @return a int.
   */
  public final int getPageNo() {
    return pageNo;
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
  public final int getTotal() {
    return total;
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
   * Setter for the field <code>pageNo</code>.
   * </p>
   * 
   * @param pageNo a int.
   */
  public void setPageNo(final int pageNo) {
    this.pageNo = pageNo;
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
  public void setTotal(final int total) {
    this.total = total;
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
    return getMaxPageNo() > pageNo;
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
  public Page<E> moveTo(int pageNo) {
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
