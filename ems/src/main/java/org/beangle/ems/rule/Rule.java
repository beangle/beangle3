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
package org.beangle.ems.rule;

import java.util.Set;

import org.beangle.commons.entity.Entity;
import org.beangle.commons.entity.TimeEntity;

/**
 * Rule represent a business constraint
 *
 * @author chaostone
 */
public interface Rule extends Entity<Integer>, TimeEntity {

  Set<RuleParameter> getParams();

  void setParams(Set<RuleParameter> ruleParams);

  boolean isEnabled();

  void setEnabled(boolean enabled);

  String getName();

  void setName(String name);

  String getBusiness();

  void setBusiness(String business);

  String getDescription();

  void setDescription(String description);

  String getFactory();

  void setFactory(String factory);

  String getServiceName();

  void setServiceName(String serviceName);

}
