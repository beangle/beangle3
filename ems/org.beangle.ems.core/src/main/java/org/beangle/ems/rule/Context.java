/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.ems.rule;

import java.util.List;
import java.util.Map;

import org.beangle.util.i18n.Message;

/**
 * 规则执行上下文
 * 
 * @author chaostone
 */
public interface Context {

	public Map<String, Object> getParams();

	public List<Message> getMessages();

	public void addMessage(Message message);

	public List<Message> getErrors();

	public void addError(Message message);
}
