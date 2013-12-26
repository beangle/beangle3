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
