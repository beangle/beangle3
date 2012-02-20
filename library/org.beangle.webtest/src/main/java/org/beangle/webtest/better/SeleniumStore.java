package org.beangle.webtest.better;

import java.util.HashMap;
import java.util.Map;

import com.thoughtworks.selenium.Selenium;

public class SeleniumStore {

    private static final ThreadLocal<Map<String, Selenium>> pathToSeleniums = new ThreadLocal<Map<String, Selenium>>() {
        @Override
        protected Map<String, Selenium> initialValue() {
            return new HashMap<String, Selenium>();
        }
    }; 
    
    /**
     * get the current thread selenium, recursively
     * @param path
     * @return
     */
    public static Selenium get(String path) {
        Selenium selenium =  pathToSeleniums.get().get(path);
        if(selenium == null && path.lastIndexOf("/") > 0) {
            return get(path.substring(0, path.lastIndexOf("/")));
        }
        return selenium;
    }
    
    public static Selenium getNotRecursively(String path) {
        return pathToSeleniums.get().get(path);
    }
    
    /**
     * bind a certain selenium instance to a path<br>
     * If there is already a selenium instance bind to that path, then do nothing 
     * @param path
     * @param selenium
     */
    public static synchronized void set(String path, Selenium selenium) {
        Map<String, Selenium> map = pathToSeleniums.get();
        if(map.get(path) == null) {
            map.put(path, selenium);
        }
    }
}
