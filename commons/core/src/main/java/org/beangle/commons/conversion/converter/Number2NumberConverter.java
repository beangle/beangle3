/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
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
package org.beangle.commons.conversion.converter;

import java.math.BigDecimal;
import java.math.BigInteger;

import org.beangle.commons.conversion.Converter;
import org.beangle.commons.conversion.impl.ConverterFactory;

/**
 * Number Converter Factory
 * 
 * @author chaostone
 * @since 3.2.0
 */
public class Number2NumberConverter extends ConverterFactory<Number, Number> {

  public Number2NumberConverter() {
    register(Integer.class, new ShortConverter());
    register(Integer.class, new IntConverter());
    register(Long.class, new LongConverter());
    register(Float.class, new FloatConverter());
    register(Double.class, new DoubleConverter());
    register(BigInteger.class, new BigIntegerConverter());
    register(BigDecimal.class, new BigDecimalConverter());
  }

  private static class ShortConverter implements Converter<Number, Short> {
    @Override
    public Short apply(Number number) {
      return Short.valueOf(number.shortValue());
    }
  }

  private static class IntConverter implements Converter<Number, Integer> {
    @Override
    public Integer apply(Number number) {
      return Integer.valueOf(number.intValue());
    }
  }

  private static class LongConverter implements Converter<Number, Long> {
    @Override
    public Long apply(Number number) {
      return Long.valueOf(number.intValue());
    }
  }

  private static class FloatConverter implements Converter<Number, Float> {
    @Override
    public Float apply(Number number) {
      return Float.valueOf(number.floatValue());
    }
  }

  private static class DoubleConverter implements Converter<Number, Double> {
    @Override
    public Double apply(Number number) {
      return Double.valueOf(number.doubleValue());
    }
  }

  private static class BigIntegerConverter implements Converter<Number, BigInteger> {
    @Override
    public BigInteger apply(Number number) {
      return BigInteger.valueOf(number.longValue());
    }
  }

  private static class BigDecimalConverter implements Converter<Number, BigDecimal> {
    @Override
    public BigDecimal apply(Number number) {
      return new BigDecimal(number.toString());
    }
  }
}
