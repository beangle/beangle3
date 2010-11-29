/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.rule;

import java.util.Set;

import org.beangle.model.pojo.LongIdEntity;

/**
 * 规则对应参数
 * 
 * @author chaostone
 */
public interface Parameter extends LongIdEntity {

	public Set<Parameter> getChildren();

	public void setChildren(Set<Parameter> subRuleParams);

	public Parameter getParent();

	public void setParent(Parameter superRuleParameter);

	public Rule getRule();

	public void setRule(Rule businessRule);

	public String getName();

	public void setName(String name);

	public String getType();

	public void setType(String type);

}
