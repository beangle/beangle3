package org.beangle.webtest.better;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.testng.ITestNGMethod;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * 
 * @author qianjia
 *
 */
public class TestNGPath {

    public static String getPath(XmlSuite xmlSuite) {
        if(xmlSuite.getParentSuite() != null) {
            return StringUtils.join(new String[]{getPath(xmlSuite.getParentSuite()), xmlSuite.getName()}, '/');
        }
        return StringUtils.join(new String[]{null, xmlSuite.getName()}, '/');
    }
    
    public static String getPath(XmlTest xmlTest) {
        return StringUtils.join(new String[]{getPath(xmlTest.getSuite()), xmlTest.getName()}, '/');
    }
    
    public static String getPath(ITestNGMethod method) {
        return StringUtils.join(new String[]{getPath(method.getXmlTest()), method.getMethodName()}, '/');
    }
    
    public static String getPath(XmlTest xmlTest, Class clazz) {
        return StringUtils.join(new String[]{getPath(xmlTest), clazz.getName()}, '/');
    }
    
    public static String getPath(XmlTest xmlTest, Class clazz, Method method) {
        return StringUtils.join(new String[]{getPath(xmlTest, clazz), method.toString()}, '/');
    }
}
