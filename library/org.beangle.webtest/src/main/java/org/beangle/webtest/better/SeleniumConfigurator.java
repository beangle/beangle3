package org.beangle.webtest.better;

import java.text.MessageFormat;
import java.util.Date;
import java.util.Formatter;

import org.apache.commons.lang.StringUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.thoughtworks.selenium.Selenium;

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
 *
 * @author qianjia
 *
 */
public class SeleniumConfigurator implements ISuiteListener, ITestListener, IInvokedMethodListener {

    private ISeleniumMaker sm = new WebDriverSeleniumMaker();
    
    private static final String PARALLEL_MODE_TEST = "tests";
    private static final String PARALLEL_MODE_CLASS = "classes";
    private static final String PARALLEL_MODE_INSTANCES = "instances";
    private static final String PARALLEL_MODE_METHOD = "methods";
    
    ///////////////////////////
    // suite part
    ///////////////////////////
    @Override
    public void onStart(ISuite suite) {
        // invoked after <suite> element is instantiated
        XmlSuite xmlSuite = suite.getXmlSuite();
        String parallelMode = xmlSuite.getParallel();
        log("suite.onStart : " + suite.getName() + ", parallel mode=" + parallelMode);

        // 如果没有使用多线程模式
        if(StringUtils.isBlank(parallelMode)) {
            // 如果child suite override了parent suite的某部分或全部selenium配置，那么就应该新建一个
            if(sm.isOverrideConfiguration(xmlSuite)) {
                String path = TestNGPath.getPath(xmlSuite);
                log("suite.onStart : create Selenium Instance : " + path);
                SeleniumStore.set(path, sm.make(xmlSuite));
            }
        } else {
            // 如果是多线程模式，那么不论是哪种模式，suite上绑定一个selenium都是没有意义的
        }
    }
    
    @Override
    public void onFinish(ISuite suite) {
        log("suite.onFinish : " + suite.getName());
        // invoked after <suite> is finished
        XmlSuite xmlSuite = suite.getXmlSuite();
        String path = TestNGPath.getPath(xmlSuite);
        Selenium selenium = SeleniumStore.getNotRecursively(path);
        // 只能对只属于自己的selenium进行关闭，其他都不行
        if(selenium != null) {
            log("suite.onFinish : close Selenium Instance : " + path);
            selenium.close();   
        }
    }

    ///////////////////////////
    // test part
    ///////////////////////////
    @Override
    public void onStart(ITestContext context) {
        // invoked after all the test classes in the <test> element is instantiated
        XmlTest xmlTest = context.getCurrentXmlTest();
        String parallelMode = context.getSuite().getXmlSuite().getParallel();
        
        log("test.onStart : " + xmlTest.getName());
        if(PARALLEL_MODE_TEST.equals(parallelMode)) {
            // 如果是test级别的多线程模式，那么每个<test>都应anObject该有一个selenium
            String path = TestNGPath.getPath(xmlTest);
            log("test.onStart : create Selenium Instance : " + path);
            SeleniumStore.set(path, sm.make(xmlTest));
        } else if (PARALLEL_MODE_CLASS.equals(parallelMode)) {
            // 如果是class级别的多线程模式，那么在某个test上绑定一个selenium是没有意义的
        } else if (PARALLEL_MODE_INSTANCES.equals(parallelMode)) {
            // 如果是instance级别的多线程模式，那么在某个test上绑定一个selenium是没有意义的
        } else if (PARALLEL_MODE_METHOD.equals(parallelMode)) {
            // 如果是method级别的多线程模式，那么在某个test上绑定一个selenium是没有意义的
        } else {
            // 单线程模式下
            // 如果test override了suite的某部分或全部selenium配置，那么就应该新建一个
            if(sm.isOverrideConfiguration(xmlTest)) {
                String path = TestNGPath.getPath(xmlTest);
                log("test.onStart : create Selenium Instance for override : " + path);
                SeleniumStore.set(path, sm.make(xmlTest));
            }
        }
    }
    
    
    @Override
    public void onFinish(ITestContext context) {
        // invoked after all the test classes in the <test> element is tested 
        XmlTest xmlTest = context.getCurrentXmlTest();
        log("test.onFinish : " + xmlTest.getName());
        
        String path = TestNGPath.getPath(xmlTest);
        Selenium selenium = SeleniumStore.getNotRecursively(path);
        // 如果找到了test级别的selenium，那么就可以断定使用的是test级别的多线程模式
        if(selenium != null) {
            log("suite.onFinish : close Selenium Instance : " + path);
            selenium.close();   
        }
    }
    
    ///////////////////////////
    // test method part
    ///////////////////////////
    @Override
    public void onTestStart(ITestResult result) {
        log("testClass.onTestStart : " + result.getMethod().getConstructorOrMethod().getMethod().toString());
        // invoked after a @Test annotated method invoked and also after IInvokedMethodListener.beforeInvocation
        onTestMethodOrConfigurationMethodStart(result);
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        log("testClass.onTestSuccess : " + result.getMethod().getConstructorOrMethod().getMethod().toString());
        // invoked after a @Test annotated method invoked and also after IInvokedMethodListener.afterInvocation
        onTestMethodOrConfigurationMethodFinish(result);
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        log("testClass.onTestFailedButWithinSuccessPercentage : " + result.getMethod().getConstructorOrMethod().getMethod().toString());
        // invoked after a @Test annotated method invoked and also after IInvokedMethodListener.afterInvocation
        onTestMethodOrConfigurationMethodFinish(result);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        log("testClass.onTestFailure : " + result.getMethod().getConstructorOrMethod().getMethod().toString());
        // invoked after a @Test annotated method invoked and also after IInvokedMethodListener.afterInvocation
        onTestMethodOrConfigurationMethodFinish(result);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log("testClass.onTestSkipped : " + result.getMethod().getConstructorOrMethod().getMethod().toString());
        // invoked after a @Test annotated method invoked and also after IInvokedMethodListener.afterInvocation
        onTestMethodOrConfigurationMethodFinish(result);
    }

    private void onTestMethodOrConfigurationMethodStart(ITestResult result) {
        // 实际上还没看出classes和instances级别的多线程模式有什么区别
        String parallelMode = result.getTestClass().getXmlTest().getSuite().getParallel();
        if(PARALLEL_MODE_TEST.equals(parallelMode)) {
            // 如果是test级别的多线程模式，什么都不用做，因为一个test内的所有class共享一个selenium instance
        } else if (PARALLEL_MODE_CLASS.equals(parallelMode)) {
            // 如果是class级别的多线程模式，绑定selenium instance到test class instance上去
            XmlTest xmlTest = result.getTestClass().getXmlTest();
            String path = TestNGPath.getPathToInstance(result);
            // 同一个instance内的方法调用是单线程的
            if(SeleniumStore.getNotRecursively(path) == null) {
                log("testClass.onTestStart : create Selenium Instance : " + path);
                SeleniumStore.set(path, sm.make(xmlTest));
            }
        } else if (PARALLEL_MODE_INSTANCES.equals(parallelMode)) {
            // 如果是instance级别的多线程模式，绑定selenium instance到test class instance上去
            XmlTest xmlTest = result.getTestClass().getXmlTest();
            String path = TestNGPath.getPathToInstance(result);
            // 同一个instance内的方法调用是单线程的
            if(SeleniumStore.getNotRecursively(path) == null) {
                log("testClass.onTestStart : create Selenium Instance : " + path);
                SeleniumStore.set(path, sm.make(xmlTest));
            }
        }else if(PARALLEL_MODE_METHOD.equals(parallelMode)) {
            // 如果是method级别的多线程模式，绑定selenium instance到test class instance上去
            // 之所以这样做可以，是因为method级别的多线程模式开启后，每一个method(除了configration method)调用，
            // 都会开启一个新的thread，所以不用担心method和method之间的selenium instance会冲突
            XmlTest xmlTest = result.getTestClass().getXmlTest();
            // 每一个method已经通过thread隔离开来
            String path = TestNGPath.getPathToInstance(result);
            if(SeleniumStore.getNotRecursively(path) == null) {
                log("testClass.onTestStart : create Selenium Instance : " + path);
                SeleniumStore.set(path, sm.make(xmlTest));
            }
        } else {
            // 如果是单线程模式，什么都不用做
        }
    }
    
    private void onTestMethodOrConfigurationMethodFinish(ITestResult result) {
        String parallelMode = result.getTestClass().getXmlTest().getSuite().getParallel();
        Selenium selenium = null;
        String path = null;
        if(PARALLEL_MODE_TEST.equals(parallelMode)) {
        } else if (PARALLEL_MODE_CLASS.equals(parallelMode)) {
            path = TestNGPath.getPathToInstance(result);
            selenium = SeleniumStore.getNotRecursively(path);
        } else if (PARALLEL_MODE_INSTANCES.equals(parallelMode)) {
            path = TestNGPath.getPathToInstance(result);
            selenium = SeleniumStore.getNotRecursively(path);
        }else if(PARALLEL_MODE_METHOD.equals(parallelMode)) {
            path = TestNGPath.getPathToInstance(result);
            selenium = SeleniumStore.getNotRecursively(path);
        } else {
            // 如果是单线程模式，什么都不用做
        }
        if(selenium != null) {
            log("testClass.onTestMethodFinish : close Selenium Instance : " + path);
            selenium.close();   
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
            log("testClass.beforeConfigurationMethod : " + method.getTestMethod().getConstructorOrMethod().getMethod().toString());
            onTestMethodOrConfigurationMethodStart(testResult);
        }
    }
    
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    private void log(String message) {
        String format = "{0} {1, time}" + this.getClass().getName() + ": {2}";
        System.out.println(MessageFormat.format(format, StringUtils.rightPad("#" + Thread.currentThread().getId(), 4), new Date(), message));
    }
    
}
