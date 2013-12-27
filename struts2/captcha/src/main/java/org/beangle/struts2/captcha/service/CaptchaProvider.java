package org.beangle.struts2.captcha.service;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * Captcha 提供者
 * @author chaostone
 *
 */
public interface CaptchaProvider {

  /**
   * 生成验证字节流
   * @param request
   * @return null if non local provider.
   */
  ByteArrayOutputStream generater(HttpServletRequest request);

  /**
   * 验证用户输入
   * @param request
   * @param response
   * @return
   */
  boolean verify(HttpServletRequest request,String response);

}
