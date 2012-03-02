package org.beangle.webtest.better;

import org.apache.commons.lang.StringUtils;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * 
 * @author qianjia
 *
 */
public class TestNGParam {

    /**
     * get the parameter value from the suite, recursively
     * @param xmlSuite
     * @param name
     * @return
     */
    public static String getParameter(XmlSuite xmlSuite, String name) {
        String value = xmlSuite.getParameter(name);
        if(StringUtils.isNotBlank(value)) {
            return value;
        }
        if(xmlSuite.getParentSuite() != null) {
            return getParameter(xmlSuite.getParentSuite(), name);
        }
        return null;
    }
    
    /**
     * find the parameter value from the test, recursively
     * @see #getParameter(XmlSuite, String) 
     * 
     * @param xmlTest
     * @param name
     * @return
     */
    public static String getParameter(XmlTest xmlTest, String name) {
        String value = xmlTest.getParameter(name);
        if(StringUtils.isNotBlank(value)) {
            return value;
        }
        return getParameter(xmlTest.getSuite(), name);
    }
    
}
