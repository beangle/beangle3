package org.beangle.webtest.better;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    /**
     * 新建selenium时所关心的参数
     */
    protected Set<String> concernedParams = new HashSet<String>();
    
    private java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
    
    public WebDriverSeleniumMaker() {
        concernedParams.add("selenium.browser");
        concernedParams.add("selenium.baseurl");
    }
    
    @Override
    public Selenium make(XmlSuite xmlSuite) {
        Map<String, String> params = new HashMap<String, String>();
        for(String key : concernedParams) {
            params.put(key, TestNGParam.getParameter(xmlSuite, key));
            params.put(key, TestNGParam.getParameter(xmlSuite, key));
        }
        
        return make(params);
    }

    @Override
    public Selenium make(XmlTest xmlTest) {
        Map<String, String> params = new HashMap<String, String>();
        for(String key : concernedParams) {
            params.put(key, TestNGParam.getParameter(xmlTest, key));
            params.put(key, TestNGParam.getParameter(xmlTest, key));
        }
        return make(params);
    }
    
    protected Selenium make(Map<String, String> params) {
        String browser = params.get("selenium.browser");
        String baseurl = params.get("selenium.baseurl");
        
        if(StringUtils.isBlank(browser) && StringUtils.isBlank(baseurl)) {
            throw new RuntimeException("Please set selenium.baseurl in TestNG configuration file.");
        }
        WebDriver seleniumDriver = new FirefoxDriver();
        seleniumDriver.manage().deleteAllCookies();
        seleniumDriver.manage().window().setPosition(new Point(0, 0));
        seleniumDriver.manage().window().setSize(new Dimension((int)screenSize.getWidth(), (int)screenSize.getHeight()));
        Selenium selenium = new WebDriverBackedSelenium(seleniumDriver, baseurl);
        return selenium;
    }

    @Override
    public boolean isOverrideConfiguration(XmlSuite xmlSuite) {
        for(String key : concernedParams) {
            if(!StringUtils.equals(TestNGParam.getParameter(xmlSuite, key), xmlSuite.getParameter(key))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isOverrideConfiguration(XmlTest xmlTest) {
        for(String key : concernedParams) {
            if(!StringUtils.equals(TestNGParam.getParameter(xmlTest.getSuite(), key), xmlTest.getParameter(key))) {
                return true;
            }
        }
        return false;
    }

}
