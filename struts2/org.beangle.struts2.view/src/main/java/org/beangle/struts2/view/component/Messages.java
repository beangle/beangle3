/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.view.component;

import java.util.Collection;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.struts2.convention.ActionMessages;
import org.beangle.struts2.convention.Flash;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.util.ValueStack;

public class Messages extends UIBean {

  List<String> actionMessages = null;
  List<String> actionErrors = null;

  private String clear = "true";

  public Messages(ValueStack stack) {
    super(stack);
  }

  protected Flash getFlash() {
    Flash flash = (Flash) ActionContext.getContext().getSession().get("flash");
    if (null == flash) {
      flash = new Flash();
      ActionContext.getContext().getSession().put("flash", flash);
    }
    return flash;
  }

  @Override
  protected void evaluateParams() {
    ActionMessages messages = (ActionMessages) getFlash().get(Flash.MESSAGES);
    if (null != messages && (!messages.getMessages().isEmpty() || !messages.getErrors().isEmpty())) {
      generateIdIfEmpty();
      actionMessages = CollectUtils.newArrayList(messages.getMessages());
      actionErrors = CollectUtils.newArrayList(messages.getErrors());
      if ("true".equals(clear)) {
        messages.clear();
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
