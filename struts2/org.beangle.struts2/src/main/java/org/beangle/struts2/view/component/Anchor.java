/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.io.Writer;

import org.beangle.struts2.view.freemarker.BeangleModels;
import org.beangle.struts2.view.template.Theme;

import com.opensymphony.xwork2.util.ValueStack;

public class Anchor extends ClosingUIBean {

	private String href;

	public Anchor(ValueStack stack) {
		super(stack);
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	@Override
	public boolean end(Writer writer, String body) {
		if (getTheme().equals(Theme.DEFAULT_THEME)) {
			try {
				writer.append("<a href=\"");
				writer.append(BeangleModels.render.render(getRequestURI(), this.href)).append("\"");
				writer.append(getParameterString());
				writer.append(">").append(body).append("</a>");
			} catch (Exception e) {
				e.printStackTrace();
			}
			return false;
		} else {
			return super.end(writer, body);
		}
	}

}
