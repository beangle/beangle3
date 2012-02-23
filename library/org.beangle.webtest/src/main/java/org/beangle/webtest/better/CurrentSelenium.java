package org.beangle.webtest.better;

import com.thoughtworks.selenium.Selenium;

public class CurrentSelenium {

    static final ThreadLocal<Selenium> currentSelenium = new ThreadLocal<Selenium>();
    
    public static Selenium get() {
        return currentSelenium.get();
    }
}
