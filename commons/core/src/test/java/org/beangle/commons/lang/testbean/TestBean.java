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
package org.beangle.commons.lang.testbean;

public class TestBean {
  private Integer id;
  private String name;
  private int intValue;
  TestEnum testEnum;

  private String B;
  private NestedBean nested = new NestedBean();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

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

  public NestedBean getNested() {
    return nested;
  }

  public void setNested(NestedBean nested) {
    this.nested = nested;
  }

  private Boolean AAA;

  public void foo() {

  }

  public int getIntProperty() {
    return 0;
  }

  public int getA() {
    return 0;
  }

  public Boolean getAAA() {
    return AAA;
  }

  public void setAAA(Boolean aAA) {
    AAA = aAA;
  }

  public String getB() {
    return B;
  }

  public void setB(String b) {
    B = b;
  }
  
}
