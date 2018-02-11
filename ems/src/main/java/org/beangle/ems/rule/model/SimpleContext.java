/*
 * Beangle, Agile Development Scaffold and Toolkits.
 *
 * Copyright © 2005, The Beangle Software.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.ems.rule.model;

import java.util.List;
import java.util.Map;

import org.beangle.commons.collection.CollectUtils;
import org.beangle.commons.text.i18n.Message;
import org.beangle.ems.rule.Context;

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
