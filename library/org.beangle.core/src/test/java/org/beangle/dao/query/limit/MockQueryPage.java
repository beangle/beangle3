/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.query.limit;

import java.util.ArrayList;
import java.util.List;

import org.beangle.collection.page.Page;
import org.beangle.collection.page.SinglePage;
import org.beangle.dao.query.LimitQuery;

public class MockQueryPage extends AbstractQueryPage<String> {

	public MockQueryPage() {

	}

	public MockQueryPage(LimitQuery<String> query) {
		super(query);
		next();
	}

	public Page<String> moveTo(int pageNo) {
		SinglePage<String> page = new SinglePage<String>();
		page.setPageNo(pageNo);
		page.setPageSize(super.getPageSize());
		List<String> datas = new ArrayList<String>(getPageSize());
		for (int i = 0; i < getPageSize(); i++) {
			datas.add(String.valueOf(i) + " of " + pageNo);
		}
		page.setItems(datas);
		page.setTotal(100);
		setPageData(page);
		return this;
	}

}
