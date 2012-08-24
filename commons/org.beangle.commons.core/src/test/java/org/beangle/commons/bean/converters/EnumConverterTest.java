/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.bean.converters;

import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author chaostone
 * @since 3.0.0
 */
@Test
public class EnumConverterTest {

  public void testConvertEnum() throws IllegalAccessException, InvocationTargetException {
    Converters.registerEnum(TestEnum.class);
    BeanUtilsBean beanUtils = new BeanUtilsBean(Converters.Instance);
    TestBean testBean = new TestBean();
    beanUtils.copyProperty(testBean, "testEnum", "Private");
    Assert.assertEquals(testBean.getTestEnum(), TestEnum.Private);
  }
}
