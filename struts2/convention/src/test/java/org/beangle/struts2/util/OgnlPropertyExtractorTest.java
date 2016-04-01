/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.struts2.util;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.lang.time.Stopwatch;
import org.beangle.commons.transfer.exporter.PropertyExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

@Test
public class OgnlPropertyExtractorTest {

  private static final Logger logger = LoggerFactory.getLogger(OgnlPropertyExtractorTest.class);
  
  public void testGet() throws Exception {
    OgnlPropertyExtractor extractor = new OgnlPropertyExtractor();
    Map<String, String> datas = CollectUtils.newHashMap();
    datas.put("name", "211");
    assertEquals("211", extractor.getPropertyValue(datas, "name"));
  }

  public void testGet2() throws Exception {
    OgnlPropertyExtractor extractor = new OgnlPropertyExtractor();
    Map<String, Object> data = CollectUtils.newHashMap();
    Float age = new Float(22);
    data.put("jack", age);
    Map<?, ?>[] datas1 = new Map[] { null };
    Map<?, ?>[] datas = new Map[] { data };
    assertNull(extractor.getPropertyValue(datas1, "[0][\"jack\"]"));
    assertEquals(age, extractor.getPropertyValue(datas, "[0][\"jack\"]"));
  }

  @Test(timeOut = 10000)
  public void testPerformance() throws Exception {
    OgnlPropertyExtractor extractor = new OgnlPropertyExtractor();
    Stopwatch watch = new Stopwatch().start();
    for (int i = 0; i < 1000; i++) {
      extract(extractor);
    }
    logger.debug("1000 extract using" + watch);
  }

  private void extract(PropertyExtractor extractor) throws Exception {
    List<Object> datas = CollectUtils.newArrayList();
    Float age = new Float(22);
    datas.add(age);

    Map<String, Object> person = CollectUtils.newHashMap();
    ManagerEmployer employer1 = new ManagerEmployer();
    person.put("firstName", "jack");
    person.put("employer", employer1);
    datas.add(person);

    ManagerEmployer employer2 = new ManagerEmployer();
    ContractInfo info = new ContractInfo();
    info.setAdd1("add1");
    employer2.setContractInfo(info);
    person.put("employer2", employer2);

    assertEquals(age, extractor.getPropertyValue(datas, "[0]"));
    assertEquals("jack", extractor.getPropertyValue(datas, "[1][\"firstName\"]"));
    assertNull(extractor.getPropertyValue(datas, "[1][\"employer\"].contractInfo.add1"));
    assertEquals("add1", extractor.getPropertyValue(datas, "[1][\"employer2\"].contractInfo.add1"));
  }
}
