/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.collection.page;

import java.util.List;

public class PagedList<E> extends PageWapper<E> {

	private final List<E> datas;

	private int pageNo = 0;

	private int maxPageNo;

	private int pageSize;

	public PagedList(List<E> datas, int pageSize) {
		this(datas, new PageLimit(1, pageSize));
	}

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

	public int getMaxPageNo() {
		return maxPageNo;
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotal() {
		return datas.size();
	}

	public final int getNextPageNo() {
		return getPage().getNextPageNo();
	}

	public final int getPreviousPageNo() {
		return getPage().getPreviousPageNo();
	}

	public boolean hasNext() {
		return getPageNo() < getMaxPageNo();
	}

	public boolean hasPrevious() {
		return getPageNo() > 1;
	}

	public Page<E> next() {
		return moveTo(pageNo + 1);
	}

	public Page<E> previous() {
		return moveTo(pageNo - 1);
	}

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
