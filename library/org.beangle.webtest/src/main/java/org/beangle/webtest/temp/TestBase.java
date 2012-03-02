package org.beangle.webtest.temp;

import org.beangle.webtest.better.CurrentSelenium;

import com.thoughtworks.selenium.Selenium;


public class TestBase {

    public TestBase() {
    }
    
    protected Selenium selenium() {
        return CurrentSelenium.get();
    }
}
