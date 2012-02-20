package org.beangle.webtest.better;

import java.lang.reflect.Method;
import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
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
    private static final String PARALLEL_MODE_METHOD = "methods";
    
    ///////////////////////////
    // suite part
    ///////////////////////////
    @Override
    public void onStart(ISuite suite) {
        // invoked after <suite> element is instantiated
        log("Suite.onStart : " + suite.getName());
        XmlSuite xmlSuite = suite.getXmlSuite();
        String parallelMode = xmlSuite.getParallel();
        if(StringUtils.isBlank(parallelMode)) {
            // 如果child suite override了parent suite的某部分或全部selenium配置，那么就应该新建一个
            if(sm.isOverrideConfiguration(xmlSuite)) {
                SeleniumStore.set(TestNGPath.getPath(xmlSuite), sm.make(xmlSuite));
            }
        } else {
            // 如果是多线程模式，那么不论是哪种模式，suite上绑定一个selenium都是没有意义的
        }
    }
    
    @Override
    public void onFinish(ISuite suite) {
        // invoked after <suite> is 
        XmlSuite xmlSuite = suite.getXmlSuite();
        Selenium selenium = SeleniumStore.getNotRecursively(TestNGPath.getPath(xmlSuite));
        if(selenium != null) {
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
        if(PARALLEL_MODE_TEST.equals(parallelMode)) {
            // 如果是test级别的多线程模式，那么每个<test>都应该有一个selenium
            SeleniumStore.set(TestNGPath.getPath(xmlTest), sm.make(xmlTest));
        } else if (PARALLEL_MODE_CLASS.equals(parallelMode)) {
            // 如果是class级别的多线程模式，那么在某个test上绑定一个selenium是没有意义的
        } else if (PARALLEL_MODE_METHOD.equals(parallelMode)) {
            // 如果是method级别的多线程模式，那么在某个test上绑定一个selenium是没有意义的
        } else {
            // 单线程模式下
            // 如果test override了suite的某部分或全部selenium配置，那么就应该新建一个
            if(sm.isOverrideConfiguration(xmlTest)) {
                SeleniumStore.set(TestNGPath.getPath(xmlTest), sm.make(xmlTest));
            }
        }
    }
    
    
    @Override
    public void onFinish(ITestContext context) {
        // invoked after all the test classes in the <test> element is tested 
        XmlTest xmlTest = context.getCurrentXmlTest();
        Selenium selenium = SeleniumStore.getNotRecursively(TestNGPath.getPath(xmlTest));
        // 如果找到了test级别的selenium，那么就可以断定使用的是test级别的多线程模式
        if(selenium != null) {
            selenium.close();   
        }
    }
    
    ///////////////////////////
    // test method part
    ///////////////////////////
    @Override
    public void onTestStart(ITestResult result) {
        onTestMethodOrConfigurationMethodStart(result.getMethod());
    }
    
    @Override
    public void onTestSuccess(ITestResult result) {
        // be called after a @Test annotated method be called and also after IInvokedMethodListener.afterInvocation
        onTestMethodOrConfigurationMethodFinished(result.getMethod());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // be called after a @Test annotated method be called and also after IInvokedMethodListener.afterInvocation
        onTestMethodOrConfigurationMethodFinished(result.getMethod());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        // be called after a @Test annotated method be called and also after IInvokedMethodListener.afterInvocation
        onTestMethodOrConfigurationMethodFinished(result.getMethod());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        // be called after a @Test annotated method be called and also after IInvokedMethodListener.afterInvocation
        onTestMethodOrConfigurationMethodFinished(result.getMethod());
    }

    private void onTestMethodOrConfigurationMethodStart(ITestNGMethod method) {
        String parallelMode = method.getXmlTest().getSuite().getParallel();
        if(PARALLEL_MODE_TEST.equals(parallelMode)) {
            // 如果是test级别的多线程模式，什么都不用做，因为一个test内的所有class共享一个selenium instance
        } else if (PARALLEL_MODE_CLASS.equals(parallelMode)) {
            // 如果是class级别的多线程模式，什么都不用做，因为一个class内部共享一个selenium instance
        } else if(PARALLEL_MODE_METHOD.equals(parallelMode)) {
            // 如果是method级别的多线程模式
            XmlTest xmlTest = method.getXmlTest();
            Class<?> testClass = method.getConstructorOrMethod().getDeclaringClass();
            Method testMethod = method.getConstructorOrMethod().getMethod();
            SeleniumStore.set(TestNGPath.getPath(xmlTest, testClass, testMethod), sm.make(xmlTest));
        } else {
            // 如果是单线程模式，什么都不用做
        }
    }
    
    private void onTestMethodOrConfigurationMethodFinished(ITestNGMethod method) {
        XmlTest xmlTest = method.getXmlTest();
        Class<?> testClass = method.getConstructorOrMethod().getDeclaringClass();
        Method testMethod = method.getConstructorOrMethod().getMethod();
        Selenium selenium = SeleniumStore.getNotRecursively(TestNGPath.getPath(xmlTest, testClass, testMethod));
        // 一旦有了方法级别的selenium，那么就可以断定使用的是method级别的多线程模式
        if(selenium != null) {
            selenium.close();   
        }
    }
    
    
    ///////////////////////////
    // method part
    ///////////////////////////
    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        // TODO 不知道一个方法是不是既可以 @Test 也可以@Before之类
//        if(method.isConfigurationMethod()) {
//            onTestMethodOrConfigurationMethodStart(method.getTestMethod());
//        }
    }
    
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }


    private void log(String message) {
        String format = "SeleniumConfiguration : #{0} {1} {2}";
        System.out.println(MessageFormat.format(format, Thread.currentThread().getId(), new Date(), message));
    }
}
