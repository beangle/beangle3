/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
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
