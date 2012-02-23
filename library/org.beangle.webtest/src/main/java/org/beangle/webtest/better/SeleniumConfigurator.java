package org.beangle.webtest.better;

import org.apache.commons.lang.StringUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.log4testng.Logger;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * This class is used to prepare selenium instance for each suite, test, and methods.<br>
 * <br>
 * 
 * <b>Each child suite can override parent suite's selenium configuration, partially or not.</b>
 * <br><br>
 * If a child suite does not override parent suite's selenium configuration, 
 * it will use it's parent suite's selenium instance, 
 * or instantiate a new selenium instance by parent suite's selenium configuration when testng is running in suite parallel mode<br>
 * <br>
 * If a child suite override parent suite's selenium configuration, partially or not,
 * it will use its own selenium instance, whether testng is running in suite parallel mode or not.
 * <br>
 * <br>
 * <b>Each test can override suite's selenium configuration</b>
 * <br><br>
 * If a test does not override suite's selenium configuration,
 * it will use the suite's selenium instance, 
 * or instantiate a new selenium instance by suite's selenium configuration when testng is running in test parallel mode<br>
 * <br>
 * If a test override suite's selenium configuration, partially or not,
 * it will use its own selenium instance, whether testng is running in test parallel mode or not. 
 * <br><br>
 * <b>If testng is running in methods parallel mode, each method invocation will create a new selenium instance</b>
 * <br><br>
 * <h2>Binding path</h2>
 * single thread mode: /parentSuiteName/.../suiteName<br>
 * tests parallel mode : /parentSuiteName/.../suiteName/testName<br>
 * classes/instances parallel mode : /parentSuiteName/.../suiteName/testName/className/instance<br>
 * methods parallel mode : /parentSuiteName/.../suiteName/testName/className/instance<br>
 * @see SeleniumStore
 * @author qianjia
 *
 */
public class SeleniumConfigurator implements ISuiteListener, ITestListener, IInvokedMethodListener {

    private static final Logger LOGGER = Logger.getLogger(SeleniumConfigurator.class);
            
    private ISeleniumMaker sm = new WebDriverSeleniumMaker();
    
    ///////////////////////////
    // suite part
    ///////////////////////////
    @Override
    public void onStart(ISuite suite) {
        // invoked after <suite> element is instantiated
        XmlSuite xmlSuite = suite.getXmlSuite();
        String parallelMode = xmlSuite.getParallel();
        LoggerHelper.debug(LOGGER, "suite.onStart : " + suite.getName() + ", parallel mode=" + parallelMode);

        // 如果是单线程模式
        if(XmlSuite.DEFAULT_PARALLEL.equals(parallelMode)) {
            if((xmlSuite.getParentSuite() == null) 
                // 如果child suite override了parent suite的某部分或全部selenium配置，那么就应该新建一个
                || (xmlSuite.getParentSuite() != null && sm.isOverrideConfiguration(xmlSuite))) {
                String path = TestNGPath.getPath(xmlSuite);
                LoggerHelper.debug(LOGGER, "suite.onStart : create Selenium Instance : " + path);
                SeleniumStore.put(path, sm.make(xmlSuite));
            }
        } else {
            // 如果是多线程模式，那么不论是哪种模式，suite上绑定一个selenium都是没有意义的
        }
    }
    
    @Override
    public void onFinish(ISuite suite) {
        LoggerHelper.debug(LOGGER, "suite.onFinish : " + suite.getName());
        // invoked after <suite> is finished
        XmlSuite xmlSuite = suite.getXmlSuite();
        String path = TestNGPath.getPath(xmlSuite);
        SeleniumStore.close(path);
    }

    ///////////////////////////
    // test part
    ///////////////////////////
    @Override
    public void onStart(ITestContext context) {
        // invoked after all the test classes in the <test> element is instantiated
        XmlTest xmlTest = context.getCurrentXmlTest();
        String parallelMode = context.getCurrentXmlTest().getParallel();
        String path = TestNGPath.getPath(xmlTest);
        
        LoggerHelper.debug(LOGGER, "test.onStart : " + xmlTest.getName());
        if(XmlSuite.PARALLEL_TESTS.equals(parallelMode)) {
            // 如果是test级别的多线程模式，那么每个<test>都应anObject该有一个selenium
            LoggerHelper.debug(LOGGER, "test.onStart : create Selenium Instance : " + path);
            SeleniumStore.put(path, sm.make(xmlTest));
        } else if (XmlSuite.PARALLEL_CLASSES.equals(parallelMode) || XmlSuite.PARALLEL_INSTANCES.equals(parallelMode)) {
            // 如果是class/instance级别的多线程模式，那么在某个test上绑定一个selenium是没有意义的
        } else if (XmlSuite.PARALLEL_METHODS.equals(parallelMode)) {
            // 如果是method级别的多线程模式，那么在某个test上绑定一个selenium是没有意义的
        } else {
            // 单线程模式下
            // 如果test override了suite的某部分或全部selenium配置，那么就应该新建一个
            if(sm.isOverrideConfiguration(xmlTest)) {
                LoggerHelper.debug(LOGGER, "test.onStart : create Selenium Instance for override : " + path);
                SeleniumStore.put(path, sm.make(xmlTest));
            }
        }
    }
    
    
    @Override
    public void onFinish(ITestContext context) {
        // invoked after all the test classes in the <test> element is tested 
        XmlTest xmlTest = context.getCurrentXmlTest();
        LoggerHelper.debug(LOGGER, "test.onFinish : " + xmlTest.getName());
        // 如果找到了test级别的selenium，那么就可以断定使用的是test级别的多线程模式
        String parallelMode = context.getCurrentXmlTest().getParallel();
        String path = TestNGPath.getPath(xmlTest);
        if(XmlSuite.PARALLEL_TESTS.equals(parallelMode)) {
            SeleniumStore.close(path);
        } else if (XmlSuite.PARALLEL_CLASSES.equals(parallelMode)) {
            SeleniumStore.closeInClassesParallelMode(path);
        } else if (XmlSuite.PARALLEL_INSTANCES.equals(parallelMode)) {
            SeleniumStore.closeInClassesParallelMode(path);
        } else if(XmlSuite.PARALLEL_METHODS.equals(parallelMode)) {
        } else {
            // 如果是单线程模式，那么就有可能test override了suite的selenium配置造成新建了一个selenium
            // 那么这时就需要关掉这个selenium
            if(sm.isOverrideConfiguration(xmlTest)) {
                SeleniumStore.close(path);
            }
        }
    }
    
    ///////////////////////////
    // test method part
    ///////////////////////////
    @Override
    public void onTestStart(ITestResult result) {
        LoggerHelper.debug(LOGGER, "testClass.onTestStart : " + result.getMethod().getConstructorOrMethod().getMethod().toString());
        // invoked after a @Test annotated method invoked and also after IInvokedMethodListener.beforeInvocation
        onTestOrConfigurationMethodStart(result);
        
        String path = TestNGPath.getPathToInstance(result);
        CurrentSelenium.currentSelenium.set(SeleniumStore.getRecursively(path));
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        LoggerHelper.debug(LOGGER, "testClass.onTestSuccess : " + result.getMethod().getConstructorOrMethod().getMethod().toString());
        // invoked after a @Test annotated method invoked and also after IInvokedMethodListener.afterInvocation
        onTestOrConfigurationMethodFinish(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        LoggerHelper.debug(LOGGER, "testClass.onTestFailedButWithinSuccessPercentage : " + result.getMethod().getConstructorOrMethod().getMethod().toString());
        // invoked after a @Test annotated method invoked and also after IInvokedMethodListener.afterInvocation
        onTestOrConfigurationMethodFinish(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        LoggerHelper.debug(LOGGER, "testClass.onTestFailure : " + result.getMethod().getConstructorOrMethod().getMethod().toString());
        // invoked after a @Test annotated method invoked and also after IInvokedMethodListener.afterInvocation
        onTestOrConfigurationMethodFinish(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        LoggerHelper.debug(LOGGER, "testClass.onTestSkipped : " + result.getMethod().getConstructorOrMethod().getMethod().toString());
        // invoked after a @Test annotated method invoked and also after IInvokedMethodListener.afterInvocation
        onTestOrConfigurationMethodFinish(result);
    }

    private void onTestOrConfigurationMethodStart(ITestResult result) {
        // 实际上还没看出classes和instances级别的多线程模式有什么区别
        String parallelMode = result.getTestClass().getXmlTest().getParallel();
        if(XmlSuite.PARALLEL_TESTS.equals(parallelMode)) {
            // 如果是test级别的多线程模式，什么都不用做，因为一个test内的所有class共享一个selenium instance
        } else if (XmlSuite.PARALLEL_CLASSES.equals(parallelMode) || XmlSuite.PARALLEL_INSTANCES.equals(parallelMode)) {
            // 如果是class/instance级别的多线程模式，绑定selenium instance到test class instance上去
            SeleniumStore.setInClassesParallelMode(result, sm);
        } else if(XmlSuite.PARALLEL_METHODS.equals(parallelMode)) {
            // 如果是method级别的多线程模式，绑定selenium instance到test class instance上去
            // 之所以这样做可以，是因为method级别的多线程模式开启后，每一个method(除了configration method)调用，
            // 都会开启一个新的thread，所以不用担心method和method之间的selenium instance会冲突
            SeleniumStore.setInMethodsParallelMode(result, sm);
        } else {
            // 如果是单线程模式，什么都不用做
        }
    }
    
    private void onTestOrConfigurationMethodFinish(ITestResult result) {
        String parallelMode = result.getTestClass().getXmlTest().getParallel();
        String path = null;
        if(XmlSuite.PARALLEL_TESTS.equals(parallelMode)) {
            // tests 模式的，给test去做
        } else if (XmlSuite.PARALLEL_CLASSES.equals(parallelMode) || XmlSuite.PARALLEL_INSTANCES.equals(parallelMode)) {
            // classes/instances 模式的，给test去做
        } else if(XmlSuite.PARALLEL_METHODS.equals(parallelMode)) {
            path = TestNGPath.getPathToInstance(result);
            SeleniumStore.close(path);
        } else {
            // 如果是单线程模式，给suite去做
        }
    }
    
    
    ///////////////////////////
    // method part
    ///////////////////////////
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // 如果是@Before @After之类的方法，那么需要初始化selenium instance因为可能这些方法内也需要selenium
        // 但是不必担心会在没有@Test方法的情况下调用本方法，因为如果没有@Test方法，testng压根就不执行了
        if(method.isConfigurationMethod()) {
            LoggerHelper.debug(LOGGER, "testClass.beforeConfigurationMethod : " + method.getTestMethod().getConstructorOrMethod().getMethod().toString());
            onTestOrConfigurationMethodStart(testResult);
        }
    }
    
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    
}
