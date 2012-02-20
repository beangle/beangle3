package org.beangle.webtest.temp;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TempListener implements ISuiteListener, ITestListener, IInvokedMethodListener {

    @Override
    public void onFinish(ISuite suite) {
        System.out.println("ISuiteListener.onFinish");
    }

    @Override
    public void onStart(ISuite suite) {
        System.out.println("ISuiteListener.onStart");
    }

    @Override
    public void onFinish(ITestContext context) {
        System.out.println("ITestListener.onFinish");
    }

    @Override
    public void onStart(ITestContext context) {
        System.out.println("ITestListener.onStart");
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onTestFailure(ITestResult result) {
        System.out.println("ITestListener.onTestFailure");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        System.out.println("ITestListener.onTestSkipped");
    }

    @Override
    public void onTestStart(ITestResult result) {
        System.out.println("ITestListener.onTestStart");
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        System.out.println("ITestListener.onTestSuccess");
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("IInvokedMethodListener.afterInvocation");
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        System.out.println("IInvokedMethodListener.beforeInvocation");
    }

}
