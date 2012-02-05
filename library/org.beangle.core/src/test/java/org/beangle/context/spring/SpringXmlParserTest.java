/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.context.spring;

import java.util.List;

import org.beangle.context.spring.BeanDefinitionReader;
import org.beangle.context.spring.ReconfigBeanDefinitionHolder;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.Test;

@Test
public class SpringXmlParserTest {

	public void test() throws Exception {
		parser("org/beangle/context/spring/context.xml");
	}

	private void parser(String path) throws Exception {
		List<ReconfigBeanDefinitionHolder> holders = new BeanDefinitionReader().load(new ClassPathResource(
				path));
		for (ReconfigBeanDefinitionHolder holder : holders) {
			System.out.println(holder);
		}
	}
}
