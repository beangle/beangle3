/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.business.action;

import org.beangle.dao.Entity;
import org.beangle.dao.query.builder.OqlBuilder;
import org.beangle.ems.rule.RuleParameter;
import org.beangle.ems.web.action.SecurityActionSupport;

/**
 * 规则参数管理
 * 
 * @author chaostone
 * @version $Id: RuleParamAction.java Jun 27, 2011 7:41:33 PM chaostone $
 */
public class RuleParamAction extends SecurityActionSupport {

	@Override
	protected void editSetting(Entity<?> entity) {
		Long ruleId = getLong("ruleParameter.rule.id");
		Long paramId = getLong("ruleParameter.id");

		OqlBuilder<RuleParameter> builder = OqlBuilder.from(RuleParameter.class, "ruleParam");
		if (null != ruleId) builder.where(" ruleParam.rule.id=:ruleId", ruleId);
		if (null != paramId) {
			builder.where(" ruleParam.id<>:paramId", paramId);
		}
		put("ruleParams", entityDao.search(builder));
	}

	@Override
	protected String getEntityName() {
		return RuleParameter.class.getName();
	}

}
