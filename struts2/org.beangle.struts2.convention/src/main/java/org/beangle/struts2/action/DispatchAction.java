/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.action;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.CharUtils;
import org.apache.commons.lang.StringUtils;
import org.beangle.struts2.convention.Flash;
import org.beangle.struts2.convention.route.Action;

import com.opensymphony.xwork2.ActionContext;

public class DispatchAction extends ActionSupport {

	/**
	 * forward to index method
	 */
	public String execute() throws Exception {
		return forward(new Action((Class<?>) null, "index"));
	}

	protected String forward() {
		return SUCCESS;
	}

	protected String forward(String view) {
		return view;
	}

	protected String forward(String view, String message) {
		addActionMessage(getText(message));
		return view;
	}

	/**
	 * @param action
	 * @return
	 */
	protected String forward(Action action) {
		ActionContext.getContext().getContextMap().put("dispatch_action", action);
		return "chain:dispatch_action";
	}

	protected String forward(Action action, String message) {
		if (null != message) addActionMessage(getText(message));
		return forward(action);
	}

	/**
	 * @param method
	 * @param message
	 * @param params
	 * @return
	 */
	protected String redirect(String method, String message, String params) {
		return redirect(new Action((String) null, method, params), message);
	}

	protected String redirect(String method) {
		return redirect(new Action(method), null);
	}

	/**
	 * @param method
	 * @param message
	 * @return
	 */
	protected String redirect(String method, String message) {
		return redirect(new Action(method), message);
	}

	protected String redirect(Action action, String message) {
		if (StringUtils.isNotEmpty(message)) {
			addFlashMessage(message);
		}
		ActionContext.getContext().getContextMap().put("dispatch_action", action);
		return "redirectAction:dispatch_action";
	}

	protected String getTextInternal(String msgKey, Object... args) {
		if (null == msgKey) return null;
		if (CharUtils.isAsciiAlpha(msgKey.charAt(0)) && msgKey.indexOf('.') > 0) {
			if (args.length > 0) return getText(msgKey, Arrays.asList(args));
			else return getText(msgKey);
		} else {
			return msgKey;
		}
	}

	protected void addMessage(String msgKey) {
		addActionMessage(getTextInternal(msgKey));
	}

	protected void addError(String msgKey) {
		addActionError(getTextInternal(msgKey));
	}

	protected void addFlashError(String msgKey, Object... args) {
		getFlash().addError(getTextInternal(msgKey, args));
	}

	protected void addFlashMessage(String msgKey, Object... args) {
		getFlash().addMessage(getTextInternal(msgKey, args));
	}

	protected void addFlashMessageNow(String msgKey, Object... args) {
		getFlash().addMessageNow(getTextInternal(msgKey, args));
	}

	protected void addFlashErrorNow(String msgKey, Object... args) {
		getFlash().addErrorNow(getTextInternal(msgKey, args));
	}

	protected Flash getFlash() {
		Flash flash = (Flash) ActionContext.getContext().getSession().get("flash");
		if (null == flash) {
			flash = new Flash();
			ActionContext.getContext().getSession().put("flash", flash);
		}
		return flash;
	}

	/**
	 * 将flash中的消息转移到actionmessage<br>
	 * 不要将flash和message混合使用。
	 */
	public Collection<String> getActionMessages() {
		Flash flash = getFlash();
		@SuppressWarnings("unchecked")
		List<String> messages = (List<String>) flash.get(Flash.MESSAGES);
		if (null != messages) {
			for (String msg : messages) {
				addActionMessage(msg);
			}
			messages.clear();
		}
		return super.getActionMessages();
	}

	/**
	 * 将flash中的错误转移到actionerror<br>
	 * 不要将flash和error混合使用。
	 */
	public Collection<String> getActionErrors() {
		Flash flash = getFlash();
		@SuppressWarnings("unchecked")
		List<String> errors = (List<String>) flash.get(Flash.ERRORS);
		if (null != errors) {
			for (String msg : errors) {
				addActionError(msg);
			}
			errors.clear();
		}
		return super.getActionErrors();
	}

}
