/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule;

import java.util.Set;

import org.beangle.model.pojo.LongIdTimeEntity;

public interface Rule extends LongIdTimeEntity {

	public static final String ELECTBUSINESS = "elect";

	public static final String AUDITTBUSINESS = "audit";

	public Set<RuleParameter> getParams();

	public void setParams(Set<RuleParameter> ruleParams);

	public boolean isEnabled();

	public void setEnabled(boolean enabled);

	public String getName();

	public void setName(String name);

	public String getBusiness();

	public void setBusiness(String business);

	public String getDescription();

	public void setDescription(String description);

	public String getFactory();

	public void setFactory(String factory);

	public String getServiceName();

	public void setServiceName(String serviceName);

}
