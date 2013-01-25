/*
 * Beangle, Agile Java/Scala Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2012, Beangle Software.
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
package org.beangle.inject.spring.config;

import java.util.List;

import org.beangle.inject.spring.config.BeanDefinitionReader;
import org.beangle.inject.spring.config.ReconfigBeanDefinitionHolder;
import org.springframework.core.io.ClassPathResource;
import org.testng.annotations.Test;

@Test
public class SpringXmlParserTest {

  public void test() throws Exception {
    parser("org/beangle/inject/spring/context.xml");
  }

  private void parser(String path) throws Exception {
    List<ReconfigBeanDefinitionHolder> holders = new BeanDefinitionReader().load(new ClassPathResource(path));
    for (ReconfigBeanDefinitionHolder holder : holders) {
      System.out.println(holder);
    }
  }
}
