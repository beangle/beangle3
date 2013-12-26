package org.beangle.struts2.captcha.service;

import java.io.ByteArrayOutputStream;

import javax.servlet.http.HttpServletRequest;

public interface CaptchaProvider {

  boolean verify(HttpServletRequest request,String response);

  ByteArrayOutputStream generater(HttpServletRequest request);
}
