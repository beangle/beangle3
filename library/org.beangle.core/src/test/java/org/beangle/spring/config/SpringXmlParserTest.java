/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.config;

import java.util.List;

import org.beangle.spring.config.processor.BeanDefinitionReader;
import org.beangle.spring.config.processor.ReconfigBeanDefinitionHolder;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.Test;

@Test
public class SpringXmlParserTest {

	public void test() throws Exception {
		parser("org/beangle/spring/config/context.xml");
	}

	private void parser(String path) throws Exception {
		List<ReconfigBeanDefinitionHolder> holders = new BeanDefinitionReader().load(new ClassPathResource(
				path));
		for (ReconfigBeanDefinitionHolder holder : holders) {
			System.out.println(holder);
		}
	}
}
