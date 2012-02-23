package org.beangle.webtest.better;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.testng.ITestResult;
import org.testng.log4testng.Logger;
import org.testng.xml.XmlTest;

import com.thoughtworks.selenium.Selenium;

/**
 * Selenium instance store
 * 
 * @author qianjia
 *
 */
public class SeleniumStore {

    private static final Logger LOGGER = Logger.getLogger(SeleniumStore.class);
    
    private static final ThreadLocal<Map<String, Selenium>> pathToSeleniums = new ThreadLocal<Map<String, Selenium>>() {
        @Override
        protected Map<String, Selenium> initialValue() {
            return new HashMap<String, Selenium>();
        }
    }; 
    
    private static final Map<String, Set<Selenium>> seleniumsOfClassesParallelMode = new HashMap<String, Set<Selenium>>();
    
    /**
     * bind a certain selenium instance to a path<br>
     * If selenium is null, do nothing
     * @param path
     * @param selenium
     */
    static synchronized void put(String path, Selenium selenium) {
        if(selenium != null) {
            Map<String, Selenium> map = pathToSeleniums.get();
            map.put(path, selenium);
        }
    }
    
    /**
     * 当使用classes, instances多线程模式时，绑定在instance上的selenium只能在test.onFinish的时候关闭，因为每个@Test方法调用结束就关闭是不行的<br>
     * 但是test.onFinish/onStart和instances的@Test方法不在一个thread里，所以不能够通过 {@link #getRecursively(String)}  来获得一个<br>
     * test下所有的classes的instances所绑定的selenium, 因此只能采取这种折中的办法来解决<br>
     * 
     * 对于这种模式下关闭selenium请使用 {@link #closeInClassesParallelMode(String)}
     */
    static void setInClassesParallelMode(ITestResult result, ISeleniumMaker sm) {
        XmlTest xmlTest = result.getTestClass().getXmlTest();
        String instancePath = TestNGPath.getPathToInstance(result);
        
        // 同一个instance内的方法调用是单线程的，所以不能够覆盖原先的selenium
        if(getDirectly(instancePath) == null) {
            LoggerHelper.debug(LOGGER, "Create selenium instance : " + instancePath);
            Selenium selenium = sm.make(xmlTest);
            SeleniumStore.put(instancePath, selenium);
            
            String testPath = TestNGPath.getPath(xmlTest);
            Set<Selenium> seleniums = seleniumsOfClassesParallelMode.get(testPath);
            if(seleniums == null) {
                seleniums = new HashSet<Selenium>();
                seleniumsOfClassesParallelMode.put(testPath, seleniums);
            }
            seleniums.add(selenium);
        }
    }
    
    static void setInMethodsParallelMode(ITestResult result, ISeleniumMaker sm) {
        XmlTest xmlTest = result.getTestClass().getXmlTest();
        String path = TestNGPath.getPathToInstance(result);
        // 避免多个method在一个线程内连续调用产生过多的selenium
        if(getDirectly(path) == null) {
            LoggerHelper.debug(LOGGER,"Create selenium instance : " + path);
            SeleniumStore.put(path, sm.make(xmlTest));
        }
    }
    
    /**
     * 只能对只属于自己的selenium进行关闭，其他都不行
     * @param path
     */
    static void close(String path) {
        Selenium selenium = getDirectly(path);
        if(selenium != null) {
            LoggerHelper.debug(LOGGER, "Close selenium instance : " + path);
            selenium.close();
        }
    }
    
    static void closeInClassesParallelMode(String path) {
        for(Selenium selenium : seleniumsOfClassesParallelMode.get(path)) {
            selenium.close();
            LoggerHelper.debug(LOGGER, "Close selenium instance : " + path);
        }
    }
    
    /**
     * get the current thread selenium, recursively
     * @param path
     * @return
     */
    static Selenium getRecursively(String path) {
        Selenium selenium =  pathToSeleniums.get().get(path);
        if(selenium == null && path.lastIndexOf("/") != -1) {
            return getRecursively(path.substring(0, path.lastIndexOf("/")));
        }
        return selenium;
    }
    
    private static Selenium getDirectly(String path) {
        return pathToSeleniums.get().get(path);
    }

    /**
     * @Test, @Before 从此获取当前的Selenium instance
     * @param context
     * @return
    public static Selenium get(ITestContext context, Object testInstance) {
        XmlTest xmlTest = context.getCurrentXmlTest();
        String parallelMode = xmlTest.getParallel();
        String path = null;
        if(XmlSuite.PARALLEL_TESTS.equals(parallelMode)) {
            path = TestNGPath.getPath(xmlTest);
        } else if (XmlSuite.PARALLEL_CLASSES.equals(parallelMode) || XmlSuite.PARALLEL_INSTANCES.equals(parallelMode)) {
            path = TestNGPath.getPathToInstance(context, testInstance);
        } else if (XmlSuite.PARALLEL_METHODS.equals(parallelMode)) {
            path = TestNGPath.getPathToInstance(context, testInstance);
        } else {
            path = TestNGPath.getPath(xmlTest);
        }
        LoggerHelper.debug(LOGGER,"Retreiving selenium instance : " + path);
        return getDirectly(path);
    }
     */

}
