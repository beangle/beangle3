package org.beangle.commons.lang.asm;

import static org.testng.Assert.assertEquals;

import org.beangle.commons.lang.testbean.TestBean;
import org.testng.annotations.Test;

@Test
public class AccessProxyTest {

  public static void main(String[] args) {
    AccessProxy access = AccessProxy.get(TestBean.class);
    TestBean someObject = new TestBean();
    Object value;

    value = access.invoke(someObject, "getName");
    assertEquals(null, value);
    value = access.invoke(someObject, "setName", "sweet");
    assertEquals(null, value);
    value = access.invoke(someObject, "getName");
    assertEquals("sweet", value);
    value = access.invoke(someObject, "setName", (Object) null);
    assertEquals(null, value);
    value = access.invoke(someObject, "getName");
    assertEquals(null, value);

    value = access.invoke(someObject, "getIntValue");
    assertEquals(0, value);
    value = access.invoke(someObject, "setIntValue", 1234);
    assertEquals(null, value);
    value = access.invoke(someObject, "getIntValue");
    assertEquals(1234, value);

  }

}
