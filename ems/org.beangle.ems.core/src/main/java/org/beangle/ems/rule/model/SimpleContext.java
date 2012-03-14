/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule.model;

import java.util.List;
import java.util.Map;

import org.beangle.collection.CollectUtils;
import org.beangle.ems.rule.Context;
import org.beangle.util.i18n.Message;

public class SimpleContext implements Context {

	private List<Message> errors = CollectUtils.newArrayList();

	private List<Message> messages = CollectUtils.newArrayList();

	private Map<String, Object> params = CollectUtils.newHashMap();

	public void addError(Message message) {
		errors.add(message);
	}

	public void addMessage(Message message) {
		messages.add(message);
	}

	public List<Message> getErrors() {
		return errors;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	@SuppressWarnings("unchecked")
	public <T> T getParam(String paramName, Class<T> clazz) {
		return (T) getParams().get(paramName);
	}

}
