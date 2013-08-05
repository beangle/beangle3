package org.beangle.test.selenium;

import java.io.File;
import java.text.MessageFormat;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.IResultListener;

public class PageCaptureListener implements IResultListener {

    File outputDirectory;

    TakesScreenshot screenshotTaker;
    
    public PageCaptureListener(File outputDirectory, TakesScreenshot screenshotTaker) {
        this.outputDirectory = outputDirectory;
        this.screenshotTaker = screenshotTaker;
    }

    public void onTestFailure(ITestResult result) {
        Reporter.setCurrentTestResult(result);
        try {
            outputDirectory.mkdirs();
            File tmp_screenshot = screenshotTaker.getScreenshotAs(OutputType.FILE);
            File outFile = new File(outputDirectory, "TEST-" + result.getName()+ ".png");
            FileUtils.copyFile(tmp_screenshot, outFile);
            Reporter.log(MessageFormat.format("<a href=''file://{0}''>错误截图</a>", outFile.getAbsolutePath()));
        } catch (Exception e) {
            e.printStackTrace();
            Reporter.log("Couldn't create screenshot");
            Reporter.log(e.getMessage());
        }
        Reporter.setCurrentTestResult(null);
    }
    
    public void onConfigurationFailure(ITestResult result) {
        onTestFailure(result);
    }

    public void onFinish(ITestContext context) {
    }

    public void onStart(ITestContext context) {
        outputDirectory = new File(context.getOutputDirectory());
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
    }

    public void onTestSkipped(ITestResult result) {
    }

    public void onTestStart(ITestResult result) {
    }

    public void onTestSuccess(ITestResult result) {
    }

    public void onConfigurationSuccess(ITestResult itr) {
    }

    public void onConfigurationSkip(ITestResult itr) {
    }
}
