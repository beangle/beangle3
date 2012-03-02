package org.beangle.webtest.better;

import org.apache.commons.lang.StringUtils;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * Get the test instance's path, the structure of the path is:<br>
 * 
 * <code>/parentSuiteName/.../suiteName/testName/className/instanceName/methodName</code>
 * 
 * @author qianjia
 *
 */
public class TestNGPath {

    /**
     * return the path to suite<br>
     * <code>/parentSuiteName/.../suiteName</code>
     * @param xmlSuite
     * @return
     */
    public static String getPath(XmlSuite xmlSuite) {
        if(xmlSuite.getParentSuite() != null) {
            return StringUtils.join(new String[]{getPath(xmlSuite.getParentSuite()), xmlSuite.getName()}, '/');
        }
        return StringUtils.join(new String[]{null, xmlSuite.getName()}, '/');
    }
    
    /**
     * return the path to test<br>
     * <code>/parentSuiteName/.../suiteName/testName</code>
     * @param xmlTest
     * @return
     */
    public static String getPath(XmlTest xmlTest) {
        return StringUtils.join(new String[]{getPath(xmlTest.getSuite()), xmlTest.getName()}, '/');
    }
    
    /**
     * return the path to 
     * @param context
     * @return
     */
    public static String getPathToClass(ITestResult testResult) {
        return StringUtils.join(new String[] {
                    getPath(testResult.getTestClass().getXmlTest()),
                    testResult.getTestClass().getName()}, '/');
    }
    
    
    /**
     * return the path to instance<br>
     * <code>/parentSuiteName/.../suiteName/testName/className/instanceName</code>
     * @param method
     * @return
     */
    public static String getPathToInstance(ITestResult testResult) {
        return StringUtils.join(
                new String[] {
                    getPath(testResult.getTestClass().getXmlTest()),
                    testResult.getInstance().getClass().getName(),
                    testResult.getInstance().toString(),
//                    testResult.getMethod().getConstructorOrMethod().getMethod().toString()
                }, '/');
    }
    
}
