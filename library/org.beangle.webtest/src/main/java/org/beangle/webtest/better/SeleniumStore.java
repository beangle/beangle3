package org.beangle.webtest.better;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.thoughtworks.selenium.Selenium;

public class SeleniumStore {

    private static final ThreadLocal<Map<String, Selenium>> pathToSeleniums = new ThreadLocal<Map<String, Selenium>>() {
        @Override
        protected Map<String, Selenium> initialValue() {
            return new HashMap<String, Selenium>();
        }
    }; 
    
    private static final Map<String, Set<Selenium>> seleniumsOfTestInClassesParallelMode = new HashMap<String, Set<Selenium>>();
    
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
     * If selenium is null, do nothing
     * @param path
     * @param selenium
     */
    public static synchronized void set(String path, Selenium selenium) {
        if(selenium != null) {
            Map<String, Selenium> map = pathToSeleniums.get();
            map.put(path, selenium);
        }
    }
    
    /**
     * 当使用classes, instances多线程模式时，绑定在instance上的selenium只能在test.onFinish的时候关闭<br>
     * 但是test.onFinish/onStart和instances的测试方法调用不在一个thread里，所以不能够通过 pathToSeleniums来获得一个<br>
     * test下所有的classes的instances所绑定的selenium, 因此只能采取这种折中的办法来解决
     */
    public static void setWhenClassesParallelMode(String path, Selenium selenium) {
        Set<Selenium> seleniums = seleniumsOfTestInClassesParallelMode.get(path);
        if(seleniums == null) {
            seleniums = new HashSet<Selenium>();
            seleniumsOfTestInClassesParallelMode.put(path, seleniums);
        }
        seleniums.add(selenium);
    }
    
    public static Set<Selenium> getWhenClassesParallelMode(String path) {
        return seleniumsOfTestInClassesParallelMode.get(path);
    }
}
