package org.beangle.webtest.better;

import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.thoughtworks.selenium.Selenium;

public interface ISeleniumMaker {

    /**
     * get a Selenium instance for a test suite
     * @param xmlSuite
     * @return
     */
    Selenium make(XmlSuite xmlSuite);
    
    /**
     * get a Selenium instance for a test
     * @param xmlTest
     * @return
     */
    Selenium make(XmlTest xmlTest);
 
    /**
     * 判断suite是否override了parent suite的selenium配置
     * @param xmlSuite
     * @return
     */
    boolean isOverrideConfiguration(XmlSuite xmlSuite);
    
    /**
     * 判断test是否override了suite的selenium配置
     * @param xmlTest
     * @return
     */
    boolean isOverrideConfiguration(XmlTest xmlTest);
}
