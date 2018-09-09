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
package org.beangle.struts1.support;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.beangle.struts1.dispatch.Action;
import org.beangle.struts1.dispatch.Conventions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForwardSupport {

  public ForwardSupport() {
  }

  public static ActionForward forward(Class actionClass, HttpServletRequest request, String pagePath) {
    StringBuffer buf = Conventions.getViewName(request, actionClass, pagePath);
    buf.append(Conventions.getProfile(actionClass).getPagePostfix());
    if (logger.isDebugEnabled()) logger.debug(buf.toString());
    return new ActionForward(buf.toString());
  }

  public static ActionMessages buildMessages(String messages[]) {
    ActionMessages actionMessages = new ActionMessages();
    if (null != messages && messages.length > 0) {
      for (int i = 0; i < messages.length; i++)
        actionMessages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(messages[i]));

    }
    return actionMessages;
  }

  public static ActionForward forward(HttpServletRequest request, Action action) {
    return new ActionForward(action.getURL(request).toString());
  }

  protected static final Logger logger;

  static {
    logger = LoggerFactory.getLogger(org.beangle.struts1.support.ForwardSupport.class);
  }
}
