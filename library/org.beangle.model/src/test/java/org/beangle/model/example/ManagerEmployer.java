/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.example;

import java.util.Map;

import org.beangle.model.pojo.LongIdObject;

public class ManagerEmployer extends LongIdObject implements Employer {

	Name name;

	ContractInfo contractInfo;

	Map<Long, Skill> skills;

	public Map<Long, Skill> getSkills() {
		return skills;
	}

	public void setSkills(Map<Long, Skill> skills) {
		this.skills = skills;
	}

	public Name getName() {
		return name;
	}

	public void setName(Name name) {
		this.name = name;
	}

	public ContractInfo getContractInfo() {
		return contractInfo;
	}

	public void setContractInfo(ContractInfo contractInfo) {
		this.contractInfo = contractInfo;
	}

}
