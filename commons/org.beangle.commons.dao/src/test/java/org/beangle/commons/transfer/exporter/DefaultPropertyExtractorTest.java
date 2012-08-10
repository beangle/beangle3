/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.transfer.exporter;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.time.StopWatch;
import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.entity.metadata.ContractInfo;
import org.beangle.commons.entity.metadata.ManagerEmployer;
import org.testng.annotations.Test;

@Test
public class DefaultPropertyExtractorTest {

  public void testGet() throws Exception {
    DefaultPropertyExtractor extractor = new DefaultPropertyExtractor();
    Map<String, String> datas = CollectUtils.newHashMap();
    datas.put("name", "211");
    assertEquals("211", extractor.getPropertyValue(datas, "name"));
  }

  public void testGet2() throws Exception {
    DefaultPropertyExtractor extractor = new DefaultPropertyExtractor();
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
    DefaultPropertyExtractor extractor = new DefaultPropertyExtractor();
    StopWatch watch = new StopWatch();
    watch.start();
    for (int i = 0; i < 1000; i++) {
      extract(extractor);
    }
    // System.out.println(watch.getTime());
  }

  @Test(timeOut = 1000)
  public void testPerformance2() throws Exception {
    DefaultPropertyExtractor extractor = new DefaultPropertyExtractor();
    StopWatch watch = new StopWatch();
    watch.start();
    for (int i = 0; i < 1000; i++) {
      extract(extractor);
    }
    // System.out.println(watch.getTime());
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
