/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.page;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class PageWapper<E> implements Page<E> {

	private Page<E> page;

	public int getFirstPageNo() {
		return 1;
	}

	public Page<E> getPage() {
		return page;
	}

	public void setPage(Page<E> page) {
		this.page = page;
	}

	public List<E> getItems() {
		return page.getItems();
	}

	public Iterator<E> iterator() {
		return page.iterator();
	}

	public boolean add(E obj) {
		return page.add(obj);
	}

	public boolean addAll(Collection<? extends E> datas) {
		return page.addAll(datas);
	}

	public void clear() {
		page.clear();
	}

	public boolean contains(Object obj) {
		return page.contains(obj);
	}

	public boolean containsAll(Collection<?> datas) {
		return page.containsAll(datas);
	}

	public boolean isEmpty() {
		return page.isEmpty();
	}

	public int size() {
		return page.size();
	}

	public Object[] toArray() {
		return page.toArray();
	}

	public <T> T[] toArray(T[] datas) {
		return page.toArray(datas);
	}

	public boolean remove(Object obj) {
		return page.remove(obj);
	}

	public boolean removeAll(Collection<?> datas) {
		return page.removeAll(datas);
	}

	public boolean retainAll(Collection<?> datas) {
		return page.retainAll(datas);
	}

	public void add(int index, E arg1) {
		page.add(index, arg1);
	}

	public boolean addAll(int index, Collection<? extends E> arg1) {
		return page.addAll(index, arg1);
	}

	public E get(int index) {
		return page.get(index);
	}

	public int lastIndexOf(Object o) {
		return page.lastIndexOf(o);
	}

	public ListIterator<E> listIterator() {
		return page.listIterator();
	}

	public ListIterator<E> listIterator(int index) {
		return page.listIterator(index);
	}

	public E remove(int index) {
		return page.remove(index);
	}

	public E set(int index, E arg1) {
		return page.set(index, arg1);
	}

	public List<E> subList(int fromIndex, int toIndex) {
		return page.subList(fromIndex, toIndex);
	}

	public int indexOf(Object o) {
		return page.indexOf(o);
	}
}
