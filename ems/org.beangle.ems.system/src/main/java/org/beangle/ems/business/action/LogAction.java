/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.business.action;

import org.apache.commons.lang.StringUtils;
import org.beangle.ems.log.BusinessLog;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.query.QueryBuilder;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.struts2.helper.QueryHelper;

/**
 * 业务日志相应类
 * 
 * @author chaostone
 * @version $Id: LogAction.java Jun 27, 2011 7:34:59 PM chaostone $
 */
public class LogAction extends SecurityActionSupport {

	@Override
	protected String getEntityName() {
		return BusinessLog.class.getName();
	}

	@Override
	protected QueryBuilder<?> getQueryBuilder() {
		OqlBuilder<BusinessLog> builder = OqlBuilder.from(BusinessLog.class, "log");
		populateConditions(builder);
		QueryHelper.addDateIntervalCondition(builder, "operateAt", "beginDate", "endDate");
		builder.limit(getPageLimit());
		String orderBy = get("orderBy");
		if (StringUtils.isEmpty(orderBy)) {
			orderBy = "log.operateAt desc";
		}
		builder.orderBy(orderBy);
		return builder;
	}

}
