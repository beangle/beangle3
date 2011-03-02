/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.example;

import java.util.Map;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;

import org.beangle.model.pojo.LongIdTimeObject;
@Entity(name = "org.beangle.model.example.Employer")
public class ManagerEmployer extends LongIdTimeObject implements Employer {

	private static final long serialVersionUID = 1L;

	Name name;

	ContractInfo contractInfo;

	@ManyToMany
	@MapKeyColumn(name = "skill_type_id")
	Map<Long, Skill> skills;
	
	@ManyToMany
	Set<Skill> oskills;
	
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

	public Set<Skill> getOskills() {
		return oskills;
	}

	public void setOskills(Set<Skill> oskills) {
		this.oskills = oskills;
	}

}
