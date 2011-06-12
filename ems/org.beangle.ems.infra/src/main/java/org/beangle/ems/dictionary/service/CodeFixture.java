/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.dictionary.service;


import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
/**
*
* @author chaostone
* @version $Id: CodeFixture.java May 5, 2011 3:48:37 PM chaostone $
*/
public class CodeFixture {

	private Map<String, Object> params = CollectUtils.newHashMap();

	private String script;

	private Object entity;
	
	public CodeFixture(Object entity) {
		super();
		this.entity = entity;
	}

	public CodeFixture(Object entity, String script) {
		super();
		this.entity = entity;
		this.script = script;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public String getScript() {
		return script;
	}

	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public final Object getEntity() {
		return entity;
	}

}