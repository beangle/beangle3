/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.spring.config;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class BeanDefinitionReader {

	public List<ReconfigBeanDefinitionHolder> load(Resource resource) {
		List<ReconfigBeanDefinitionHolder> holders = new ArrayList<ReconfigBeanDefinitionHolder>();
		try {
			InputStream inputStream = resource.getInputStream();
			try {
				InputSource inputSource = new InputSource(inputStream);
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder docBuilder = factory.newDocumentBuilder();
				Document doc = docBuilder.parse(inputSource);
				Element root = doc.getDocumentElement();
				NodeList nl = root.getChildNodes();
				BeanDefinitionParser parser = new BeanDefinitionParser();
				for (int i = 0; i < nl.getLength(); i++) {
					Node node = nl.item(i);
					if (node instanceof Element) {
						Element ele = (Element) node;
						ReconfigBeanDefinitionHolder holder = parser.parseBeanDefinitionElement(ele);
						holders.add(holder);
					}
				}
			} finally {
				if (null != inputStream) {
					inputStream.close();
				}
			}
		} catch (Exception ex) {
			throw new RuntimeException("IOException parsing XML document from " + resource.getDescription(),
					ex);
		}
		return holders;
	}
}
