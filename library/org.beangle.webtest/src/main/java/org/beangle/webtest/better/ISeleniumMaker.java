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
     * Determine whether this suite override its parent suite's the selenium configuration
     * @param xmlSuite
     * @return
     */
    boolean isOverrideConfiguration(XmlSuite xmlSuite);
    
    /**
     * Determine whether this test override its suite's the selenium configuration
     * @param xmlTest
     * @return
     */
    boolean isOverrideConfiguration(XmlTest xmlTest);
}
