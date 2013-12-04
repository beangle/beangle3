/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2013, Beangle Software.
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

import org.beangle.commons.lang.Strings;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestContext;
import org.testng.TestRunner;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.internal.IResultListener;

import com.thoughtworks.selenium.Selenium;

public class SeleniumTestBootstrap  {

    protected static Selenium selenium;
    protected static WebDriver seleniumDriver;
    protected static String PAGE_LOAD_TIMEOUT;

    public SeleniumTestBootstrap() {
        super();
    }

    @BeforeSuite(groups = "webdriver.start")
    @Parameters( { "selenium.url", "selenium.browser", "selenium.timeout" })
    public void beforeSuite(
            @Optional("http://localhost") String baseUrl, 
            @Optional("*firefox") String browserString, 
            @Optional("30000") String maxTimeout) throws Exception {
        seleniumDriver = null;
        if (Strings.contains(browserString, "firefox")) {
            seleniumDriver = new FirefoxDriver();
        } else if (Strings.contains(browserString, "chrome")) {
            seleniumDriver = new ChromeDriver();
        } else if (Strings.contains(browserString, "ie")) {
            seleniumDriver = new InternetExplorerDriver();
        } else {
            seleniumDriver = new HtmlUnitDriver(true);
        }
        
        seleniumDriver.manage().deleteAllCookies();
        seleniumDriver.manage().window().setPosition(new Point(0, 0));
        // maximize browser size
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        seleniumDriver.manage().window().setSize(new Dimension((int)screenSize.getWidth(), (int)screenSize.getHeight()));
        selenium = new WebDriverBackedSelenium(seleniumDriver, baseUrl);
        PAGE_LOAD_TIMEOUT = maxTimeout;
    }

    @BeforeSuite(groups="webdriver.testng.listener",dependsOnGroups = "webdriver.start")
    public void attachScreenshotListener(ITestContext context) {
        TestRunner tr = (TestRunner) context;
        File outputDirectory = new File(context.getOutputDirectory());
        tr.addListener((IResultListener) new PageCaptureListener(outputDirectory, (TakesScreenshot) seleniumDriver));
    }
    
    @AfterSuite(groups = "webdriver.stop")
    public void afterSuite() {
        selenium.close();
    }


}
