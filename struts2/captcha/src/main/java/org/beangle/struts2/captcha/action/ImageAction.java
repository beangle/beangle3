package org.beangle.struts2.captcha.action;

import org.beangle.struts2.action.ActionSupport;
import org.beangle.struts2.annotation.Result;
import org.beangle.struts2.annotation.Results;
import org.beangle.struts2.captcha.service.CaptchaProvider;

/**
 * Generator Jcaptcha image.
 * 
 * @author chaostone
 */
@Results({ @Result(name = "index", type = "captchaImage") })
public class ImageAction extends ActionSupport {

  private CaptchaProvider captchaProvider;

  /**
   * Holds the byte[] for JPEG encoded Captcha Image.
   */
  @Override
  public String index() throws Exception {
    put("captchaImage", captchaProvider.generater(getRequest()).toByteArray());
    return forward();
  }

  public void setCaptchaProvider(CaptchaProvider captchaProvider) {
    this.captchaProvider = captchaProvider;
  }

}
