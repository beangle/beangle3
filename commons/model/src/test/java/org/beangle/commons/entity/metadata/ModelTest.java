/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.commons.entity.metadata;

import static org.testng.Assert.assertNotNull;

import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.testng.Assert;
import org.testng.annotations.Test;

@Test
public class ModelTest {

  public void testNewInstance() {
    SimpleBean entity = Model.newInstance(SimpleBean.class);
    assertNotNull(entity);
  }

  public void testPopulate() {
    Map<String, Object> params = CollectUtils.newHashMap();
    params.put("contractInfo.id", "");
    ManagerEmployer employer = (ManagerEmployer) Model.getPopulator().populate(new ManagerEmployer(),
        Model.getType(ManagerEmployer.class), params);
    Assert.assertNull(employer.getContractInfo());
  }
}
