/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.page;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class Pages {

	private static final Page<Object> EMPTY_PAGE = new EmptyPage<Object>();

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
