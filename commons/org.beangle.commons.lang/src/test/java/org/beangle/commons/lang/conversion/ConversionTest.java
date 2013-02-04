package org.beangle.commons.lang.conversion;

import org.beangle.commons.lang.conversion.impl.DefaultConversion;
import org.testng.annotations.Test;

@Test
public class ConversionTest {

  public static  void main(String[] args){
    DefaultConversion con=new DefaultConversion();
    System.out.println(con.convert(2.5f, Integer.class));
  }
}
