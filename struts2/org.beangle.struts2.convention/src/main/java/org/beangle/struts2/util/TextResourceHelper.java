/* Copyright c 2005-2012.
 * Licensed under GNU  LESSER General Public License, Version 3.
 * http://www.gnu.org/licenses
 */
package org.beangle.struts2.util;

import org.beangle.commons.i18n.TextResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opensymphony.xwork2.util.ValueStack;

/**
 * TextResource Helper <br>
 * Search valuestack to find first textResource
 * 
 * @author chaostone
 * @since 3.0.0
 */
public final class TextResourceHelper {

  private static Logger logger = LoggerFactory.getLogger(TextResourceHelper.class);

  public static String getText(String key, String defaultMessage, ValueStack stack, Object... args) {
    String msg = null;
    TextResource tp = null;

    for (Object o : stack.getRoot()) {
      if (o instanceof TextResource) {
        tp = (TextResource) o;
        msg = tp.getText(key, defaultMessage,args);
        break;
      }
    }
    if (msg == null) {
      if (tp != null) {
        logger.warn("The first TextResource in the ValueStack (" + tp.getClass().getName()
            + ") could not locate the message resource with key '" + key + "'");
      } else {
        logger.warn("Could not locate the message resource '" + key
            + "' as there is no TextResource in the ValueStack.");
      }
    }
    return msg;
  }

}
