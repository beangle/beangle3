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

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * <p>
 * Abstract PageWapper class.
 * </p>
 * 
 * @author chaostone
 * @version $Id: $
 */
public abstract class PageWapper<E> implements Page<E> {

  private Page<E> page;

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
   * Getter for the field <code>page</code>.
   * </p>
   * 
   * @return a {@link org.beangle.commons.collection.page.Page} object.
   */
  public Page<E> getPage() {
    return page;
  }

  /**
   * <p>
   * Setter for the field <code>page</code>.
   * </p>
   * 
   * @param page a {@link org.beangle.commons.collection.page.Page} object.
   */
  public void setPage(Page<E> page) {
    this.page = page;
  }

  /**
   * <p>
   * getItems.
   * </p>
   * 
   * @return a {@link java.util.List} object.
   */
  public List<E> getItems() {
    return page.getItems();
  }

  /**
   * <p>
   * iterator.
   * </p>
   * 
   * @return a {@link java.util.Iterator} object.
   */
  public Iterator<E> iterator() {
    return page.iterator();
  }

  /**
   * <p>
   * add.
   * </p>
   * 
   * @param obj a E object.
   * @return a boolean.
   */
  public boolean add(E obj) {
    return page.add(obj);
  }

  /** {@inheritDoc} */
  public boolean addAll(Collection<? extends E> datas) {
    return page.addAll(datas);
  }

  /**
   * <p>
   * clear.
   * </p>
   */
  public void clear() {
    page.clear();
  }

  /** {@inheritDoc} */
  public boolean contains(Object obj) {
    return page.contains(obj);
  }

  /** {@inheritDoc} */
  public boolean containsAll(Collection<?> datas) {
    return page.containsAll(datas);
  }

  /**
   * <p>
   * isEmpty.
   * </p>
   * 
   * @return a boolean.
   */
  public boolean isEmpty() {
    return page.isEmpty();
  }

  /**
   * <p>
   * size.
   * </p>
   * 
   * @return a int.
   */
  public int size() {
    return page.size();
  }

  /**
   * <p>
   * toArray.
   * </p>
   * 
   * @return an array of {@link java.lang.Object} objects.
   */
  public Object[] toArray() {
    return page.toArray();
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
  public <T> T[] toArray(T[] datas) {
    return page.toArray(datas);
  }

  /** {@inheritDoc} */
  public boolean remove(Object obj) {
    return page.remove(obj);
  }

  /** {@inheritDoc} */
  public boolean removeAll(Collection<?> datas) {
    return page.removeAll(datas);
  }

  /** {@inheritDoc} */
  public boolean retainAll(Collection<?> datas) {
    return page.retainAll(datas);
  }

  /**
   * <p>
   * add.
   * </p>
   * 
   * @param index a int.
   * @param arg1 a E object.
   */
  public void add(int index, E arg1) {
    page.add(index, arg1);
  }

  /** {@inheritDoc} */
  public boolean addAll(int index, Collection<? extends E> arg1) {
    return page.addAll(index, arg1);
  }

  /** {@inheritDoc} */
  public E get(int index) {
    return page.get(index);
  }

  /** {@inheritDoc} */
  public int lastIndexOf(Object o) {
    return page.lastIndexOf(o);
  }

  /**
   * <p>
   * listIterator.
   * </p>
   * 
   * @return a {@link java.util.ListIterator} object.
   */
  public ListIterator<E> listIterator() {
    return page.listIterator();
  }

  /** {@inheritDoc} */
  public ListIterator<E> listIterator(int index) {
    return page.listIterator(index);
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
    return page.remove(index);
  }

  /**
   * <p>
   * set.
   * </p>
   * 
   * @param index a int.
   * @param arg1 a E object.
   * @return a E object.
   */
  public E set(int index, E arg1) {
    return page.set(index, arg1);
  }

  /** {@inheritDoc} */
  public List<E> subList(int fromIndex, int toIndex) {
    return page.subList(fromIndex, toIndex);
  }

  /** {@inheritDoc} */
  public int indexOf(Object o) {
    return page.indexOf(o);
  }
}
