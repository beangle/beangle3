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
package org.beangle.struts1.action;

import java.util.Iterator;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.codec.net.URLCodec;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.beangle.commons.lang.Objects;
import org.beangle.commons.lang.Strings;
import org.beangle.struts1.dispatch.Action;
import org.beangle.struts1.support.ForwardSupport;

public class DispatchAction extends org.apache.struts.actions.DispatchAction {

  public DispatchAction() {
  }

  protected ActionForward forward(ActionMapping mapping, String forwardTag) {
    return mapping.findForward(forwardTag);
  }

  protected ActionForward forward(ActionMapping mapping, HttpServletRequest request, String msg,
      String forward) {
    ActionMessages actionMessages = new ActionMessages();
    actionMessages.add("org.apache.struts.action.GLOBAL_MESSAGE", new ActionMessage(msg));
    addErrors(request, actionMessages);
    return mapping.findForward(forward);
  }

  protected ActionForward forward(HttpServletRequest request, Action action, String message) {
    if (Strings.isNotEmpty(message))
      saveErrors(request.getSession(), ForwardSupport.buildMessages(new String[] { message }));
    return ForwardSupport.forward(request, action);
  }

  protected ActionForward forward(HttpServletRequest request, Action action) {
    return ForwardSupport.forward(request, action);
  }

  protected ActionForward forward(HttpServletRequest request, String pagePath) {
    return ForwardSupport.forward(clazz, request, pagePath);
  }

  protected ActionForward forward(HttpServletRequest request) {
    ActionForward f = ForwardSupport.forward(clazz, request, (String) null);
    return f;
  }

  protected ActionForward redirect(HttpServletRequest request, String method, String message)
      throws Exception {
    return redirect(request, new Action("", method), message);
  }

  protected ActionForward redirect(HttpServletRequest request, Action action, String message)
      throws Exception {
    if (Strings.isNotEmpty(message))
      saveErrors(request.getSession(), ForwardSupport.buildMessages(new String[] { message }));
    return new ActionForward(action.getURL(request).toString(), true);
  }

  /**
   * @deprecated Method redirect is deprecated
   */

  protected ActionForward redirect(HttpServletRequest request, Action action, String message,
      String prefixes[]) throws Exception {
    StringBuffer buf;
    URLCodec urlCodec;
    if (Strings.isNotEmpty(message))
      saveErrors(request.getSession(), ForwardSupport.buildMessages(new String[] { message }));
    buf = action.getURL(request);
    urlCodec = new URLCodec("UTF-8");
    if (null == prefixes || prefixes.length <= 0) {
      if (log.isDebugEnabled()) log.debug("redirect:" + buf.toString());
      return new ActionForward(buf.toString(), true);
    } else {
      Iterator iter = request.getParameterMap().keySet().iterator();
      while (iter.hasNext()) {
        String key = (String) iter.next();
        if (Objects.equals("_params", key)) continue; /* Loop/switch isn't completed */
        int i = 0;
        do {
          if (i >= prefixes.length) break; /* Loop/switch isn't completed */
          if (key.startsWith(prefixes[i])) {
            String value = request.getParameter(key);
            if (Strings.isNotEmpty(value))
              buf.append("&").append(key).append("=").append(urlCodec.encode(value));
          }
          i++;
        } while (true);
      }
      if (log.isDebugEnabled()) log.debug("redirect:" + buf.toString());
      return new ActionForward(buf.toString(), true);
    }
  }
}
