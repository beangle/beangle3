/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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

/**
 * Convert string to number.
 * 
 * @author chaostone
 * @since 3.2.0
 */
public class String2NumberConverter extends StringConverterFactory<String, Number> {

  public String2NumberConverter() {
    register(Integer.class, new ShortConverter());
    register(Integer.class, new IntConverter());
    register(Long.class, new LongConverter());
    register(Float.class, new FloatConverter());
    register(Double.class, new DoubleConverter());
    register(BigInteger.class, new BigIntegerConverter());
    register(BigDecimal.class, new BigDecimalConverter());
  }

  private static class ShortConverter implements Converter<String, Short> {
    @Override
    public Short apply(String string) {
      try {
        return Short.valueOf(string);
      } catch (NumberFormatException e) {
        return null;
      }
    }
  }

  private static class IntConverter implements Converter<String, Integer> {
    @Override
    public Integer apply(String string) {
      try {
        return Integer.valueOf(string);
      } catch (NumberFormatException e) {
        return null;
      }
    }
  }

  private static class LongConverter implements Converter<String, Long> {
    @Override
    public Long apply(String string) {
      try {
        return Long.valueOf(string);
      } catch (NumberFormatException e) {
        return null;
      }
    }
  }

  private static class FloatConverter implements Converter<String, Float> {
    @Override
    public Float apply(String string) {
      try {
        return Float.valueOf(string);
      } catch (NumberFormatException e) {
        return null;
      }
    }
  }

  private static class DoubleConverter implements Converter<String, Double> {
    @Override
    public Double apply(String string) {
      try {
        return Double.valueOf(string);
      } catch (NumberFormatException e) {
        return null;
      }
    }
  }

  private static class BigIntegerConverter implements Converter<String, BigInteger> {
    @Override
    public BigInteger apply(String string) {
      try {
        return new BigInteger(string);
      } catch (NumberFormatException e) {
        return null;
      }
    }
  }

  private static class BigDecimalConverter implements Converter<String, BigDecimal> {
    @Override
    public BigDecimal apply(String string) {
      try {
        return new BigDecimal(string);
      } catch (NumberFormatException e) {
        return null;
      }
    }
  }

}
