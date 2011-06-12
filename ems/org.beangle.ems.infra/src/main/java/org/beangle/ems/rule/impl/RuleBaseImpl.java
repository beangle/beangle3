/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule.impl;

import java.util.List;

import org.beangle.model.persist.EntityDao;
import org.beangle.model.query.builder.OqlBuilder;
import org.beangle.ems.rule.Rule;
import org.beangle.ems.rule.RuleBase;

public class RuleBaseImpl implements RuleBase {

	private EntityDao entityDao;

	public List<Rule> getRules() {
		OqlBuilder<Rule> query = OqlBuilder.from(Rule.class, "rule");
		query.orderBy("rule.id desc");
		return entityDao.search(query);
	}

	public void setEntityDao(EntityDao entityDao) {
		this.entityDao = entityDao;
	}

}
