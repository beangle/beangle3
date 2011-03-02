/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.example;

import javax.persistence.Entity;

import org.beangle.model.pojo.LongIdObject;

@Entity
public class Skill extends LongIdObject {

	private static final long serialVersionUID = 1L;

	String name;

	SkillType skillType;

	public SkillType getSkillType() {
		return skillType;
	}

	public void setSkillType(SkillType skillType) {
		this.skillType = skillType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
