/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.business.action;

import java.util.Date;

import org.beangle.ems.rule.Rule;
import org.beangle.ems.web.action.SecurityActionSupport;
import org.beangle.model.Entity;
import org.beangle.struts2.convention.route.Action;

/**
 * 规则信息
 * 
 * @author chaostone
 * @version $Id: RuleAction.java Jun 27, 2011 7:41:11 PM chaostone $
 */
public class RuleAction extends SecurityActionSupport {

	@Override
	protected String saveAndForward(Entity<?> entity) {
		Rule rule = (Rule) entity;
		if (null == rule.getId()) {
			rule.setCreatedAt(new Date());
			rule.setUpdatedAt(new Date());
		} else {
			rule.setUpdatedAt(new Date());
		}
		entityDao.saveOrUpdate(rule);
		return redirect("search", "info.save.success");
	}

	@Override
	protected String getEntityName() {
		return Rule.class.getName();
	}

	public String params() {
		return redirect(
				Action.to(RuleParamAction.class).method("search")
						.param("ruleParameter.rule.id", getLong("rule.id")), null);
	}

}
