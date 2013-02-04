package org.beangle.inject.spring.config;

import org.springframework.core.convert.support.DefaultConversionService;
import org.testng.annotations.Test;

@Test
public class ConvertTest {

  public static void main(String[] args){
    DefaultConversionService conversion = new DefaultConversionService();
    System.out.println(conversion.convert("4.5", Number.class));
  }
}
