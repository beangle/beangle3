/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.util.Collection;

import org.beangle.struts2.action.ActionSupport;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

public class Messages extends UIBean {

	Collection<String> actionMessages = null;
	Collection<String> actionErrors = null;

	private String clear = "true";

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
			if ("true".equals(clear)) {
				Object action = ActionContext.getContext().getActionInvocation().getAction();
				if (action instanceof ActionSupport) {
					((ActionSupport) action).clearErrorsAndMessages();
				}
			}
		}
	}

	public boolean hasActionErrors() {
		return !actionErrors.isEmpty();
	}

	public boolean hasActionMessages() {
		return !actionMessages.isEmpty();
	}

	public void setClear(String clear) {
		this.clear = clear;
	}

	public Collection<String> getActionMessages() {
		return actionMessages;
	}

	public Collection<String> getActionErrors() {
		return actionErrors;
	}

}
