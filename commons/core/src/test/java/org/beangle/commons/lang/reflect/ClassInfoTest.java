package org.beangle.commons.lang.reflect;

import org.testng.annotations.Test;

@Test
public class ClassInfoTest {

  public void testReaders(){
    assert(null==ClassInfo.get(ClassInfoTest.class).getReader("foo"));
  }

  public void foo() {

  }
}
