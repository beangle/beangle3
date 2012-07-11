/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.context.spring;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;

/**
 * 基础服务的测试用例
 * 
 * @author chaostone 2005-9-8
 * @version $Id: $
 */
@ContextConfiguration("classpath:spring-context-test.xml")
public class SpringTestCase extends AbstractTestNGSpringContextTests {

}
