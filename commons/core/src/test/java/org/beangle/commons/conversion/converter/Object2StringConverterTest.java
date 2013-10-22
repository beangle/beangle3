package org.beangle.commons.conversion.converter;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

import org.testng.annotations.Test;

/**
 * @author chaostone
 * @since 3.3.7
 */
@Test
public class Object2StringConverterTest {

  public void testConvert() {
    Object2StringConverter converter = new Object2StringConverter();
    assertNull(converter.apply(null), null);
    assertEquals(converter.apply("abc"), "abc");
    assertEquals(converter.apply(new Object[] { 1, 3, "abc" }), "1,3,abc");
    assertEquals(converter.apply(new Object[] { 1, }), "1");
  }
}
