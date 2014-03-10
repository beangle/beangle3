/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2014, Beangle Software.
 *
 * Beangle is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Beangle is distributed in the hope that it will be useful.
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Beangle.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.beangle.struts2.convention;

import java.io.Serializable;
import java.util.List;

import org.beangle.commons.collection.CollectUtils;

/**
 * Store action message and error
 * 
 * @author chaostone
 * @since 3.0.0
 */
public class ActionMessages implements Serializable {

  private static final long serialVersionUID = 4112997123562877516L;
  List<String> messages = CollectUtils.newArrayList(2);
  List<String> errors = CollectUtils.newArrayList(0);

  public List<String> getMessages() {
    return messages;
  }

  public List<String> getErrors() {
    return errors;
  }

  public void clear() {
    messages.clear();
    errors.clear();
  }

}
