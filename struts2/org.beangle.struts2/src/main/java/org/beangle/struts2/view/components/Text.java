/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.components;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.xwork.StringUtils;
import org.apache.struts2.components.ContextBean;
import org.apache.struts2.components.Param;
import org.apache.struts2.util.TextProviderHelper;
import org.beangle.commons.collection.CollectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.util.ValueStack;

public class Text extends ContextBean implements Param.UnnamedParametric {
	private static final Logger logger = LoggerFactory.getLogger(Text.class);
	protected List<Object> values = Collections.emptyList();
	protected String actualName;
	protected String name;
	protected String searchStack;

	public Text(ValueStack stack) {
		super(stack);
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSearchValueStack(String searchStack) {
		this.searchStack = searchStack;
	}

	public boolean usesBody() {
		return true;
	}

	public boolean end(Writer writer, String body) {
		actualName = findString(name, "name",
				"You must specify the i18n key. Example: welcome.header");
		String defaultMessage;
		if (StringUtils.isNotEmpty(body)) {
			defaultMessage = body;
		} else {
			defaultMessage = actualName;
		}
		Boolean doSearchStack = searchStack != null ? (Boolean) findValue(searchStack,
				Boolean.class) : true;
		String msg = TextProviderHelper.getText(actualName, defaultMessage, values, getStack(),
				doSearchStack == null || doSearchStack);
		if (msg != null) {
			try {
				if (getVar() == null) {
					writer.write(msg);
				} else {
					putInContext(msg);
				}
			} catch (IOException e) {
				logger.error("Could not write out Text tag", e);
			}
		}
		return super.end(writer, "");
	}

	public void addParameter(String key, Object value) {
		addParameter(value);
	}

	public void addParameter(Object value) {
		if (values.isEmpty()) {
			values = CollectUtils.newArrayList(2);
		}
		values.add(value);
	}

}
