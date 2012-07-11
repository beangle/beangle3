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
