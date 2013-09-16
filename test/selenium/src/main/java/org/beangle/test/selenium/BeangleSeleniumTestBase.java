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

import java.text.MessageFormat;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;

public class BeangleSeleniumTestBase extends SeleniumTestBase {

    // Freemarker 错误的locator
    protected static final String FM_ERROR = "xpath=//*[contains(text(), '服务器内部错误')]";
    
    // Freemarker 错误展开按钮的locator
    protected static final String FM_ERROR_EXPANDER = "id=stackTraceA";
    
    private void my97Command(String command) {
        WebDriver driver = ((WebDriverBackedSelenium)selenium).getWrappedDriver();
        WebElement iframe = driver.findElement(new By.ByXPath("//iframe[contains(@src, 'My97DatePicker')]"));
        driver.switchTo().frame(iframe);
        selenium.click(command);
        driver.switchTo().defaultContent();
    }

    /**
     * 对有My97日历控件的日期输入框进行输入
     * @param name
     * @param date
     * @throws Exception 
     */
    protected void typeMy97Date(String name, String date) throws Exception {
        String locator = "xpath=//input[@class='Wdate' and @name='" + name + "']";
        waitForElementPresent(locator);
        
        selenium.type(locator, date);
        my97Command("id=dpOkInput");
    }

    protected void typeMy97Today(String name) throws Exception {
        String locator = "xpath=//input[@class='Wdate' and @name='" + name + "']";
        waitForElementPresent(locator);
        
        selenium.click(locator);
        my97Command("id=dpTodayInput");
    }

    protected void typeMy97Clear(String name) throws Exception {
        String locator = "xpath=//input[@class='Wdate' and @name='" + name + "']";
        waitForElementPresent(locator);
        
        selenium.click(locator);
        my97Command("id=dpClearInput");
    }

    /**
     * 断言没有出现Freemarker错误
     * @throws InterruptedException 
     */
    protected void b_assertNoFreeMarkerError() throws InterruptedException {
        if(selenium.isElementPresent(FM_ERROR)) {
            if(selenium.isElementPresent(FM_ERROR_EXPANDER)) {
                selenium.click(FM_ERROR_EXPANDER);
            }
            fail("出现Freemarker错误");
        }
    }

    /**
     * 点击beangle样式的tab
     * @param text
     * @throws Exception 
     */
    protected void b_clickBeangleTab(String text) throws Exception {
        String locator = "xpath=//div[@class='navmenu']//a[text()='" + text + "']";
        waitForElementPresent(locator);
        
        selenium.click(locator);
        selenium.waitForPageToLoad(PAGE_LOAD_TIMEOUT);
    }

    /**
     * 点击搜索按钮
     * 
     * @throws Exception
     */
    protected void b_search() throws Exception {
        String locator = "xpath=//input[@value='查询' and @type='submit']";
        waitForElementPresent(locator);
    
        selenium.click(locator);
        selenium.waitForPageToLoad(PAGE_LOAD_TIMEOUT);
    }

    
    /**
     * 选中所有list页面上的checkbox
     * 
     * @param name
     * @throws Exception
     */
    protected void b_checkAllBox(String name) throws Exception {
        String locator = MessageFormat.format(CHECKBOX_PATTERN, name);
        if (selenium.isChecked(locator)) {
            selenium.click(locator);
            selenium.click(locator);
        } else {
            selenium.click(locator);
        }
    }

    /**
     * 取消选中所有list页面上的checkbox
     * 
     * @param name
     * @throws Exception
     */
    protected void b_uncheckAllBox(String name) throws Exception {
        String locator = MessageFormat.format(CHECKBOX_PATTERN, name);
        waitForElementPresent(locator);
    
        if (selenium.isChecked(locator)) {
            selenium.click(locator);
        } else {
            selenium.click(locator);
            selenium.click(locator);
        }
    }

}
