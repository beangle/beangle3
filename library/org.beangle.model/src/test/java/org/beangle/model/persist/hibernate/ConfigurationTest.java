/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.model.persist.hibernate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ConfigurationTest extends AbstractTestNGSpringContextTests {
	protected static final Logger logger = LoggerFactory.getLogger(ConfigurationTest.class);

	public void configTest() {
		System.out.println("config is ok!");
	}

}
