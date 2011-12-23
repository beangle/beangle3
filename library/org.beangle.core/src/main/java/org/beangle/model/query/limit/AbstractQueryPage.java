/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.query.limit;

import java.util.Iterator;

import org.beangle.collection.page.Page;
import org.beangle.collection.page.PageLimit;
import org.beangle.collection.page.PageWapper;
import org.beangle.collection.page.SinglePage;
import org.beangle.model.query.LimitQuery;

/**
 * 基于查询的分页。<br>
 * 当使用或导出大批量数据时，使用者仍以List的方式进行迭代。<br>
 * 该实现则是内部采用分页方式。
 * 
 * @author chaostone
 */
public abstract class AbstractQueryPage<T> extends PageWapper<T> {

	protected int pageNo = 0;

	protected int maxPageNo = 0;

	protected LimitQuery<T> query;

	abstract public Page<T> moveTo(int pageNo);

	public AbstractQueryPage() {
		super();
	}

	public AbstractQueryPage(LimitQuery<T> query) {
		this.query = query;
		if (null != query) {
			if (null == query.getLimit()) {
				query.limit(new PageLimit(pageNo, Page.DEFAULT_PAGE_SIZE));
			} else {
				pageNo = query.getLimit().getPageNo() - 1;
			}
		}
	}

	/**
	 * 按照单个分页数据设置.
	 * 
	 * @param page
	 */
	protected void setPageData(SinglePage<T> page) {
		setPage(page);
		this.pageNo = page.getPageNo();
		this.maxPageNo = page.getMaxPageNo();
	}

	public Page<T> next() {
		return moveTo(pageNo + 1);
	}

	public Page<T> previous() {
		return moveTo(pageNo - 1);
	}

	public boolean hasNext() {
		return maxPageNo > pageNo;
	}

	public boolean hasPrevious() {
		return pageNo > 1;
	}

	public int getFirstPageNo() {
		return 1;
	}

	public int getMaxPageNo() {
		return maxPageNo;
	}

	public int getNextPageNo() {
		return getPage().getNextPageNo();
	}

	public int getPageNo() {
		return pageNo;
	}

	public int getPageSize() {
		return query.getLimit().getPageSize();
	}

	public int getPreviousPageNo() {
		return getPage().getPreviousPageNo();
	}

	public int getTotal() {
		return getPage().getTotal();
	}

	public Iterator<T> iterator() {
		return new PageIterator<T>(this);
	}

}

class PageIterator<T> implements Iterator<T> {

	private final AbstractQueryPage<T> queryPage;

	private int dataIndex;

	public PageIterator(AbstractQueryPage<T> queryPage) {
		this.queryPage = queryPage;
		this.dataIndex = 0;
	}

	public boolean hasNext() {
		return (dataIndex < queryPage.getPage().getItems().size()) || (queryPage.hasNext());
	}

	public T next() {
		if (dataIndex < queryPage.getPage().size()) {
			return queryPage.getPage().getItems().get(dataIndex++);
		} else {
			queryPage.next();
			dataIndex = 0;
			return queryPage.getPage().getItems().get(dataIndex++);
		}
	}

	public void remove() {

	}

}
