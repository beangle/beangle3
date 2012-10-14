/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.testbean;

import org.springframework.beans.factory.FactoryBean;

public class ProxyFactoryBean implements FactoryBean<TestDao> {

  private TestDao target;

  public void setTarget(TestDao target) {
    this.target = target;
  }

  public TestDao getObject() throws Exception {
    return target;
  }

  public Class<?> getObjectType() {
    return TestDao.class;
  }

  public boolean isSingleton() {
    return true;
  }

}
