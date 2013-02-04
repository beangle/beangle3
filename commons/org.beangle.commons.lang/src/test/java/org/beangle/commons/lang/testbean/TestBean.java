package org.beangle.commons.lang.testbean;

public class TestBean {
  private String name;
  private int intValue;
  TestEnum testEnum;

  public TestEnum getTestEnum() {
    return testEnum;
  }

  public void setTestEnum(TestEnum testEnum) {
    this.testEnum = testEnum;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

//  public void setName(Integer name) {
//    this.name = (null == name) ? null : name.toString();
//  }

  public int getIntValue() {
    return intValue;
  }

  public void setIntValue(int intValue) {
    this.intValue = intValue;
  }

  public String methodWithManyArguments(int i, float f, Integer I, Float F, TestBean c, TestBean c1,
      TestBean c2) {
    return "test";
  }
}
