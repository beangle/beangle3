/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.commons.text;

import java.util.ArrayList;
import java.util.List;

public class Message {

	private String key;

	private List<Object> params = new ArrayList<Object>();

	public Message(String key, List<Object> params) {
		super();
		this.key = key;
		this.params = params;
	}

	public Message(String key, Object[] objs) {
		super();
		this.key = key;
		if (null != objs) {
			for (int i = 0; i < objs.length; i++) {
				this.params.add(objs[i]);
			}
		}

	}

	public Message(String key) {
		super();
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}

}
