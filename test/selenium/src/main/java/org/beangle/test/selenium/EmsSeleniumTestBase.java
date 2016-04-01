/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

/**
 * 教务项目中某一个菜单的TestCase，多个菜单的功能测试应该继承不同TestCase
 * @author qianjia
 *
 */
public abstract class EmsSeleniumTestBase extends BeangleSeleniumTestBase {

    protected static final String ACTION_SUCCESS_LOCATOR = "css=.actionMessage";
    protected static final String ACTION_FAILURE_LOCATOR = "css=.actionError";
    
    @BeforeTest
    @Parameters({"username", "password"})
    public void login(String username, String password) {
        selenium.open("login.action");
        selenium.type("id=username", username);
        selenium.type("id=password", password);
        selenium.click("name=submitBtn");
        selenium.waitForPageToLoad(PAGE_LOAD_TIMEOUT);
    }
    
    @AfterTest
    public void logout() {
        selenium.open("/logout.action");
        selenium.waitForPageToLoad(PAGE_LOAD_TIMEOUT);
    }
    
    /**
     * 子类必须实现的方法，用于进入菜单，并且必须加上 @BeforeMethod
     * @throws Exception
     */
    public abstract void intoMenu() throws Exception;
    
    /**
     * 点击左侧菜单
     * @param text
     * @throws Exception 
     */
    protected void e_clickMenuSideBar(String text) throws Exception {
        String locator = "xpath=//div[@id='menu_panel']//li//a[text()='" + text + "']";
        waitForElementPresent(locator);
        
        selenium.click(locator);
        selenium.waitForPageToLoad(PAGE_LOAD_TIMEOUT);
    }

    /**
     * 点击上方的导航条
     * @param text
     * @throws Exception
     */
    protected void e_clickNavBanner(String text) throws Exception {
        String locator = "xpath=//ul[@id='nav_box']//a//span[contains(text(), '" + text + "')]/parent::a";
        selenium.click(locator);
        selenium.waitForPageToLoad(PAGE_LOAD_TIMEOUT);
    }

    /**
     * 点击工具栏上的按钮
     * @param text
     * @throws Exception 
     */
    protected void e_clickToolbar(String text) throws Exception {
        String locator = "xpath=//div[@class='gridbar' or contains(@class, 'toolbar')]//div[text()= '"+ text + "']";
        selenium.click(locator);
        selenium.waitForPageToLoad(PAGE_LOAD_TIMEOUT);
    }

    /**
     * 断言操作成功，成功的标志是页面出现了类似“保存成功”的提示信息，并且没有出现类似“操作失败”的提示信息
     * @throws Exception
     */
    protected void e_assertActionSuccess() throws Exception {
        try{
            waitForElementPresent(ACTION_FAILURE_LOCATOR, ELEMENT_TIMEOUT_SEC_SHORT);
            fail("操作失败");
        } catch(AssertionError e) {
            
        }
        waitForElementPresent(ACTION_SUCCESS_LOCATOR);
    }

    /**
     * 断言操作失败，失败的标志是页面出现了类似“操作失败”的提示信息
     * @throws Exception
     */
    protected void e_assertActionFailure() throws Exception {
        waitForElementPresent(ACTION_FAILURE_LOCATOR, ELEMENT_TIMEOUT_SEC_SHORT);
    }

}
