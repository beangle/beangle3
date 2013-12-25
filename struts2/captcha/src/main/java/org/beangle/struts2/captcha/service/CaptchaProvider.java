package org.beangle.struts2.captcha.service;

import javax.servlet.http.HttpServletRequest;

public interface CaptchaProvider {

  boolean verify(HttpServletRequest request);

}
