package org.beangle.commons.lang.conversion.converter;

import org.beangle.commons.lang.Assert;
import org.beangle.commons.lang.conversion.Converter;
import org.beangle.commons.lang.conversion.impl.ConverterFactory;

/**
 * Number Converter Factory
 * 
 * @author chaostone
 * @since 3.2.0
 */
public class Number2NumberConverter extends ConverterFactory<Number, Number> {

  public Number2NumberConverter() {
    register(Integer.class, new IntConverter());
  }

  private static class IntConverter implements Converter<Number, Integer> {
    @Override
    public Integer apply(Number number) {
      long value = number.longValue();
      Assert.isTrue(value >= Integer.MIN_VALUE && value <= Integer.MAX_VALUE);
      return new Integer(number.intValue());
    }
  }

}
