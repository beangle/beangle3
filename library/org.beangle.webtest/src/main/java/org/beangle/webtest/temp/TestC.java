package org.beangle.webtest.temp;

import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestC extends TestBase{

    public TestC() {
    }
    
    @Test
    public void test1(ITestContext context) {
        System.out.println(getSelenium(context));
    }
    
    @Test
    public void test2() {
    }
}
