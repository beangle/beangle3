package org.beangle.commons.lang.asm;

import java.lang.reflect.Method;

import org.beangle.commons.lang.testbean.TestBean;

public class BenchmarkTest {
  public static void main(String[] args) throws Exception {
    testReflectAsm();
    testJdkReflect();
  }

  public static void testJdkReflect() throws Exception {
    System.out.print("testJdkReflect...");
    TestBean someObject = new TestBean();
    Method method = TestBean.class.getMethod("setName", String.class);
    for (int i = 0; i < 5; i++) {
      long begin = System.currentTimeMillis();
      for (int j = 0; j < 100000000; j++) {
        method.invoke(someObject, "Unmi");
      }
      System.out.print(System.currentTimeMillis() - begin + " ");
    }
    System.out.println("\n\n");
  }

  public static void testReflectAsm() {
    System.out.print("testReflectAsm...");
    TestBean someObject = new TestBean();
    AccessProxy access = AccessProxy.get(TestBean.class);
//    int methodIndex=ClassInfo.get(TestBean.class).getMethodIndex("setName",  "Unmi");
//    String property="name".intern();
    for (int i = 0; i < 5; i++) {
      long begin = System.currentTimeMillis();
      for (int j = 0; j < 100000000; j++) {
        access.invoke( someObject,"setName", "Unmi");
      }
      System.out.print(System.currentTimeMillis() - begin + " ");
    }
  }
}
