package org.beangle.commons.lang.asm;

import java.lang.reflect.Method;

import org.beangle.commons.lang.testbean.TestBean;

public class BenchmarkTest {
  public static void main(String[] args) throws Exception {
    long begin = System.currentTimeMillis();
    TestBean[] data = new TestBean[256];
    int hashcode = 1984801293;
    for (int i = 0; i < 100000000; i++) {
      getData(data, hashcode++, 255);
    }
    System.out.println(System.currentTimeMillis() - begin + " ");
    testReflectAsm();
    testJdkReflect();
  }

  static TestBean getData(TestBean[] data, int hash, int mask) {
    return data[hash & mask];
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
    //final ClassInfo info = ClassInfo.get(TestBean.class);
    //int methodIndex = info.getIndex("setName", "Unmi");
    // String property="name".intern();
    for (int i = 0; i < 5; i++) {
      long begin = System.currentTimeMillis();
      for (int j = 0; j < 100000000; j++) {
        access.invoke(someObject, "setName", "Unmi");
      }
      System.out.print(System.currentTimeMillis() - begin + " ");
    }
  }
}
