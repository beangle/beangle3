/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.orm.example;

import org.beangle.commons.entity.pojo.NumberIdTimeObject;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Map;
import java.util.Set;

@Entity(name = "org.beangle.orm.example.Employer")
@DynamicUpdate
public class ManagerEmployer extends NumberIdTimeObject<Integer> implements Employer {

  private static final long serialVersionUID = 1L;

  Name name;

  @ManyToOne(fetch = FetchType.LAZY)
  ContractInfo contractInfo;

  @ManyToMany
  @MapKeyColumn(name = "skill_type_id")
  Map<Long, Skill> skills;

  @ManyToMany
  Set<Skill> oskills;

  @Type(type = "org.beangle.orm.hibernate.udt.IDEnumType")
  Gender gender;

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

  public Gender getGender() {
    return gender;
  }

  public void setGender(Gender gender) {
    this.gender = gender;
  }
}
