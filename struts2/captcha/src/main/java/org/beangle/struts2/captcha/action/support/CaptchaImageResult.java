/*
 * Beangle, Agile Development Scaffold and Toolkit
 *
 * Copyright (c) 2005-2016, Beangle Software.
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
package org.beangle.struts2.captcha.action.support;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;

/**
 * Struts2 Result Type for Captcha Image.
 * 
 * @author chaostone
 */
public class CaptchaImageResult implements Result {

  private static final long serialVersionUID = -6109044716352235499L;

  public void execute(ActionInvocation invocation) throws IOException, IllegalArgumentException {
    HttpServletResponse response = ServletActionContext.getResponse();

    // Read captcha image bytes
    byte[] image = (byte[]) ActionContext.getContext().getContextMap().get("captchaImage");

    // Send response
    response.setHeader("Cache-Control", "no-store");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
    response.setContentType("image/jpeg");
    response.setContentLength(image.length);
    try {
      response.getOutputStream().write(image);
      response.getOutputStream().flush();
    } catch (IOException e) {
      throw e;
    }
  }
}
