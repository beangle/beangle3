package org.beangle.webtest.temp;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class TestA extends TestBase {

    public TestA() {
        System.out.println("TestA.constructor");
    }

    @BeforeMethod
    public void before() {
        
    }
    
    @Test
    public void test1() {
    }
    
    @Test
    public void test2() {
    }
}
