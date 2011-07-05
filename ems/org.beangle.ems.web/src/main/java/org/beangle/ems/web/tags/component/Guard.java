/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.web.tags.component;

import java.io.Writer;

import org.beangle.security.access.AuthorityManager;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * 对资源和内置区域进行守护
 * 
 * @author chaostone
 */
public class Guard extends SecurityUIBean {

	private String res;

	public Guard(ValueStack stack, AuthorityManager authorityManager) {
		super(stack, authorityManager);
	}

	public boolean end(Writer writer, String body) {
		return end(writer, body, true);
	}

	@Override
	protected String getResource() {
		return res;
	}

	public void setRes(String res) {
		this.res = res;
	}

}
