package org.beangle.webtest.temp;

import java.text.MessageFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TempListener implements ISuiteListener, ITestListener, IInvokedMethodListener {

    private void log(String message) {
        String format = "TempListener : #{0} {1, time} {2}";
        System.out.println(MessageFormat.format(format, Thread.currentThread().getId(), new Date(), message));
    }
    
    @Override
    public void onFinish(ISuite suite) {
        log("suite.onFinish : " + suite.getName());
    }

    @Override
    public void onStart(ISuite suite) {
        log("suite.onStart : " + suite.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        log("test.onFinish : " + context.getName());
    }

    @Override
    public void onStart(ITestContext context) {
        log("test.onStart : " + context.getName());
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestFailure(ITestResult result) {
        log("test.onTestFailure : " + StringUtils.join(new String[]{result.getTestClass().getXmlTest().getName(), result.getTestClass().getName(), result.getInstance().toString(), result.getMethod().getConstructorOrMethod().getMethod().toString()}, '/'));
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        log("test.onTestSkipped : " + StringUtils.join(new String[]{result.getTestClass().getXmlTest().getName(), result.getTestClass().getName(), result.getInstance().toString(), result.getMethod().getConstructorOrMethod().getMethod().toString()}, '/'));
    }

    @Override
    public void onTestStart(ITestResult result) {
        log("test.onTestStart : " + StringUtils.join(new String[]{result.getTestClass().getXmlTest().getName(), result.getTestClass().getName(), result.getInstance().toString(), result.getMethod().getConstructorOrMethod().getMethod().toString()}, '/'));
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        log("test.onTestSuccess : " + StringUtils.join(new String[]{result.getTestClass().getXmlTest().getName(), result.getTestClass().getName(), result.getInstance().toString(), result.getMethod().getConstructorOrMethod().getMethod().toString()}, '/'));
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        log("method.beforeInvocation : " + StringUtils.join(new String[]{testResult.getTestClass().getXmlTest().getName(), testResult.getTestClass().getName(), testResult.getInstance().toString(), testResult.getMethod().getConstructorOrMethod().getMethod().toString()}, '/'));
//        log("method.beforeInvocation : " + method.getTestMethod().getTestClass().getName() + " : " + testResult.getInstance() + " : " + method.toString());
    }
    
    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        log("method.afterInvocation : " + StringUtils.join(new String[]{testResult.getTestClass().getXmlTest().getName(), testResult.getTestClass().getName(), testResult.getInstance().toString(), testResult.getMethod().getConstructorOrMethod().getMethod().toString()}, '/'));
//        log("method.afterInvocation : " + method.getTestMethod().getTestClass().getName() + " : " + testResult.getInstance() + " : " + method.toString());
    }

}
