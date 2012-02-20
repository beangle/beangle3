package org.beangle.webtest.better;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.thoughtworks.selenium.Selenium;

public class WebDriverSeleniumMaker implements ISeleniumMaker {

    @Override
    public Selenium make(XmlSuite xmlSuite) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("selenium.browser", TestNGParam.getParameter(xmlSuite, "selenium.browser"));
        params.put("selenium.baseurl", TestNGParam.getParameter(xmlSuite, "selenium.baseurl"));
        
        return makeSelenium(params);
    }

    @Override
    public Selenium make(XmlTest xmlTest) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("selenium.browser", TestNGParam.getParameter(xmlTest, "selenium.browser"));
        params.put("selenium.baseurl", TestNGParam.getParameter(xmlTest, "selenium.baseurl"));
        
        return makeSelenium(params);
    }
    
    protected Selenium makeSelenium(Map<String, String> params) {
        String browser = params.get("selenium.browser");
        String baseurl = params.get("selenium.baseurl");
        
        WebDriver seleniumDriver = new FirefoxDriver();
        seleniumDriver.manage().deleteAllCookies();
        seleniumDriver.manage().window().setPosition(new Point(0, 0));
        // maximize browser size
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        seleniumDriver.manage().window().setSize(new Dimension((int)screenSize.getWidth(), (int)screenSize.getHeight()));
        Selenium selenium = new WebDriverBackedSelenium(seleniumDriver, baseurl);
        return selenium;
    }

    @Override
    public boolean isOverrideConfiguration(XmlSuite xmlSuite) {
        if(!StringUtils.equals(TestNGParam.getParameter(xmlSuite, "selenium.browser"), xmlSuite.getParameter("selenium.browser"))) {
            return true;
        };
        if(!StringUtils.equals(TestNGParam.getParameter(xmlSuite, "selenium.baseurl"), xmlSuite.getParameter("selenium.baseurl"))) {
            return true;
        };
        return false;
    }

    @Override
    public boolean isOverrideConfiguration(XmlTest xmlTest) {
        if(!StringUtils.equals(TestNGParam.getParameter(xmlTest, "selenium.browser"), xmlTest.getParameter("selenium.browser"))) {
            return true;
        };
        if(!StringUtils.equals(TestNGParam.getParameter(xmlTest, "selenium.baseurl"), xmlTest.getParameter("selenium.baseurl"))) {
            return true;
        };
        return false;
    }

}
