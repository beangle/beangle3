package org.beangle.commons.bean;

import static org.testng.Assert.assertEquals;

import org.beangle.commons.lang.testbean.TestBean;
import org.testng.annotations.Test;

@Test
public class PropertyUtilsTest {

  public static void main(String[] args) {
    TestBean bean = new TestBean();
    PropertyUtils.setProperty(bean, "intValue", 2);
    assertEquals(bean.getIntValue(), 2);
  }
}
