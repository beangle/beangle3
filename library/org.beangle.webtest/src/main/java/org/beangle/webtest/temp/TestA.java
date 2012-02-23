package org.beangle.webtest.temp;

import org.testng.annotations.Test;

public class TestA extends TestBase {

    public TestA() {
    }

    @Test
    public void test1() {
        selenium().open("/");
        selenium().type("xpath=//input[@type='text']", "selenium");
        selenium().click("xpath=//input[@type='submit' and @value='Google 搜索']");
    }
    
    @Test
    public void test2() {
    }
}
