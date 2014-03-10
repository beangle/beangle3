/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
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
