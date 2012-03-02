package org.beangle.webtest.selenium;

import java.text.MessageFormat;

import org.apache.commons.lang.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverBackedSelenium;
import org.openqa.selenium.WebElement;

public class BeangleSeleniumTestBase extends SeleniumTestBase {

    protected static final double TIMEOUT_SEC_LONG = 20;
    protected static final double TIMEOUT_SEC_SHORT = 1;
    protected static final int    TIMEOUT_WAIT_LOOP_LAP = 400;
    
    // Freemarker 错误的locator
    protected static final String FM_ERROR = "xpath=//*[contains(text(), '服务器内部错误')]";
    
    // Freemarker 错误展开按钮的locator
    protected static final String FM_ERROR_EXPANDER = "id=stackTraceA";

    // TODO Form Element
    
    // checkbox and radio pattern in a certain parent
    protected static final String XPATH_CHECKBOXES_CHILD = "xpath=//{1}//input[@type=''checkbox'' and @name=''{0}'']";
    protected static final String XPATH_RADIOS_CHILD     = "xpath=//{1}//input[@type=''radio'' and @name=''{0}'']";
    protected static final String XPATH_RADIO_CHILD      = "xpath=(//{1}//input[(@type=''radio'') and @name=''{0}''])[{1}]";
    protected static final String XPATH_CHECKBOX_CHILD   = "xpath=(//{1}//input[(@type=''checkbox'') and @name=''{0}''])[{1}]";
    protected static final String XPATH_CHECKABLE_CHILD  = "xpath=(//{1}//input[(@type=''checkbox'' or @type=''radio'') and @name=''{0}''])[{1}]";

    // checkbox and radio  pattern
    protected static final String XPATH_CHECKBOXES = "xpath=//input[@type=''checkbox'' and @name=''{0}'']";
    protected static final String XPATH_RADIOS     = "xpath=//input[@type=''radio'' and @name=''{0}'']";
    protected static final String XPATH_RADIO      = "xpath=(//input[(@type=''radio'') and @name=''{0}''])[{1}]";
    protected static final String XPATH_CHECKBOX   = "xpath=(//input[(@type=''checkbox'') and @name=''{0}''])[{1}]";
    protected static final String XPATH_CHECKABLE  = "xpath=(//input[(@type=''checkbox'' or @type=''radio'') and @name=''{0}''])[{1}]";
    
    // jquery-ui pattern
    protected static final String XPATH_JQUERY_TAB = "css=.ui-tabs-nav a[title=''{0}'']";
    
    
    /**
     * 等待某个元素出现为止，超时时间为ELEMENT_TIMEOUT_SEC_LONG秒
     * 
     * @param locator
     * @throws Exception
     */
    protected void waitForElementPresent(String locator) throws Exception {
        waitForElementPresent(locator, TIMEOUT_SEC_LONG);
    }

    /**
     * 等待某个元素出现在DOM树中，且可见为止，超时秒数由用户指定
     * 
     * @param locator
     * @param timeoutSecs
     * @throws Exception
     */
    protected void waitForElementPresent(String locator, double timeoutSecs) throws Exception {
        for (int milliseconds = 0;; milliseconds += TIMEOUT_WAIT_LOOP_LAP) {
            if (milliseconds >= timeoutSecs * 1000)
                fail("timeout: " + locator);
            try {
                if (selenium.isElementPresent(locator) && selenium.isVisible(locator)) {
                    break;
                }
            } catch (Exception e) {
            }
            Thread.sleep(TIMEOUT_WAIT_LOOP_LAP);
        }
    }

    /**
     * 等待某个元素不出现在DOM树，或者不可见为止，超时时间为ELEMENT_TIMEOUT_SEC_LONG秒
     * 
     * @param locator
     * @throws Exception
     */
    protected void waitForElementVanish(String locator) throws Exception {
        waitForElementVanish(locator, TIMEOUT_SEC_LONG);
    }

    /**
     * 等待某个元素不出现在DOM树，或者不可见为止，超时秒数由用户指定
     * 
     * @param locator
     * @param timeoutSecs
     * @throws Exception
     */
    protected void waitForElementVanish(String locator, double timeoutSecs) throws Exception {
        for (int milliseconds = 0;; milliseconds += TIMEOUT_WAIT_LOOP_LAP) {
            if (milliseconds >= timeoutSecs * 1000)
                fail("timeout: " + locator);
            try {
                if (!selenium.isElementPresent(locator) || !selenium.isVisible(locator))
                    break;
            } catch (Exception e) {
            }
            Thread.sleep(TIMEOUT_WAIT_LOOP_LAP);
        }
    }
    
    
    /**
     * 点击jQuery-ui样式的Tab
     * 
     * @param text
     * @throws Exception
     */
    protected void clickJQueryTab(String text) throws Exception {
        String locator = MessageFormat.format(XPATH_JQUERY_TAB, text);
        waitForElementPresent(locator);

        selenium.click(locator);
        selenium.waitForPageToLoad(PAGE_LOAD_TIMEOUT);
    }

    /**
     * 关闭color box
     * @throws Exception 
     */
    protected void closeColorbox() throws Exception {
        String locator = "css=div#cboxClose";
        waitForElementPresent(locator, TIMEOUT_SEC_SHORT);
        
        selenium.click(locator);
        waitForElementVanish("css=#cboxLoadedContent");
    }
    
    /**
     * check上的某个checkbox/radio
     * 
     * @param name
     * @param index
     *            从1开始的数字
     * @throws Exception
     */
    protected void check(String name, int index) throws Exception {
        String locator = MessageFormat.format(XPATH_CHECKABLE, name, index);
        waitForElementPresent(locator);

        if (!selenium.isChecked(locator)) {
            selenium.click(locator);
        }
    }

    /**
     * 取消选中页面上的某个checkbox/radio
     * 
     * @throws Exception
     */
    protected void uncheck(String name, int index) throws Exception {
        String locator = MessageFormat.format(XPATH_CHECKABLE, name, index);
        waitForElementPresent(locator);

        if (selenium.isChecked(locator)) {
            selenium.click(locator);
        }
    }

    /**
     * 选中页面上所有的checkbox
     * @param name
     * @throws Exception 
     */
    protected void checkAll(String name) throws Exception {
        int count = selenium.getXpathCount(MessageFormat.format(XPATH_CHECKBOXES.replace("xpath=", ""), name)).intValue();
        for(int i = 1; i <=count; i++) {
            String single_box_locator = MessageFormat.format(XPATH_CHECKBOX, name, i);
            if(!selenium.isChecked(single_box_locator)) {
                selenium.click(single_box_locator);
            }
        }
    }
    
    /**
     * 取消选中页面上所有的checkbox
     * @param name
     */
    protected void uncheckAll(String name) {
        int count = selenium.getXpathCount(MessageFormat.format(XPATH_CHECKBOXES.replace("xpath=", ""), name)).intValue();
        for(int i = 1; i <=count; i++) {
            String single_box_locator = MessageFormat.format(XPATH_CHECKBOX, name, i);
            if(selenium.isChecked(single_box_locator)) {
                selenium.click(single_box_locator);
            }
        }
    }
    
    /**
     * 检查list页面上的checkbox是否选中
     * 
     * @param name
     * @return
     * @throws Exception
     */
    protected boolean isChecked(String name, int index) throws Exception {
        String locator = MessageFormat.format(XPATH_CHECKABLE, name, index);
        return selenium.isChecked(locator);
    }

    /**
     * 提交Form，如果页面上只有一个Form可以这么做
     * 
     * @throws Exception
     */
    protected void submitForm() throws Exception {
        submitForm(null);
    }

    /**
     * 提交Form
     * 
     * @param formName
     * @throws Exception
     */
    protected void submitForm(String formName) throws Exception {
        // TODO HTML5 compatible
        String locator = "css=input[type=submit]";
        if (StringUtils.isNotBlank(formName)) {
            locator = "css=form[name=" + formName + "] input[type=submit]";
        }
        selenium.click(locator);
        selenium.waitForPageToLoad(PAGE_LOAD_TIMEOUT);
    }
    
    /**
     * 重置Form，如果页面上只有一个Form可以这么做
     * @throws Exception
     */
    protected void resetForm() throws Exception {
        resetForm(null);
    }
    
    /**
     * 重置Form
     * @param formName
     * @throws Exception
     */
    protected void resetForm(String formName) throws Exception {
        String locator = "css=input[type=reset]";
        if (StringUtils.isNotBlank(formName)) {
            locator = "css=form[name=" + formName + "] input[type=reset]";
        }
        selenium.click(locator);
    }

    /**
     * 选择select中的某个option
     */
    protected void select(String selectName, String optionText) {
        String selectLocator = "css=select[name='" + selectName + "']";
        String optionLocator = "label=" + optionText;
        selenium.select(selectLocator, optionLocator);
        selenium.waitForPageToLoad(PAGE_LOAD_TIMEOUT);
    }

    
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
        String locator = MessageFormat.format(XPATH_CHECKBOXES, name);
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
        String locator = MessageFormat.format(XPATH_CHECKBOXES, name);
        waitForElementPresent(locator);
    
        if (selenium.isChecked(locator)) {
            selenium.click(locator);
        } else {
            selenium.click(locator);
            selenium.click(locator);
        }
    }

}
