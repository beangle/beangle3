/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.dao.example;

import java.util.Map;

import org.beangle.dao.pojo.LongIdEntity;

public interface Employer extends LongIdEntity{

	Name getName();

	ContractInfo getContractInfo();

	Map<Long, Skill> getSkills();
}
