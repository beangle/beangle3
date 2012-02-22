package org.beangle.webtest.temp;

import org.beangle.webtest.better.SeleniumStore;
import org.testng.ITestContext;

import com.thoughtworks.selenium.Selenium;


public class TestBase {

    public TestBase() {
    }
    
    protected Selenium getSelenium(ITestContext context) {
        return SeleniumStore.get(context, this);
    }
}
