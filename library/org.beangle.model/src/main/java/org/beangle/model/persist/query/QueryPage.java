/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.query;

import org.beangle.commons.collection.page.Page;
import org.beangle.commons.collection.page.SinglePage;
import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.LimitQuery;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.model.query.limit.AbstractQueryPage;

public class QueryPage<T> extends AbstractQueryPage<T> {

	private EntityDao entityDao;

	public QueryPage(LimitQuery<T> query, EntityDao entityDao) {
		super(query);
		this.entityDao = entityDao;
		next();
	}

	public QueryPage(OqlBuilder<T> builder, EntityDao entityDao) {
		super((LimitQuery<T>) builder.build());
		this.entityDao = entityDao;
		next();
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

	public Page<T> moveTo(int pageNo) {
		query.getLimit().setPageNo(pageNo);
		SinglePage<T> datas = (SinglePage<T>) entityDao.search(query);
		setPageData(datas);
		return this;
	}
}
