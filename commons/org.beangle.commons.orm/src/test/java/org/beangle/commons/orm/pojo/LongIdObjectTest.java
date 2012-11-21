/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.commons.orm.pojo;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.Set;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.orm.example.ContractInfo;
import org.testng.annotations.Test;

/**
 * @author chaostone
 * @version $Id: LongIdObjectTest.java Aug 29, 2011 3:29:40 PM chaostone $
 */
@Test
public class LongIdObjectTest {

  public void testEquals() {
    ContractInfo info1 = new ContractInfo();
    ContractInfo info2 = new ContractInfo();
    ContractInfo info3 = info1;
    assertTrue(info1.equals(info3));
    assertFalse(info1.equals(info2));
    assertTrue(info1.hashCode() == info2.hashCode());

    Set<ContractInfo> infos = CollectUtils.newHashSet();
    infos.add(info1);
    infos.add(info2);
    assertEquals(infos.size(), 2);
    infos.remove(info3);
    assertEquals(infos.size(), 1);
  }
}
