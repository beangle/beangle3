/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.util.Collection;

import com.opensymphony.xwork2.util.ValueStack;

public class Messages extends UIBean {

	Collection<String> actionMessages = null;
	Collection<String> actionErrors = null;

	public Messages(ValueStack stack) {
		super(stack);
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void evaluateParams() {
		actionMessages = (Collection<String>) findValue("actionMessages");
		actionErrors = (Collection<String>) findValue("actionErrors");
		if (!actionErrors.isEmpty() || !actionMessages.isEmpty()) {
			generateIdIfEmpty();
		}
	}

	public boolean hasActionErrors() {
		return !actionErrors.isEmpty();
	}

	public boolean hasActionMessages() {
		return !actionMessages.isEmpty();
	}

	public Collection<String> getActionMessages() {
		return actionMessages;
	}

	public Collection<String> getActionErrors() {
		return actionErrors;
	}

}
