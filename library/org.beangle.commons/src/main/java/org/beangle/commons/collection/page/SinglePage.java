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
 */
public class SinglePage<E> implements Page<E> {

	private int pageNo;

	private int pageSize;

	private int total;

	private List<E> items;

	public SinglePage() {
		super();
	}

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

	public final int getFirstPageNo() {
		return 1;
	}

	public final int getMaxPageNo() {
		if (getTotal() < getPageSize()) {
			return 1;
		} else {
			final int remainder = getTotal() % getPageSize();
			final int quotient = getTotal() / getPageSize();
			return (0 == remainder) ? quotient : (quotient + 1);
		}
	}

	public final int getNextPageNo() {
		if (getPageNo() == getMaxPageNo()) {
			return getMaxPageNo();
		} else {
			return getPageNo() + 1;
		}
	}

	public final int getPreviousPageNo() {
		if (getPageNo() == 1) {
			return getPageNo();
		} else {
			return getPageNo() - 1;
		}
	}

	public final int getPageNo() {
		return pageNo;
	}

	public final int getPageSize() {
		return pageSize;
	}

	public final List<E> getItems() {
		return items;
	}

	public final int getTotal() {
		return total;
	}

	public boolean add(final Object obj) {
		throw new RuntimeException("unsupported add");
	}

	public boolean addAll(final Collection<? extends E> datas) {
		throw new RuntimeException("unsupported addAll");
	}

	public void clear() {
		throw new RuntimeException("unsupported clear");
	}

	public boolean contains(final Object obj) {
		return items.contains(obj);
	}

	public boolean containsAll(final Collection<?> datas) {
		return items.containsAll(datas);
	}

	public boolean isEmpty() {
		return items.isEmpty();
	}

	public Iterator<E> iterator() {
		return items.iterator();
	}

	public boolean remove(final Object obj) {
		throw new RuntimeException("unsupported removeAll");
	}

	public boolean removeAll(final Collection<?> datas) {
		throw new RuntimeException("unsupported removeAll");
	}

	public boolean retainAll(final Collection<?> datas) {
		throw new RuntimeException("unsupported retailAll");
	}

	public int size() {
		return items.size();
	}

	public Object[] toArray() {
		return items.toArray();
	}

	public <T> T[] toArray(final T[] datas) {
		return items.toArray(datas);
	}

	public void setPageNo(final int pageNo) {
		this.pageNo = pageNo;
	}

	public void setPageSize(final int pageSize) {
		this.pageSize = pageSize;
	}

	public void setTotal(final int total) {
		this.total = total;
	}

	public void setItems(final List<E> dataItems) {
		this.items = dataItems;
	}

	// dummy method
	public boolean hasNext() {
		return getMaxPageNo() > pageNo;
	}

	public boolean hasPrevious() {
		return pageNo > 1;
	}

	public Page<E> next() {
		return this;
	}

	public Page<E> previous() {
		return this;
	}

	public Page<E> moveTo(int pageNo) {
		return this;
	}

	public void add(int arg0, E arg1) {
		items.add(arg0, arg1);
	}

	public E get(int index) {
		return items.get(index);
	}

	public int indexOf(Object o) {
		return items.indexOf(o);
	}

	public int lastIndexOf(Object o) {
		return items.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return items.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return items.listIterator(index);
	}

	public E remove(int index) {
		return items.remove(index);
	}

	public boolean addAll(int arg0, Collection<? extends E> arg1) {
		return items.addAll(arg0, arg1);
	}

	public E set(int arg0, E arg1) {
		return items.set(arg0, arg1);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return items.subList(fromIndex, toIndex);
	}

}
