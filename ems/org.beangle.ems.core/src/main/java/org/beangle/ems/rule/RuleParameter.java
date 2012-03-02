/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule;

import java.util.Set;

import org.beangle.dao.pojo.LongIdEntity;

/**
 * 规则对应参数
 * 
 * @author chaostone
 */
public interface RuleParameter extends LongIdEntity {

	public Set<RuleParameter> getChildren();

	public void setChildren(Set<RuleParameter> subRuleParams);

	public RuleParameter getParent();

	public void setParent(RuleParameter superRuleParameter);

	public Rule getRule();

	public void setRule(Rule businessRule);

	public String getName();

	public void setName(String name);

	public String getType();

	public void setType(String type);

	public void setTitle(String title);

	public String getTitle();

}
