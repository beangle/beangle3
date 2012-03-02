package org.beangle.webtest.temp;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestD extends TestBase{

    public TestD() {
    }
    
    @Test
    public void test1(ITestContext context) {
        System.out.println(context.getCurrentXmlTest().getParallel());
    }
    
    @Test
    public void test2() {
    }
}
